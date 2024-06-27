package com.depromeet.nahyeon.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.common.domain.exception.CertificationCodeNotMatchedException;
import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.mock.TestUuidHolder;

class UserTest {

	@Test
	public void User는_UserCreate_객체로_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("nahyeonee99@kakao.com")
			.address("Incheon")
			.nickname("nahyeon-k")
			.build();

		// when
		User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaaaaaa-aaaaaaaa"));

		// then
		assertThat(user.getId()).isNull();
		assertThat(user.getEmail()).isEqualTo("nahyeonee99@kakao.com");
		assertThat(user.getNickname()).isEqualTo("nahyeon-k");
		assertThat(user.getAddress()).isEqualTo("Incheon");
		assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaaaa");
	}

	@Test
	public void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
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
		UserUpdate userUpdate = UserUpdate.builder()
			.nickname("hy")
			.address("Busan")
			.build();

		// when
		user = user.update(userUpdate);

		// then
		assertThat(user.getId()).isEqualTo(1);
		assertThat(user.getEmail()).isEqualTo("nahyeon@gmail.com");
		assertThat(user.getNickname()).isEqualTo("hy");
		assertThat(user.getAddress()).isEqualTo("Busan");
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaaaa");
		assertThat(user.getLastLoginAt()).isEqualTo(100L);
	}

	@Test
	public void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
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
		user = user.login(new TestClockHolder(4000));

		// then
		assertThat(user.getId()).isEqualTo(1);
		assertThat(user.getEmail()).isEqualTo("nahyeon@gmail.com");
		assertThat(user.getNickname()).isEqualTo("nahyeon");
		assertThat(user.getAddress()).isEqualTo("Seoul");
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaaaa");
		assertThat(user.getLastLoginAt()).isEqualTo(4000);
	}

	@Test
	public void User는_유효한_인증_코드로_계정을_활성화_할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("nahyeon@gmail.com")
			.nickname("nahyeon")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
			.build();

		// when
		user = user.certificate("aaaaaaaa-aaaaaaaa-aaaaaaaa");

		// then
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	public void User는_잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("nahyeon@gmail.com")
			.nickname("nahyeon")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
			.build();

		// when
		// then
		assertThatThrownBy(() -> user.certificate("aaaaaaaa-aaaaaaaa-aaaaaaab"))
			.isInstanceOf(CertificationCodeNotMatchedException.class);
	}
}