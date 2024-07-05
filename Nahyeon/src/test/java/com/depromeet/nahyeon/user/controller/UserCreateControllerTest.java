package com.depromeet.nahyeon.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.depromeet.nahyeon.mock.TestContainer;
import com.depromeet.nahyeon.mock.TestUuidHolder;
import com.depromeet.nahyeon.user.controller.response.UserResponse;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class UserCreateControllerTest {

	@Test
	void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.uuidHolder(new TestUuidHolder("aaaaaaa-aaaaaa-aaaaaa"))
			.build();
		UserCreate userCreate = UserCreate.builder()
			.email("nahyeon@kakao.com")
			.nickname("hyeon")
			.address("Pangyo")
			.build();

		// when
		ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(userCreate);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("nahyeon@kakao.com");
		assertThat(result.getBody().getNickname()).isEqualTo("hyeon");
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(result.getBody().getLastLoginAt()).isNull();
		assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaa-aaaaaa-aaaaaa");
	}
}
