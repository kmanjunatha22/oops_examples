package Collections;
//Add elements to a LinkedList, then remove the first and last element
import java.util.LinkedList;

public class LinkedListRemove {
    public static void main(String[] args) {
        LinkedList<String> colors = new LinkedList<>();

        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        colors.add("Purple");

        System.out.println("Original List: " + colors);

        // Remove first and last element
        colors.removeFirst();
        colors.removeLast();

        System.out.println("After removing first and last: " + colors);
    }
}
