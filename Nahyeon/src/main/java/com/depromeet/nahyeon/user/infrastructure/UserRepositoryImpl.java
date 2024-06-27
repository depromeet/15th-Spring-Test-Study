package com.depromeet.nahyeon.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<UserEntity> findById(long id) {
		return userJpaRepository.findById(id);
	}

	@Override
	public Optional<UserEntity> findByIdAndStatus(long id, UserStatus status) {
		return userJpaRepository.findByIdAndStatus(id, status);
	}

	@Override
	public Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status) {
		return userJpaRepository.findByEmailAndStatus(email, status);
	}

	@Override
	public UserEntity save(UserEntity userEntity) {
		return userJpaRepository.save(userEntity);
	}
}
