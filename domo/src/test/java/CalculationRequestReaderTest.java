package src.test.java;

import org.junit.jupiter.api.Test;
import src.main.java.CalculationRequest;
import src.main.java.CalculationRequestReader;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestReaderTest {
    @Test
    public void System_in으로_부터_입력을_받을_수_있다() {
        // given
        CalculationRequestReader reader = new CalculationRequestReader();
        String input = "2 + 3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // when
        CalculationRequest result = reader.read();

        // then
        assertEquals(2, result.getNum1());
        assertEquals("+", result.getOperator());
        assertEquals(3, result.getNum2());
    }
}