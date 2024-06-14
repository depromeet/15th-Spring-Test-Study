package com.example.sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculationRequestTest {
    @Test
    public void 유효한_숫자를_파싱할_수_있다() {
        // given
        String[] parts = new String[]{"2", "+", "3"};

        // when
        CalculationRequst calculationRequst = new CalculationRequst(parts);

        // then
        Assertions.assertEquals(2, calculationRequst.getNum1());
        Assertions.assertEquals("+", calculationRequst.getOperator());
        Assertions.assertEquals(3, calculationRequst.getNum2());
    }

    @Test
    public void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다() {
        // given
        String[] parts = new String[]{"232", "+", "123"};

        // when
        CalculationRequst calculationRequst = new CalculationRequst(parts);

        // then
        Assertions.assertEquals(232, calculationRequst.getNum1());
        Assertions.assertEquals("+", calculationRequst.getOperator());
        Assertions.assertEquals(123, calculationRequst.getNum2());
    }

    @Test
    public void 유효한_길이의_숫자가_들어오지_않으면_에러를_던진다() {
        // given
        String[] parts = new String[]{"232", "+"};

        // when

        // then
        Assertions.assertThrows(BadRequestException.class, () -> {
            new CalculationRequst(parts);
        });
    }

    @Test
    public void 유효하지_않은_연산자가_들어오면_에러를_던진다() {
        // given
        String[] parts = new String[]{"232", "x", "123"};

        // when

        // then
        Assertions.assertThrows(InvalidOperatorException.class, () -> {
            new CalculationRequst(parts);
        });
    }

    @Test
    public void 유효하지_않은_길이의_연산자가_들어오면_에러를_던진다() {
        // given
        String[] parts = new String[]{"232", "+-", "123"};

        // when

        // then
        Assertions.assertThrows(InvalidOperatorException.class, () -> {
            new CalculationRequst(parts);
        });
    }
}
