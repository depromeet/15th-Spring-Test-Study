package com.depromeet.yunbeom.medium.user.controller.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.user.controller.response.MyProfileResponse;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;

class MyProfileResponseTest {

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
		MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

		// then
		assertThat(myProfileResponse.getId()).isEqualTo(1);
		assertThat(myProfileResponse.getEmail()).isEqualTo("uiurihappy@naver.com");
		assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
		assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
	}
}