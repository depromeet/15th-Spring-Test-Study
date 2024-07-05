package com.depromeet.nahyeon.medium;

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
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserUpdate;
import com.depromeet.nahyeon.user.service.UserServiceImpl;

@SpringBootTest
@TestPropertySource("classpath:test-application.yml")
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceImplTest {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@MockBean
	private JavaMailSender mailSender;

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "nahyeonee99@gmail.com";

		// when
		User result = userServiceImpl.getByEmail(email);

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
			userServiceImpl.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById은_PENDING_상태인_유저는_찾아올_수_없다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userServiceImpl.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("nahyeonee99@kakao.com")
			.address("Incheon")
			.nickname("nahyeon-k")
			.build();
		BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		User result = userServiceImpl.create(userCreate);

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
		userServiceImpl.update(1, userUpdateDto);

		// then
		User user = userServiceImpl.getById(1);
		assertThat(user.getId()).isNotNull();
		assertThat(user.getAddress()).isEqualTo("Incheon");
		assertThat(user.getNickname()).isEqualTo("nahyeon-kim");
	}

	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		// given
		// when
		userServiceImpl.login(1);

		// then
		User user = userServiceImpl.getById(1);
		assertThat(user.getLastLoginAt()).isGreaterThan(0L);
		// assertThat(result.getCertificationCode()).isEqualTo("T.T"); // FIXME
	}

	@Test
	void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
		// given
		// when
		userServiceImpl.verifyEmail(2, "aaaaaaaa-aaaaaaaa-aaaaaaab");

		// then
		User user = userServiceImpl.getById(2);
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userServiceImpl.verifyEmail(2, "aaaaaaaa-aaaaaaaa-aaaaaaac");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}
}