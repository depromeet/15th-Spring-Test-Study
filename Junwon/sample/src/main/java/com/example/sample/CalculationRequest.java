package swag.qrorder.sample.src.main.java.com.example.sample;

import java.util.Objects;

public class CalculationRequest {
    private final long num1;
    private final long num2;
    private final String operation;

    public CalculationRequest(String[] parts) {
        if (parts.length != 3) {
            throw new BadRequestException();
        }
        String operation = parts[1];
        if (operation.length() != 1 || isInvalidOperation(operation)) {
            throw new InvalidOperatorException();
        }
        this.num1 = Long.parseLong(parts[0]);
        this.num2 = Long.parseLong(parts[2]);
        this.operation = operation;
    }

    private static boolean isInvalidOperation(String operation) {
        return !operation.equals("+") &&
                !operation.equals("-") &&
                !operation.equals("*") &&
                !operation.equals("/");
    }

    public long getNum1() {
        return num1;
    }

    public long getNum2() {
        return num2;
    }

    public String getOperation() {
        return operation;
    }
}
