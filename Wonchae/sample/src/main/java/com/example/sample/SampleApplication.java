package com.example.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		String[] parts = new CalculationRequestReader().read();
		CalculationRequst calculationRequst = new CalculationRequst(parts);
		long answer = new Calculator().calculate(
				calculationRequst.getNum1(),
				calculationRequst.getOperator(),
				calculationRequst.getNum2()
		);
		System.out.println(answer);
        //		SpringApplication.run(SampleApplication.class, args);
	}

}
