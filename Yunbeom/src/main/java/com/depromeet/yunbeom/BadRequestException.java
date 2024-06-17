package com.depromeet.yunbeom;

public class BadRequestException extends RuntimeException {
	public BadRequestException() {
		super("Invalid input request");
	}
}
