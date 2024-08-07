package com.depromeet.yunbeom.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.mock.FakeMailSender;
import com.depromeet.yunbeom.mock.FakeUserRepository;
import com.depromeet.yunbeom.mock.TestClockHolder;
import com.depromeet.yunbeom.mock.TestUuidHolder;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserCreate;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.domain.UserUpdate;
import com.depromeet.yunbeom.user.exception.CertificationCodeNotMatchedException;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;

public class UserServiceTest {

	private UserServiceImpl userService;

	@BeforeEach
	void init() {
		FakeMailSender fakeMailSender = new FakeMailSender();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		this.userService = UserServiceImpl.builder()
			.uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
			.clockHolder(new TestClockHolder(1678530673958L))
			.userRepository(fakeUserRepository)
			.certificationService(new CertificationService(fakeMailSender))
			.build();
		fakeUserRepository.save(User.builder()
			.id(1L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build());
		fakeUserRepository.save(User.builder()
			.id(2L)
			.email("ybchar@naver.com")
			.nickname("ybchar")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build());
	}

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
			userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		// when
		User result = userService.getById(1);

		// then
		assertThat(result.getNickname()).isEqualTo("uiurihappy");
	}

	@Test
	void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void userCreate_를_이용하여_유저를_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("uiurihappy@kakao.com")
			.address("Gyeongi")
			.nickname("uiurihappy-k")
			.build();

		// when
		User result = userService.create(userCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
	}

	@Test
	void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
		// given
		UserUpdate userUpdate = UserUpdate.builder()
			.address("Incheon")
			.nickname("uiurihappy-n")
			.build();

		// when
		userService.update(1, userUpdate);

		// then
		User user = userService.getById(1);
		assertThat(user.getId()).isNotNull();
		assertThat(user.getAddress()).isEqualTo("Incheon");
		assertThat(user.getNickname()).isEqualTo("uiurihappy-n");
	}

	@Test
	void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
		// given
		// when
		userService.login(1);

		// then
		User user = userService.getById(1);
		assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
	}

	@Test
	void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
		// given
		// when
		userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

		// then
		User user = userService.getById(2);
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}

}