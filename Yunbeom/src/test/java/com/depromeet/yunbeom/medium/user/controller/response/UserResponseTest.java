package com.depromeet.yunbeom.user.controller.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;

class UserResponseTest {

	@Test
	public void User으로_응답을_생성할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.build();

		// when
		UserResponse userResponse = UserResponse.from(user);

		// then
		assertThat(userResponse.getId()).isEqualTo(1);
		assertThat(userResponse.getEmail()).isEqualTo("uiurihappy@naver.com");
		assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
	}
}
