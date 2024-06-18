package com.example.sample;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CalulationRequestTest {
    @Test
    public void 유효한_숫자를_파싱할_수_있다(){
        //given
        String[] parts = new String[]{"2", "+", "3"};

        //when
        CalculationRequest calculationRequest = new CalculationRequest(parts);

        //then
        assertEquals(2, calculationRequest.getNum1());
        assertEquals("+", calculationRequest.getOperation());
        assertEquals(3, calculationRequest.getNum2());
    }
    @Test
    public void 세자리_숫자가_넘어가는_유효한_숫자를_파싱할_수_있다(){
        //given
        String[] parts = new String[]{"232", "+", "123"};

        //when
        CalculationRequest calculationRequest = new CalculationRequest(parts);

        //then
        assertEquals(232, calculationRequest.getNum1());
        assertEquals("+", calculationRequest.getOperation());
        assertEquals(123, calculationRequest.getNum2());
    }
    @Test
    public void 유효한_길이의_숫자가_들어오지_않으면_에러를_던진다(){
        //given
        String[] parts = new String[]{"232", "+"};

        //then
        assertThrows(BadRequestException.class, () -> new CalculationRequest(parts));
    }
    @Test
    public void 유효하지_않은_연산자가_들어오면_에러를_던진다(){
        //given
        String[] parts = new String[]{"232", "x", "2"};

        //then
        assertThrows(InvalidOperatorException.class, () -> new CalculationRequest(parts));
    }
    @Test
    public void 유효하지_않은_길이의_연산자가_들어오면_에러를_던진다(){
        //given
        String[] parts = new String[]{"232", "x-", "2"};

        //then
        assertThrows(InvalidOperatorException.class, () -> new CalculationRequest(parts));
    }
}
