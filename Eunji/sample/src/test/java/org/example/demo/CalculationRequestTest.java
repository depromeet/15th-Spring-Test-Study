package org.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CalculationRequestTest {

    @Test
    public void 유효한_숫자를_파싱할_수_있다() {
        // given
        String[] parts = new String[]{"2", "+", "3"};

        // when
        CalculationRequest request = new CalculationRequest(parts);

        // then
        assertEquals(2, request.getNum1());
        assertEquals("+", request.getOperator());
        assertEquals(3, request.getNum2());
    }

    @Test
    public void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다() {
        // given
        String[] parts = new String[]{"232", "+", "123"};

        // when
        CalculationRequest request = new CalculationRequest(parts);

        // then
        assertEquals(232, request.getNum1());
        assertEquals("+", request.getOperator());
        assertEquals(123, request.getNum2());
    }

    @Test
    public void 유효한_길이의_숫자가_들어오지_않으면_에러를_던진다() {
        // given
        String[] parts = new String[]{"232", "+"};

        // when
        // then
        assertThrows(BadRequestException.class, () -> {
            new CalculationRequest(parts);
        });
    }

    @Test
    public void 유효하지_않은_연산자가_들어오면_에러를_던진다() {
        // given
        String[] parts = new String[]{"232", "x", "2"};

        // when
        // then
        assertThrows(InvalidOperatorException.class, () -> new CalculationRequest(parts));
    }

    @Test
    public void 유효하지_않은_길이의_연산자가_들어오면_에러를_던진다() {
        // given
        String[] parts = new String[]{"232", "*/", "2"};

        // when
        // then
        assertThrows(InvalidOperatorException.class, () -> new CalculationRequest(parts));
    }

}
