package src.main.java;

import java.util.Scanner;

public class CalculationRequestReader {
    public String[] read() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter two numbers and an operator (eg. 1 + 2): ");
        return sc.nextLine().split(" ");
    }

}
