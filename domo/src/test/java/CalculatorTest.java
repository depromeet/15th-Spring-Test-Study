package src.test.java;

import org.junit.jupiter.api.Test;
import src.main.java.Calculator;
import src.main.java.InvalidOperatorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CalculatorTest {

    @Test
    public void 덧셈_연산을_할_수_있다() {
        // given
        long num1 = 2;
        String operator = "+";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, num2, operator);

        // then
        assertEquals(5, result);
    }

    @Test
    public void 뺄셈_연산을_할_수_있다() {
        // given
        long num1 = 5;
        String operator = "-";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, num2, operator);

        // then
        assertEquals(2, result);
    }

    @Test
    public void 곱셈_연산을_할_수_있다() {
        // given
        long num1 = 2;
        String operator = "*";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, num2, operator);

        // then
        assertEquals(6, result);
    }

    @Test
    public void 나눗셈_연산을_할_수_있다() {
        // given
        long num1 = 6;
        String operator = "/";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when
        long result = calculator.calculate(num1, num2, operator);

        // then
        assertEquals(2, result);
    }

    @Test
    public void 잘못된_연산자를_입력하면_예외가_발생한다() {
        // given
        long num1 = 6;
        String operator = "%";
        long num2 = 3;
        Calculator calculator = new Calculator();

        // when, then
        assertThrows(InvalidOperatorException.class, () -> calculator.calculate(num1, num2, operator));
    }
}
