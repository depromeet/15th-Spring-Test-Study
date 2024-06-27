package com.depromeet.nahyeon.user.controller.response;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyProfileResponse {

	private Long id;
	private String email;
	private String nickname;
	private String address;
	private UserStatus status;
	private Long lastLoginAt;

	public static MyProfileResponse from(User user) {
		return MyProfileResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.address(user.getAddress())
			.status(user.getStatus())
			.lastLoginAt(user.getLastLoginAt())
			.build();
	}
}
