package Collections;
//Create an ArrayList of integers, add 10 numbers, and print them using a for-each loop.
import java.util.ArrayList;

public class ArrayListExample1 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();

        // Adding 10 numbers
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        // Printing using for-each loop
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}
