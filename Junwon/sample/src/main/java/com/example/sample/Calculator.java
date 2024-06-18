package swag.qrorder.sample.src.main.java.com.example.sample;

public class Calculator {
    public long calculate(long num1, String operation, long num2) {
        return switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new InvalidOperatorException();
        };
    }
}
