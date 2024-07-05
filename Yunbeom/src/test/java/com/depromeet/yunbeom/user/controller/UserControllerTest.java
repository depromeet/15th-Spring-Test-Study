package com.depromeet.yunbeom.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.depromeet.yunbeom.mock.TestContainer;
import com.depromeet.yunbeom.user.controller.response.UserResponse;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.domain.UserUpdate;
import com.depromeet.yunbeom.user.exception.CertificationCodeNotMatchedException;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;
import com.depromeet.yunbeom.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserControllerTest {
	@Test
	void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<UserResponse> result = testContainer.userController.getUserById(11);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("uiurihappy@naver.com");
		assertThat(result.getBody().getNickname()).isEqualTo("uiurihappy");
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.userController.getUserById(11);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<Void> result = testContainer.userController.verifyEmail(11, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
		assertThat(testContainer.userRepository.getById(11).getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build());

		// when
		assertThatThrownBy(() -> {
			testContainer.userController.verifyEmail(11, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}

}