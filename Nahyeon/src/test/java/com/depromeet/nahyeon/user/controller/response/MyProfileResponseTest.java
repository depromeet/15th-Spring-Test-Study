package com.depromeet.nahyeon.user.controller.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class MyProfileResponseTest {

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
		MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

		// then
		Assertions.assertThat(myProfileResponse.getId()).isEqualTo(1);
		Assertions.assertThat(myProfileResponse.getEmail()).isEqualTo("nahyeon@gmail.com");
		Assertions.assertThat(myProfileResponse.getNickname()).isEqualTo("nahyeon");
		Assertions.assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
		Assertions.assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
		Assertions.assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
	}
}