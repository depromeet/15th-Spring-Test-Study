package com.example.sample;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class CalculationRequestReaderTest {
    @Test
    public void System_in으로_데이터를_읽어올_수_있다() {
        //given
        CalculationRequestReader reader = new CalculationRequestReader();

        //when
        System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));
        CalculationRequest result = reader.read();

        //then
        assertEquals(2, result.getNum1());
        assertEquals("+", result.getOperation());
        assertEquals(3, result.getNum2());
    }
}
