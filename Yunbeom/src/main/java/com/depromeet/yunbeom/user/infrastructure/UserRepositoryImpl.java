package com.depromeet.yunbeom.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository userJpaRepository;

	@Override
	public User getById(long id) {
		return userJpaRepository.findById(id).map(UserEntity::toModel).orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Override
	public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
		return userJpaRepository.findByIdAndStatus(id, userStatus).map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
		return userJpaRepository.findByEmailAndStatus(email, userStatus).map(UserEntity::toModel);
	}

	@Override
	public Optional<User> findById(long id) {
		return userJpaRepository.findById(id).map(UserEntity::toModel);
	}

	@Override
	public User save(User user) {
		// 도메인은 인프라 레이어의 정보를 모르는 것이 좋다.(캡슐화)
		return userJpaRepository.save(UserEntity.from(user)).toModel();
	}
}
