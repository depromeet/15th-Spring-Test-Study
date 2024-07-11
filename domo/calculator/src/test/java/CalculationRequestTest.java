package calculator.src.test.java;


import org.junit.jupiter.api.Test;
import calculator.src.main.java.BadRequestException;
import calculator.src.main.java.CalculationRequest;
import calculator.src.main.java.InvalidOperatorException;

import static org.junit.jupiter.api.Assertions.*;

class CalculationRequestTest {

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
        String[] parts = new String[]{"223", "+", "243"};

        // when
        CalculationRequest request = new CalculationRequest(parts);

        // then
        assertEquals(223, request.getNum1());
        assertEquals("+", request.getOperator());
        assertEquals(243, request.getNum2());
    }

    @Test
    public void 유효한_길이의_숫자가_들어오지_않으면_예외를_발생시킨다() {
        // given
        String[] parts = new String[]{"2", "+"};

        // when, then
        assertThrows(BadRequestException.class, () -> new CalculationRequest(parts));
    }

    @Test
    public void 유효하지_않은_연산자가_들어오면_예외를_발생시킨다() {
        // given
        String[] parts = new String[]{"2", "x", "3"};

        // when, then
        assertThrows(InvalidOperatorException.class, () -> new CalculationRequest(parts));
    }

    @Test
    public void 유효하지_않은_길이의_연산자가_들어오면_예외를_발생시킨다() {
        // given
        String[] parts = new String[]{"2", "+-", "3"};

        // when, then
        assertThrows(InvalidOperatorException.class, () -> new CalculationRequest(parts));
    }
}
