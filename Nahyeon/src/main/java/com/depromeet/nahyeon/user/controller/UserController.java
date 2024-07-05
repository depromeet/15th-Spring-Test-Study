package com.depromeet.nahyeon.user.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.user.controller.port.UserService;
import com.depromeet.nahyeon.user.controller.response.UserResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class UserController {

	private final UserService userService;

	@ResponseStatus
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable long id) {
		return ResponseEntity.ok().body(UserResponse.from(userService.getById(id)));
	}

	@GetMapping("/{id}/verify")
	public ResponseEntity<Void> verifyEmail(
		@PathVariable long id,
		@RequestParam String certificationCode
	) {
		userService.verifyEmail(id, certificationCode);
		return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create("http://localhost:3000"))
			.build();
	}
}
