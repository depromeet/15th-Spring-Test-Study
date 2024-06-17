package com.depromeet.yunbeom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.yunbeom.model.UserStatus;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
