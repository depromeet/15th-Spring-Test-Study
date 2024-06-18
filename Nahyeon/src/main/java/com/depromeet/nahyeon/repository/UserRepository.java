package com.depromeet.nahyeon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.nahyeon.model.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByIdAndStatus(long id, UserStatus status);

	Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status);
}
