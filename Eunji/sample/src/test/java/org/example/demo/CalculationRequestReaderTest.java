package org.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

public class CalculationRequestReaderTest {

    @Test
    public void System_in_으로_데이터를_읽어들일_수_있다() {
        // given
        CalculationRequestReader calculationRequestReader = new CalculationRequestReader();

        // when
        System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));
        CalculationRequest result = calculationRequestReader.read();

        // then
        assertEquals(2, result.getNum1());
        assertEquals("+", result.getOperator());
        assertEquals(3, result.getNum2());
    }

}
