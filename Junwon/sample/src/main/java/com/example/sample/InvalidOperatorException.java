package com.example.sample;

public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException() {
        super("Invalid operator, (+, -, *, /)");
    }
}
