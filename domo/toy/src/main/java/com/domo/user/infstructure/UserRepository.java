package com.domo.user.infstructure;

import java.util.Optional;

import com.domo.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
