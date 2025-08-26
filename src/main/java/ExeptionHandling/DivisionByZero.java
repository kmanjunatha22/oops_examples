package ExeptionHandling;
//1. Write a program to handle division by zero using try-catch.

    public class DivisionByZero {
        public static void main(String[] args) {
            try {
                int a = 10, b = 0;
                int result = a / b;   // This will throw ArithmeticException
                System.out.println("Result: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Error: Division by zero is not allowed!");
            }
        }
    }



