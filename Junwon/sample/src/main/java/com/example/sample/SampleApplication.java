package swag.qrorder.sample.src.main.java.com.example.sample;

import java.util.Scanner;

public class SampleApplication {
    public static void main(String[] args) {
        CalculationRequest calculationRequest = new CalculationRequestReader().read();
        long answer = new Calculator().calculate(
                calculationRequest.getNum1(),
                calculationRequest.getOperation(),
                calculationRequest.getNum2());
        System.out.println(answer);
    }
}
