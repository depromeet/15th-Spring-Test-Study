package com.depromeet.yunbeom;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	@Test
	public void 덧셈() {
		// given
		Calculator calculator = new Calculator();
		long num1 = 2;
		long num2 = 3;
		String operator = "+";

		// when
		long result = calculator.calculator(num1, num2, operator);

		// then
		assertEquals(5, result);	// JUnit 5
		assertThat(result).isEqualTo(5); // AssertJ
	}

	@Test
	public void 곱셈() {
		// given
		Calculator calculator = new Calculator();
		long num1 = 2;
		long num2 = 3;
		String operator = "*";

		// when
		long result = calculator.calculator(num1, num2, operator);

		// then
		assertEquals(6, result);	// JUnit 5
		assertThat(result).isEqualTo(6); // AssertJ
	}

	@Test
	public void 뺄셈() {
		// given
		Calculator calculator = new Calculator();
		long num1 = 3;
		long num2 = 2;
		String operator = "-";

		// when
		long result = calculator.calculator(num1, num2, operator);

		// then
		assertEquals(1, result);	// JUnit 5
		assertThat(result).isEqualTo(1); // AssertJ
	}

	@Test
	public void 나눗셈() {
		// given
		Calculator calculator = new Calculator();
		long num1 = 25;
		long num2 = 5;
		String operator = "/";

		// when
		long result = calculator.calculator(num1, num2, operator);

		// then
		assertEquals(5, result);	// JUnit 5
		assertThat(result).isEqualTo(5); // AssertJ
	}

	@Test
	public void 잘못된_연산이_들어왔을때() {
		// given
		Calculator calculator = new Calculator();
		long num1 = 25;
		long num2 = 5;
		String operator = "2";

		// then
		assertThrows(InvalidOperatorException.class, () -> calculator.calculator(num1, num2, operator));	// JUnit 5
	}
}
