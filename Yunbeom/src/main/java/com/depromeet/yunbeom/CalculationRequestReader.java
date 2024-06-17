package com.depromeet.yunbeom;

import java.util.Scanner;

public class CalculationRequestReader {

	public CalculationRequest read() {
		System.out.println("Enter two numbers and an operator: ");
		Scanner scanner = new Scanner(System.in);

		String result = scanner.nextLine();
		String[] parts = result.split(" ");
		return new CalculationRequest(parts);
	}
}
