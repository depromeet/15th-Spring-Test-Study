package com.depromeet.yunbeom;

import java.util.Scanner;

public class SampleApplication {

	public static void main(String[] args) {
		CalculationRequest request = new CalculationRequestReader().read();
		long answer = new Calculator().calculator(
			request.getNum1(),
			request.getNum2(),
			request.getOperator()
		);
		System.out.println(answer);
	}
}
