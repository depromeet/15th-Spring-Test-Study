package com.example.sample;

public class CalculationRequst {
    private long num1;
    private String operator;
    private long num2;

    public CalculationRequst(String[] parts) {
        this.num1 = Long.parseLong(parts[0]);
        this.num2 = Long.parseLong(parts[2]);
        this.operator = parts[1];
    }

    public long getNum1() {
        return num1;
    }

    public String getOperator() {
        return operator;
    }

    public long getNum2() {
        return num2;
    }
}
