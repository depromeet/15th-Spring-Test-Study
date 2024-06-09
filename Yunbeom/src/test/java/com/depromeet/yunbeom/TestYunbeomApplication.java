package com.depromeet.yunbeom;

import org.springframework.boot.SpringApplication;

public class TestYunbeomApplication {

	public static void main(String[] args) {
		SpringApplication.from(YunbeomApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
