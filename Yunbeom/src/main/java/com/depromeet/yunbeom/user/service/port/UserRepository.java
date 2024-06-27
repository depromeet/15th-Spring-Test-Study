package com.depromeet.yunbeom.user.service.port;

import java.util.Optional;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;

public interface UserRepository {
	Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

	Optional<User> findById(long id);

	User save(User user);
}
