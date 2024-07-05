package com.depromeet.yunbeom.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.depromeet.yunbeom.mock.TestContainer;
import com.depromeet.yunbeom.user.controller.response.UserResponse;
import com.depromeet.yunbeom.user.domain.UserCreate;
import com.depromeet.yunbeom.user.domain.UserStatus;

class UserCreateControllerTest {

	@Test
	void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.build();
		UserCreate userCreate = UserCreate.builder()
			.email("uiurihappy@kakao.com")
			.nickname("uiurihappy")
			.address("Pangyo")
			.build();

		// when
		ResponseEntity<UserResponse> result = testContainer.userCreateController.create(userCreate);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("uiurihappy@kakao.com");
		assertThat(result.getBody().getNickname()).isEqualTo("uiurihappy");
		assertThat(result.getBody().getLastLoginAt()).isNull();
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
	}

}