package OOPs;

//public class Encapsulation {
    // Encapsulation Example
    class Student {
        // Private variables (hidden from outside classes)
        private String name;
        private int age;

        // Getter for name
        public String getName() {
            return name;
        }

        // Setter for name
        public void setName(String name) {
            this.name = name;
        }

        // Getter for age
        public int getAge() {
            return age;
        }

        // Setter for age
        public void setAge(int age) {
            if (age > 0) {  // validation
                this.age = age;
            } else {
                System.out.println("Age must be positive!");
            }
        }
    }

    public class Encapsulation {
        public static void main(String[] args) {
            Student s = new Student();

            // Setting values using setters
            s.setName("Rahul");
            s.setAge(20);

            // Accessing values using getters
            System.out.println("Name: " + s.getName());
            System.out.println("Age: " + s.getAge());
        }
    }


