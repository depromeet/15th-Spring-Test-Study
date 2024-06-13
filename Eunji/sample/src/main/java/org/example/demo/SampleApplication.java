package org.example.demo;

public class SampleApplication {

    public static void main(String[] args) {
        CalculationRequest calculationRequest = new CalculationRequestReader().read();
        long answer = new Calculator().calculate(
            calculationRequest.getNum1(),
            calculationRequest.getOperator(),
            calculationRequest.getNum2()
        );
        System.out.println(answer);
    }

}
