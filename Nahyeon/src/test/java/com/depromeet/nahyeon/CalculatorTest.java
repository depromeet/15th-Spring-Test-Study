package com.depromeet.nahyeon;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CalculatorTest {

	// ctrl + shift + r : 현재 커서의 테스트 실행
	@Test
	public void 덧셈연산을_할_수_있다() {
		// given
		long num1 = 2;
		long num2 = 3;
		String operator = "+";
		Calculator calculator = new Calculator();

		// when
		long result = calculator.calculate(num1, num2, operator);

		// then
		assertEquals(5, result); // junit assertion
		assertThat(result).isEqualTo(5); // assertj assertion
	}

	@Test
	public void 뺄셈연산을_할_수_있다() {
		// given
		long num1 = 2;
		long num2 = 3;
		String operator = "-";
		Calculator calculator = new Calculator();

		// when
		long result = calculator.calculate(num1, num2, operator);

		// then
		assertEquals(-1, result); // junit assertion
	}

	@Test
	public void 곱셈연산을_할_수_있다() {
		// given
		long num1 = 2;
		long num2 = 3;
		String operator = "*";
		Calculator calculator = new Calculator();

		// when
		long result = calculator.calculate(num1, num2, operator);

		// then
		assertEquals(6, result); // junit assertion
	}

	@Test
	public void 나눗셈연산을_할_수_있다() {
		// given
		long num1 = 6;
		long num2 = 3;
		String operator = "/";
		Calculator calculator = new Calculator();

		// when
		long result = calculator.calculate(num1, num2, operator);

		// then
		assertEquals(2, result); // junit assertion
	}

	@Test
	public void 잘못된_연산자가_요청으로_들어올_경우_에러가_난다() {
		// given
		long num1 = 6;
		long num2 = 3;
		String operator = "x";
		Calculator calculator = new Calculator();

		// when
		// then
		assertThrows(InvalidOperatorException.class,
			() -> calculator.calculate(num1, num2, operator));
	}
}
