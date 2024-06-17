package com.depromeet.yunbeom.service;

import java.time.Clock;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.yunbeom.exception.CertificationCodeNotMatchedException;
import com.depromeet.yunbeom.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.model.UserStatus;
import com.depromeet.yunbeom.model.dto.UserCreateDto;
import com.depromeet.yunbeom.model.dto.UserUpdateDto;
import com.depromeet.yunbeom.repository.UserEntity;
import com.depromeet.yunbeom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public Optional<UserEntity> getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE);
    }

    public UserEntity getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public UserEntity getByIdOrElseThrow(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    public UserEntity createUser(UserCreateDto userCreateDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userCreateDto.getEmail());
        userEntity.setNickname(userCreateDto.getNickname());
        userEntity.setAddress(userCreateDto.getAddress());
        userEntity.setStatus(UserStatus.PENDING);
        userEntity.setCertificationCode(UUID.randomUUID().toString());
        userEntity = userRepository.save(userEntity);
        String certificationUrl = generateCertificationUrl(userEntity);
        sendCertificationEmail(userCreateDto.getEmail(), certificationUrl);
        return userEntity;
    }

    @Transactional
    public UserEntity updateUser(long id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = getByIdOrElseThrow(id);
        userEntity.setNickname(userUpdateDto.getNickname());
        userEntity.setAddress(userUpdateDto.getAddress());
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    @Transactional
    public void login(long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        userEntity.setLastLoginAt(Clock.systemUTC().millis());
    }

    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
        if (!certificationCode.equals(userEntity.getCertificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }
        userEntity.setStatus(UserStatus.ACTIVE);
    }

    private void sendCertificationEmail(String email, String certificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please certify your email address");
        message.setText("Please click the following link to certify your email address: " + certificationUrl);
        mailSender.send(message);
    }

    private String generateCertificationUrl(UserEntity userEntity) {
        return "http://localhost:8080/api/users/" + userEntity.getId() + "/verify?certificationCode=" + userEntity.getCertificationCode();
    }
}