package com.example.sample;

import java.util.Scanner;

public class CalculationRequestReader {
    public CalculationRequest read(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter two number: ");
        String result = scanner.nextLine();
        String[] parts = result.split(" ");
        return new CalculationRequest(parts);
    }
}
