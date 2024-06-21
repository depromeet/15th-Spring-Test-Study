package com.depromeet.nahyeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
	title = "Toy project",
	version = "v1",
	description = "테스트 코드를 추가하기 위한 연습에 사용될 토이 프로젝트입니다"
))
public class NahyeonApplication {

	public static void main(String[] args) {
		SpringApplication.run(NahyeonApplication.class, args);
	}
}