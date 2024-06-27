package com.depromeet.nahyeon.user.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.nahyeon.user.domain.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByIdAndStatus(long id, UserStatus status);

	Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status);
}
