package com.depromeet.yunbeom;

import java.util.Scanner;

public class SampleApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter two numbers and an operator: ");
		String result = scanner.nextLine();

		String[] parts = result.split(" ");
		long num1 = Long.parseLong(parts[0]);
		long num2 = Long.parseLong(parts[2]);
		for (String part : parts) {
			System.out.println(part);
		}
	}
}
