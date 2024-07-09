package com.domo.user.infstructure;

import com.domo.common.domain.exception.ResourceNotFoundException;
import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import com.domo.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndStatus(id, userStatus).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndStatus(email, userStatus).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public User getById(long id) {
        return userJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id)).toDomain();
    }
}
