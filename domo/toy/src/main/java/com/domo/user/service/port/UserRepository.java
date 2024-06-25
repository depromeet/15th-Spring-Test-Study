package com.domo.user.service.port;

import com.domo.user.domain.UserStatus;
import com.domo.user.infstructure.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findById(long id);
    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

    UserEntity save(UserEntity userEntity);
}
