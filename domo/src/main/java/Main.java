package src.main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter two numbers and an operator (eg. 1 + 2): ");
        String result = sc.nextLine();
        String[] parts = result.split(" ");
        long num1 = Long.parseLong(parts[0]);
        long num2 = Long.parseLong(parts[2]);
        String operator = parts[1];
        long answer = new Calculator().calculate(num1, num2, operator);
        System.out.println(answer);
    }
}
