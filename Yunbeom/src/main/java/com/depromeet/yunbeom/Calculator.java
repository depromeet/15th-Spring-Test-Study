package com.depromeet.yunbeom;

public class Calculator {

	public long calculator(long num1, long num2, String operator) {
		// java 12부터 도입된 문법이라 한다.
		return switch (operator) {
			case "+" -> num1 + num2;
			case "-" -> num1 - num2;
			case "*" -> num1 * num2;
			case "/" -> num1 / num2;
			default -> throw new InvalidOperatorException();
		};
	}
}
