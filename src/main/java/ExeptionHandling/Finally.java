package ExeptionHandling;
//Write a program to demonstrate
// how the finally block always executes, whether an exception occurs or not.
    public class Finally {
        public static void main(String[] args) {
            try {
                int result = 10 / 0; // Exception occurs
                System.out.println("Result: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Exception caught: " + e.getMessage());
            } finally {
                System.out.println("Finally block executed, no matter what!");
            }

            System.out.println("Program continues...");
        }
    }


