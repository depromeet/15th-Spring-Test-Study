package com.depromeet.nahyeon.user.controller.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class UserResponseTest {

	@Test
	public void User로_응답을_생성할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("nahyeon@gmail.com")
			.nickname("nahyeon")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
			.build();

		// when
		UserResponse userResponse = UserResponse.from(user);

		// then
		Assertions.assertThat(userResponse.getId()).isEqualTo(1);
		Assertions.assertThat(userResponse.getEmail()).isEqualTo("nahyeon@gmail.com");
		Assertions.assertThat(userResponse.getNickname()).isEqualTo("nahyeon");
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
		Assertions.assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
	}
}