package com.depromeet.yunbeom.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.user.exception.CertificationCodeNotMatchedException;

class UserTest {

	@Test
	public void UserCreate_객체로_생성할_수_있다() {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("ybchar@kakao.com")
			.nickname("ybchar")
			.address("Pangyo")
			.build();

		// when
		User user = User.from(userCreate);

		// then
		assertThat(user.getId()).isNull();
		assertThat(user.getEmail()).isEqualTo("ybchar@kakao.com");
		assertThat(user.getNickname()).isEqualTo("ybchar");
		assertThat(user.getAddress()).isEqualTo("Pangyo");
		assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	}

	@Test
	public void UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("ybchar@kakao.com")
			.nickname("ybchar")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.build();
		UserUpdate userUpdate = UserUpdate.builder()
			.nickname("ybchar-k")
			.address("Pangyo")
			.build();

		// when
		user = user.update(userUpdate);

		// then
		assertThat(user.getId()).isEqualTo(1L);
		assertThat(user.getEmail()).isEqualTo("ybchar@kakao.com");
		assertThat(user.getNickname()).isEqualTo("ybchar-k");
		assertThat(user.getAddress()).isEqualTo("Pangyo");
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
		assertThat(user.getLastLoginAt()).isEqualTo(100L);
	}

	@Test
	public void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("ybchar@kakao.com")
			.nickname("ybchar")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.build();

		// when
		user = user.login();

		// then
		assertThat(user.getLastLoginAt()).isNotEqualTo(100L);
		
	}

	@Test
	public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("ybchar@kakao.com")
			.nickname("ybchar")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.build();

		// when
		user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// then
		assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	public void 잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
		// given
		User user = User.builder()
			.id(1L)
			.email("ybchar@kakao.com")
			.nickname("ybchar")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.lastLoginAt(100L)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.build();

		// when
		// then
		assertThatThrownBy(() -> user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
			.isInstanceOf(CertificationCodeNotMatchedException.class);
	}

}