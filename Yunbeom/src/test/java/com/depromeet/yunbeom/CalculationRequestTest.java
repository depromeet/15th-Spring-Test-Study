package com.depromeet.yunbeom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculationRequestTest {

	@Test
	public void 유효한_숫자를_파싱할_수_있다(){
		// given
		String[] parts = new String[] {"1", "+", "2"};

		// when
		// 생성자 로직 검증
		CalculationRequest calculationRequest = new CalculationRequest(parts);

		// then
		assertEquals(1, calculationRequest.getNum1());
		assertEquals(2, calculationRequest.getNum2());
		assertEquals("+", calculationRequest.getOperator());
	}

	@Test
	public void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다(){
		// given
		String[] parts = new String[] {"123", "+", "234"};

		// when
		// 생성자 로직 검증
		CalculationRequest calculationRequest = new CalculationRequest(parts);

		// then
		assertEquals(123, calculationRequest.getNum1());
		assertEquals(234, calculationRequest.getNum2());
		assertEquals("+", calculationRequest.getOperator());
	}

	@Test
	public void 숫자가_들어오지_않을_때() {
		// given
		String[] parts = new String[] {"1", "+"};

		// then
		assertThrows(BadRequestException.class, () -> {
			// when
			new CalculationRequest(parts);
		});
	}

	@Test
	public void 유효하지_않는_연산자가_들어오지_않을때() {
		// given
		String[] parts = new String[] {"1", "&", "2"};

		// then
		assertThrows(InvalidOperatorException.class, () -> {
			// when
			new CalculationRequest(parts);
		});
	}

}