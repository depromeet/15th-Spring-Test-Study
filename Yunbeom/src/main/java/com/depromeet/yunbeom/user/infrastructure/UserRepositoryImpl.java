package com.depromeet.yunbeom.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus) {
		return userJpaRepository.findByIdAndStatus(id, userStatus);
	}

	@Override
	public Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus) {
		return userJpaRepository.findByEmailAndStatus(email, userStatus);
	}

	@Override
	public Optional<UserEntity> findById(long id) {
		return userJpaRepository.findById(id);
	}

	@Override
	public UserEntity save(UserEntity userEntity) {
		return userJpaRepository.save(userEntity);
	}
}
