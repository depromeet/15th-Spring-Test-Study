package com.depromeet.nahyeon.user.service.port;

import java.util.Optional;

import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.infrastructure.UserEntity;

public interface UserRepository {

	Optional<UserEntity> findById(long id);

	Optional<UserEntity> findByIdAndStatus(long id, UserStatus status);

	Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status);

	UserEntity save(UserEntity userEntity);
}