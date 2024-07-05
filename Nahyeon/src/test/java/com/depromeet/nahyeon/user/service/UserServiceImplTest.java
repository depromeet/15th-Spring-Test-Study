package com.depromeet.nahyeon.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.common.domain.exception.CertificationCodeNotMatchedException;
import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.mock.FakeMailSender;
import com.depromeet.nahyeon.mock.FakeUserRepository;
import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.mock.TestUuidHolder;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserUpdate;

class UserServiceImplTest {

	private UserServiceImpl userServiceImpl;

	@BeforeEach
	void init() {
		FakeMailSender fakeMailSender = new FakeMailSender();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		this.userServiceImpl = UserServiceImpl.builder()
			.certificationService(new CertificationService(fakeMailSender))
			.userRepository(fakeUserRepository)
			.uuidHolder(new TestUuidHolder("aaaaaaaa-aaaaaaaa-aaaaaaab"))
			.clockHolder(new TestClockHolder(4000))
			.build();
		fakeUserRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build());
		fakeUserRepository.save(User.builder()
			.id(2L)
			.email("nahyeonee100@gmail.com")
			.nickname("nahyeonee100")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaab")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build());
	}

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
	void userCreate_를_이용하여_유저를_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("nahyeonee99@kakao.com")
			.address("Incheon")
			.nickname("nahyeon-k")
			.build();

		// when
		User result = userServiceImpl.create(userCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaaab");
	}

	@Test
	void UserUpdate_를_이용하여_유저를_수정할_수_있다() {
		// given
		UserUpdate userUpdate = UserUpdate.builder()
			.address("Incheon")
			.nickname("nahyeon-kim")
			.build();

		// when
		userServiceImpl.update(1, userUpdate);

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
		assertThat(user.getLastLoginAt()).isEqualTo(4000L);
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