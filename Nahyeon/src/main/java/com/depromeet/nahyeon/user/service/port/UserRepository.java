package com.depromeet.nahyeon.user.service.port;

import java.util.Optional;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public interface UserRepository {

	Optional<User> findById(long id);

	Optional<User> findByIdAndStatus(long id, UserStatus status);

	Optional<User> findByEmailAndStatus(String email, UserStatus status);

	User save(User user);
}