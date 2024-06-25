package com.depromeet.yunbeom.user.service.port;

import java.util.Optional;

import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;

public interface UserRepository {
	Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

	Optional<UserEntity> findById(long id);

	UserEntity save(UserEntity userEntity);
}
