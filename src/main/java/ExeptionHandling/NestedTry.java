package ExeptionHandling;
//3. Show how a nested try block works with two different exceptions.

    public class NestedTry {
        public static void main(String[] args) {
            try {
                int[] arr = new int[5];

                try {
                    arr[5] = 100;  // ArrayIndexOutOfBoundsException
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Inner catch: " + e);
                }

                int result = 10 / 0;  // ArithmeticException
            } catch (ArithmeticException e) {
                System.out.println("Outer catch: Division by zero");
            }
        }
    }


