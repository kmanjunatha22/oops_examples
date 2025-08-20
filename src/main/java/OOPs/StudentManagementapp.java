package OOPs;

//public class StudentManagementapp {
//}
import java.util.*;

/*
 * Student Database Management System – Console App
 * Demonstrates OOP pillars:
 *  - Encapsulation: private fields + getters/setters with validation
 *  - Inheritance: Person -> Student, Teacher
 *  - Polymorphism: method overriding (displayDetails), method overloading (calculateGrade)
 *  - Abstraction: DatabaseOperations<T> defines CRUD; StudentDatabase implements it
 *
 * Compile & Run:
 *   javac Main.java && java Main
 */

// ---------- Abstraction: common database operations ----------
interface DatabaseOperations<T> {
    boolean add(T item);
    boolean update(int id, T item);
    boolean delete(int id);
    List<T> getAll();
    Optional<T> getById(int id);
}

// ---------- Base class (Inheritance) ----------
abstract class Person {
    // Encapsulation: private fields
    private int id;
    private String name;

    public Person(int id, String name) {
        setId(id);
        setName(name);
    }

    // Getters/Setters with simple validation
    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive");
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");
        this.name = name.trim();
    }

    // Polymorphism via overriding
    public abstract void displayDetails();
}

// ---------- Derived classes (Inheritance) ----------
class Student extends Person {
    private String course;
    private List<Integer> marks; // store 0-100 per subject

    public Student(int id, String name, String course, List<Integer> marks) {
        super(id, name);
        setCourse(course);
        setMarks(marks);
    }

    public String getCourse() { return course; }
    public void setCourse(String course) {
        if (course == null || course.trim().isEmpty())
            throw new IllegalArgumentException("Course cannot be empty");
        this.course = course.trim();
    }

    public List<Integer> getMarks() { return new ArrayList<>(marks); }
    public void setMarks(List<Integer> marks) {
        if (marks == null || marks.isEmpty())
            throw new IllegalArgumentException("Marks cannot be empty");
        for (Integer m : marks) {
            if (m == null || m < 0 || m > 100)
                throw new IllegalArgumentException("Each mark must be 0..100");
        }
        this.marks = new ArrayList<>(marks);
    }

    public double average() {
        return marks.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    // Polymorphism: Overloading calculateGrade with different strategies
    public String calculateGrade() {
        return calculateGrade(0); // default without grace
    }

    public String calculateGrade(int graceMarksEach) { // overloaded
        // Apply grace to each mark (capped 100)
        double avg = marks.stream()
                .mapToInt(m -> Math.min(100, m + Math.max(graceMarksEach, 0)))
                .average().orElse(0);
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        return "F";
    }

    // Overloaded display: with/without marks
    public void displayDetails() { // overrides Person.displayDetails
        displayDetails(true);
    }

    public void displayDetails(boolean showMarks) { // overloaded
        System.out.println("[Student] ID: " + getId() + ", Name: " + getName());
        System.out.println("  Course: " + course);
        if (showMarks) {
            System.out.println("  Marks: " + marks);
        }
        System.out.printf("  Average: %.2f, Grade: %s%n", average(), calculateGrade());
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', course='%s', avg=%.2f, grade=%s}",
                getId(), getName(), course, average(), calculateGrade());
    }
}

class Teacher extends Person {
    private String department;

    public Teacher(int id, String name, String department) {
        super(id, name);
        setDepartment(department);
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty())
            throw new IllegalArgumentException("Department cannot be empty");
        this.department = department.trim();
    }

    @Override
    public void displayDetails() { // polymorphic override
        System.out.println("[Teacher] ID: " + getId() + ", Name: " + getName());
        System.out.println("  Department: " + department);
    }
}

// ---------- Database implementation (Abstraction -> Concrete) ----------
class StudentDatabase implements DatabaseOperations<Student> {
    private final Map<Integer, Student> students = new LinkedHashMap<>();

    @Override
    public boolean add(Student s) {
        if (students.containsKey(s.getId())) return false; // prevent duplicate IDs
        students.put(s.getId(), s);
        return true;
    }

    @Override
    public boolean update(int id, Student updated) {
        if (!students.containsKey(id)) return false;
        students.put(id, updated);
        return true;
    }

