package com.depromeet.nahyeon.user.infrastructure;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "address")
	private String address;

	@Column(name = "certification_code")
	private String certificationCode;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Column(name = "last_login_at")
	private Long lastLoginAt;

	public static UserEntity from(User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(user.getId());
		userEntity.setEmail(user.getEmail());
		userEntity.setNickname(user.getNickname());
		userEntity.setAddress(user.getAddress());
		userEntity.setCertificationCode(user.getCertificationCode());
		userEntity.setStatus(user.getStatus());
		userEntity.setLastLoginAt(user.getLastLoginAt());
		return userEntity;
	}

	public User toModel() {
		return User.builder()
			.id(id)
			.email(email)
			.nickname(nickname)
			.address(address)
			.certificationCode(certificationCode)
			.status(status)
			.lastLoginAt(lastLoginAt)
			.build();
	}
}
