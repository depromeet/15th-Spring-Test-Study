package com.example.sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class CalculationRequestReaderTest {
    @Test
    public void System_in으로_데이터를_읽어들일_수_있다() {
        // given
        CalculationRequestReader calculationRequestReader = new CalculationRequestReader();

        // when
        System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));
        CalculationRequst calculationRequst = calculationRequestReader.read();

        // then
        Assertions.assertEquals(2, calculationRequst.getNum1());
        Assertions.assertEquals("+", calculationRequst.getOperator());
        Assertions.assertEquals(3, calculationRequst.getNum2());
    }
}