    @Override
    public boolean delete(int id) {
        return students.remove(id) != null;
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(students.values());
    }

    @Override
    public Optional<Student> getById(int id) {
        return Optional.ofNullable(students.get(id));
    }
}

// ---------- Console UI ----------
public class StudentManagementapp {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDatabase db = new StudentDatabase();

    public static void main(String[] args) {
        seedSampleData(); // optional: preload some records
        System.out.println("===== Student Database Management System (OOP) =====");

        while (true) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addStudentFlow(); break;
                case 2: viewAllStudents(); break;
                case 3: updateStudentFlow(); break;
                case 4: deleteStudentFlow(); break;
                case 5: viewSingleStudent(); break;
                case 0:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1) Add new student");
        System.out.println("2) View all student records");
        System.out.println("3) Update existing record");
        System.out.println("4) Delete record");
        System.out.println("5) View a student by ID");
        System.out.println("0) Exit");
    }

    // ---------- Flows ----------
    private static void addStudentFlow() {
        try {
            int id = readInt("ID: ");
            String name = readLine("Name: ");
            String course = readLine("Course: ");
            List<Integer> marks = readMarks();
            Student s = new Student(id, name, course, marks);
            if (db.add(s)) System.out.println("Added: " + s);
            else System.out.println("A student with this ID already exists.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void viewAllStudents() {
        List<Student> all = db.getAll();
        if (all.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("-- All Students --");
        // Demonstrate polymorphism: call displayDetails() via Person reference
        for (Person p : all) {
            p.displayDetails();
            System.out.println();
        }
    }

    private static void updateStudentFlow() {
        int id = readInt("Enter ID to update: ");
        Optional<Student> opt = db.getById(id);
        if (!opt.isPresent()) {
            System.out.println("No student with ID " + id);
            return;
        }
        Student existing = opt.get();
        System.out.println("Editing: " + existing);

        try {
            String name = readLine("New name (leave blank to keep): ");
            if (!name.trim().isEmpty()) existing.setName(name);

            String course = readLine("New course (leave blank to keep): ");
            if (!course.trim().isEmpty()) existing.setCourse(course);

            String marksChoice = readLine("Update marks? (y/N): ");
            if (marksChoice.equalsIgnoreCase("y")) {
                existing.setMarks(readMarks());
            }
            // Put back to DB (not strictly needed since same reference is stored)
            db.update(id, existing);
            System.out.println("Updated: " + existing);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void deleteStudentFlow() {
        int id = readInt("Enter ID to delete: ");
        if (db.delete(id)) System.out.println("Deleted student with ID " + id);
        else System.out.println("No student with ID " + id);
    }

    private static void viewSingleStudent() {
        int id = readInt("Enter ID: ");
        Optional<Student> s = db.getById(id);
        if (s.isPresent()) s.get().displayDetails();
        else System.out.println("No student with ID " + id);
    }

    // ---------- Helpers ----------
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = sc.nextLine();
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static List<Integer> readMarks() {
        System.out.println("Enter marks (0-100). Type 'done' to finish. Minimum 1 mark.");
        List<Integer> marks = new ArrayList<>();
        while (true) {
            System.out.print("Mark #" + (marks.size() + 1) + ": ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("done")) {
                if (marks.isEmpty()) {
                    System.out.println("Please enter at least one mark.");
                    continue;
                }
                break;
            }
            try {
                int m = Integer.parseInt(line);
                if (m < 0 || m > 100) {
                    System.out.println("Mark must be between 0 and 100.");
                } else {
                    marks.add(m);
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number or 'done'.");
            }
        }
        return marks;
    }

    private static void seedSampleData() {
        db.add(new Student(101, "Asha", "B.Sc CS", Arrays.asList(88, 92, 79)));
        db.add(new Student(102, "Vikram", "BCA", Arrays.asList(67, 73, 71)));
        db.add(new Student(103, "Nisha", "B.Tech IT", Arrays.asList(95, 91, 97)));

        // Unused in DB but here to show inheritance & polymorphism with Teacher
        Teacher t = new Teacher(201, "Meera", "Computer Science");
        // Demonstration (can be removed):
        // Person ref = t; ref.displayDetails();
    }
}
