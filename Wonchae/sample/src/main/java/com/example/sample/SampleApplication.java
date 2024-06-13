package com.example.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		CalculationRequst calculationRequst = new CalculationRequestReader().read();
		long answer = new Calculator().calculate(
				calculationRequst.getNum1(),
				calculationRequst.getOperator(),
				calculationRequst.getNum2()
		);
		System.out.println(answer);
        //		SpringApplication.run(SampleApplication.class, args);
	}

}
