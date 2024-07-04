package com.depromeet.nahyeon.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User getById(long id) {
		return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Override
	public Optional<User> findById(long id) {
		return userJpaRepository.findById(id).map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByIdAndStatus(long id, UserStatus status) {
		return userJpaRepository.findByIdAndStatus(id, status).map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByEmailAndStatus(String email, UserStatus status) {
		return userJpaRepository.findByEmailAndStatus(email, status).map(UserEntity::toModel);
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
	}
}
