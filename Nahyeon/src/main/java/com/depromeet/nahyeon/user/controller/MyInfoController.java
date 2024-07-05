package com.depromeet.nahyeon.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.user.controller.port.UserService;
import com.depromeet.nahyeon.user.controller.response.MyProfileResponse;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserUpdate;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저 프로필(MyInfo)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class MyInfoController {

	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<MyProfileResponse> getByEmail(
		@Parameter(name = "EMAIL", in = ParameterIn.HEADER)
		@RequestHeader("EMAIL") String email // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져온다.
	) {
		User user = userService.getByEmail(email);
		userService.login(user.getId());
		user = userService.getByEmail(email);
		return ResponseEntity.ok()
			.body(MyProfileResponse.from(user));
	}

	@PutMapping("/me")
	@Parameter(in = ParameterIn.HEADER, name = "EMAIL")
	public ResponseEntity<MyProfileResponse> update(
		@Parameter(name = "EMAIL", in = ParameterIn.HEADER)
		@RequestHeader("EMAIL") String email, // 일반적으로 스프링 시큐리티를 사용한다면 UserPrincipal 에서 가져온다.
		@RequestBody UserUpdate userUpdate
	) {
		User user = userService.getByEmail(email);
		user = userService.update(user.getId(), userUpdate);
		return ResponseEntity.ok().body(MyProfileResponse.from(user));
	}
}
