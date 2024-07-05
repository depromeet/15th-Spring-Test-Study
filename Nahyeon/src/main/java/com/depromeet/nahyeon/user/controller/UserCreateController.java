package com.depromeet.nahyeon.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.user.controller.port.UserService;
import com.depromeet.nahyeon.user.controller.response.UserResponse;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class UserCreateController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserCreate userCreate) {
		User user = userService.create(userCreate);
		return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
	}
}
