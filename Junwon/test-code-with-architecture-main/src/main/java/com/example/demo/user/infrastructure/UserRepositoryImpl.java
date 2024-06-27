package com.example.demo.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private  final UserJpaRepository userJpaRepository;

	@Override
	public Optional<UserEntity> findById(long id) {
		return userJpaRepository.findById(id);
	}

	@Override
	public Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus) {
		return userJpaRepository.findByIdAndStatus(id, userStatus);
	}

	@Override
	public Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus) {
		return userJpaRepository.findByEmailAndStatus(email, userStatus);
	}

	@Override
	public UserEntity save(UserEntity userEntity) {
		return userJpaRepository.save(userEntity);
	}
}
