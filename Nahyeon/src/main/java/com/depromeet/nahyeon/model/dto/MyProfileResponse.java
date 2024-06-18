package com.depromeet.nahyeon.model.dto;

import com.depromeet.nahyeon.model.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProfileResponse {

	private Long id;
	private String email;
	private String nickname;
	private String address;
	private UserStatus status;
	private Long lastLoginAt;
}
