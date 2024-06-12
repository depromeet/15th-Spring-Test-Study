package com.depromeet.yunbeom;

public class InvalidOperatorException extends RuntimeException{
	public InvalidOperatorException() {
		super("Invalid Operator, you need to choose one of +, -, *, /");
	}
}
