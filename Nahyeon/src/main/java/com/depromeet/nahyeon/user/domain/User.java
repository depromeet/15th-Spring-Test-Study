package com.depromeet.nahyeon.user.domain;

import com.depromeet.nahyeon.common.domain.exception.CertificationCodeNotMatchedException;
import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.common.service.port.UuidHolder;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

	private final Long id;
	private final String email;
	private final String nickname;
	private final String address;
	private final String certificationCode;
	private final UserStatus status;
	private final Long lastLoginAt;

	@Builder
	public User(Long id, String email, String nickname, String address, String certificationCode, UserStatus status,
		Long lastLoginAt) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.address = address;
		this.certificationCode = certificationCode;
		this.status = status;
		this.lastLoginAt = lastLoginAt;
	}

	public static User from(UserCreate userCreate, UuidHolder uuidHolder) {
		return User.builder()
			.email(userCreate.getEmail())
			.nickname(userCreate.getNickname())
			.address(userCreate.getAddress())
			.status(UserStatus.PENDING)
			.certificationCode(uuidHolder.random())
			.build();
	}

	public User update(UserUpdate userUpdate) {
		return User.builder()
			.id(id)
			.email(email)
			.nickname(userUpdate.getNickname())
			.address(userUpdate.getAddress())
			.status(status)
			.certificationCode(certificationCode)
			.lastLoginAt(lastLoginAt)
			.build();
	}

	public User login(ClockHolder clockHolder) {
		return User.builder()
			.id(id)
			.email(email)
			.nickname(nickname)
			.address(address)
			.status(status)
			.certificationCode(certificationCode)
			.lastLoginAt(clockHolder.millis())
			.build();
	}

	public User certificate(String certificationCode) {
		if (!this.certificationCode.equals(certificationCode)) {
			throw new CertificationCodeNotMatchedException();
		}

		return User.builder()
			.id(id)
			.email(email)
			.nickname(nickname)
			.address(address)
			.status(UserStatus.ACTIVE)
			.certificationCode(this.certificationCode)
			.lastLoginAt(lastLoginAt)
			.build();
	}
}
