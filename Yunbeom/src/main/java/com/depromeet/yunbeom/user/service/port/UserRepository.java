package com.depromeet.yunbeom.user.service.port;

import java.util.Optional;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;

public interface UserRepository {

	User getById(long id);

	Optional<User> findById(long id);

	Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

	Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

	User save(User user);
}
