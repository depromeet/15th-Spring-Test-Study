package com.example.sample;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad Request, ilvalid size");
    }
}
