package Collections;
//Demonstrate the difference between size() and isEmpty() using an ArrayList
import java.util.ArrayList;

public class ArrayListSizeEmpty {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        System.out.println("Is list empty? " + list.isEmpty()); // true
        System.out.println("Size of list: " + list.size());     // 0

        list.add("Hello");
        list.add("World");

        System.out.println("Is list empty? " + list.isEmpty()); // false
        System.out.println("Size of list: " + list.size());     // 2
    }
}

