package com.depromeet.nahyeon.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.depromeet.nahyeon.common.domain.exception.CertificationCodeNotMatchedException;
import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserUpdate;
import com.depromeet.nahyeon.user.infrastructure.UserEntity;
import com.depromeet.nahyeon.user.service.UserService;

@SpringBootTest
@TestPropertySource("classpath:test-application.yml")
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private JavaMailSender mailSender;

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "nahyeonee99@gmail.com";

		// when
		UserEntity result = userService.getByEmail(email);

		// then
		assertThat(result.getNickname()).isEqualTo("nahyeonee99");
	}

	@Test
	void getByEmail은_PENDING_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "nahyeonee100@gmail.com";

		// when
		// then
		assertThatThrownBy(() -> {
			userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById은_PENDING_상태인_유저는_찾아올_수_없다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
		// given
		UserCreate userCreateDto = UserCreate.builder()
			.email("nahyeonee99@kakao.com")
			.address("Incheon")
			.nickname("nahyeon-k")
			.build();
		BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		UserEntity result = userService.create(userCreateDto);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
		// assertThat(result.getCertificationCode()).isEqualTo("T.T");
	}

	@Test
	void UserUpdateDto_를_이용하여_유저를_수정할_수_있다() {
		// given
		UserUpdate userUpdateDto = UserUpdate.builder()
			.address("Incheon")
			.nickname("nahyeon-kim")
			.build();
		BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		userService.update(1, userUpdateDto);

		// then
		UserEntity userEntity = userService.getById(1);
		assertThat(userEntity.getId()).isNotNull();
		assertThat(userEntity.getAddress()).isEqualTo("Incheon");
		assertThat(userEntity.getNickname()).isEqualTo("nahyeon-kim");
	}

	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		// given
		// when
		userService.login(1);

		// then
		UserEntity userEntity = userService.getById(1);
		assertThat(userEntity.getLastLoginAt()).isGreaterThan(0L);
		// assertThat(result.getCertificationCode()).isEqualTo("T.T"); // FIXME
	}

	@Test
	void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
		// given
		// when
		userService.verifyEmail(2, "aaaaaaaa-aaaaaaaa-aaaaaaab");

		// then
		UserEntity userEntity = userService.getById(2);
		assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.verifyEmail(2, "aaaaaaaa-aaaaaaaa-aaaaaaac");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}
}