package com.depromeet.nahyeon.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.depromeet.nahyeon.exception.CertificationCodeNotMatchedException;
import com.depromeet.nahyeon.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

	@ResponseBody
	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public String resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
		return exception.getMessage();
	}

	@ResponseBody
	@ResponseStatus(FORBIDDEN)
	@ExceptionHandler(CertificationCodeNotMatchedException.class)
	public String certificationCodeNotMatchedExceptionHandler(CertificationCodeNotMatchedException exception) {
		return exception.getMessage();
	}
}
