package com.depromeet.yunbeom.medium.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.exception.CertificationCodeNotMatchedException;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.domain.UserCreate;
import com.depromeet.yunbeom.user.domain.UserUpdate;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;
import com.depromeet.yunbeom.user.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
/** SQLGroup
 * SQLGroup은 테스트가 실행되기 전에 SQL 파일을 실행하거나 테스트가 끝난 후에 SQL 파일을 실행할 수 있도록 지원하는 어노테이션입니다.
 */
@SqlGroup({
	// 테스트 실행하기 전에 실행합니다.
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
	// 테스트 실행한 후에 실행합니다.
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

	@Autowired
	private UserService userService;
	@MockBean
	private JavaMailSender mailSender;

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "uiurihappy@naver.com";

		// when
		User result = userService.getByEmail(email);
		// then
		assertThat(result.getNickname()).isEqualTo("uiurihappy");
	}

	@Test
	void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
		// given
		String email = "ybchar@naver.com";

		// when
		// then
		assertThatThrownBy(() -> {
			User result = userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		// when
		User result = userService.getById(11);

		// then
		assertThat(result.getNickname()).isEqualTo("uiurihappy");
	}

	@Test
	void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			User result = userService.getById(12);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("ybchar@naver.com")
			.address("Seoul")
			.nickname("ybchar-k")
			.build();
		BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		User result = userService.create(userCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
	}

	@Test
	void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
		// given
		UserUpdate userUpdate = UserUpdate.builder()
			.address("Incheon")
			.nickname("uiurihappy-n")
			.build();

		// when
		userService.update(11, userUpdate);

		// then
		User userEntity = userService.getById(11);
		assertThat(userEntity.getId()).isNotNull();
		assertThat(userEntity.getAddress()).isEqualTo("Incheon");
		assertThat(userEntity.getNickname()).isEqualTo("uiurihappy-n");
	}

	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		// given
		// when
		userService.login(11);

		// then
		User userEntity = userService.getById(11);
		// 0 이상의 값이 나와야 한다.
		assertThat(userEntity.getLastLoginAt()).isGreaterThan(0L);
	}

	@Test
	void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
		// given
		// when
		userService.verifyEmail(12, "1234asda-1321dada-12dascascet");

		// then
		User userEntity = userService.getById(12);
		assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.verifyEmail(12, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}

}