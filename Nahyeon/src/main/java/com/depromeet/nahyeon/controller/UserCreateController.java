package com.depromeet.nahyeon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.model.dto.UserCreateDto;
import com.depromeet.nahyeon.model.dto.UserResponse;
import com.depromeet.nahyeon.repository.UserEntity;
import com.depromeet.nahyeon.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

	private final UserController userController;
	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateDto userCreateDto) {
		UserEntity userEntity = userService.createUser(userCreateDto);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(userController.toResponse(userEntity));
	}
}
