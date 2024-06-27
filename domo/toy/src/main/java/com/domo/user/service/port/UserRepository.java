package com.domo.user.service.port;

import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import com.domo.user.infstructure.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long id);
    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);
}
