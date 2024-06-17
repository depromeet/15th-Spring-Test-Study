package com.depromeet.yunbeom;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

class CalculationRequestReaderTest {

	@Test
	public void System_in_데이터를_읽는다() {
		// given
		CalculationRequestReader calculationRequestReader = new CalculationRequestReader();

		// when
		System.setIn(new ByteArrayInputStream("1 + 2".getBytes()));
		CalculationRequest result = calculationRequestReader.read();

		// then
		assertThat(result.getNum1()).isEqualTo(1);
		assertThat(result.getNum2()).isEqualTo(2);
		assertThat(result.getOperator()).isEqualTo("+");
	}
}