package com.depromeet.nahyeon.repository;

import com.depromeet.nahyeon.model.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
	@GeneratedValue
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
}
