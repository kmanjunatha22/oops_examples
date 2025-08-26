package Collections;
//Create a LinkedList of strings, add 5 names, and retrieve the 3rd element.
import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> names = new LinkedList<>();

        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");
        names.add("Emma");

        // Retrieve the 3rd element (index starts from 0)
        String thirdName = names.get(2);

        System.out.println("The 3rd name is: " + thirdName);
    }
}


