package com.depromeet.nahyeon.user.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.user.controller.port.AuthenticationService;
import com.depromeet.nahyeon.user.controller.port.UserCreateService;
import com.depromeet.nahyeon.user.controller.port.UserReadService;
import com.depromeet.nahyeon.user.controller.port.UserUpdateService;
import com.depromeet.nahyeon.user.controller.response.MyProfileResponse;
import com.depromeet.nahyeon.user.controller.response.UserResponse;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class UserController {

	private final UserCreateService userCreateService;

	private final UserReadService userReadService;

	private final UserUpdateService userUpdateService;

	private final AuthenticationService authenticationService;

	@ResponseStatus
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
		return ResponseEntity.ok().body(UserResponse.from(userReadService.getById(id)));
	}

	@GetMapping("/{id}/verify")
	public ResponseEntity<Void> verifyEmail(
		@PathVariable long id,
		@RequestParam String certificationCode
	) {
		authenticationService.verifyEmail(id, certificationCode);
		return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create("http://localhost:3000"))
			.build();
	}

	@GetMapping("/me")
	public ResponseEntity<MyProfileResponse> getMyInfo(
		@Parameter(name = "EMAIL", in = ParameterIn.HEADER)
		@RequestHeader("EMAIL") String email // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져온다.
	) {
		User user = userReadService.getByEmail(email);
		authenticationService.login(user.getId());
		user = userReadService.getByEmail(email);
		return ResponseEntity.ok()
			.body(MyProfileResponse.from(user));
	}

	@PutMapping("/me")
	@Parameter(in = ParameterIn.HEADER, name = "EMAIL")
	public ResponseEntity<MyProfileResponse> updateMyInfo(
		@Parameter(name = "EMAIL", in = ParameterIn.HEADER)
		@RequestHeader("EMAIL") String email, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져온다.
		@RequestBody UserUpdate userUpdateDto
	) {
		User user = userReadService.getByEmail(email);
		user = userUpdateService.update(user.getId(), userUpdateDto);
		return ResponseEntity.ok().body(MyProfileResponse.from(user));
	}
}
