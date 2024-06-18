package swag.qrorder.sample.src.main.java.com.example.sample;

public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException() {
        super("Invalid operator, (+, -, *, /)");
    }
}
