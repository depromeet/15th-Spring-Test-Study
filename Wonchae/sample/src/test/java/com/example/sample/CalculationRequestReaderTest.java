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
        String[] result = calculationRequestReader.read();

        // then
        Assertions.assertEquals("2", result[0]);
        Assertions.assertEquals("+", result[1]);
        Assertions.assertEquals("3", result[2]);
    }
}
