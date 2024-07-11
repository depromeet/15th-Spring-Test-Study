package org.example.demo.service;

import java.time.Clock;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.demo.exception.CertificationCodeNotMatchedException;
import org.example.demo.exception.ResourceNotFoundException;
import org.example.demo.model.UserStatus;
import org.example.demo.model.dto.UserCreateDto;
import org.example.demo.model.dto.UserUpdateDto;
import org.example.demo.repository.UserEntity;
import org.example.demo.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    // get은 찾아서 없으면 throw 한다는 의미가 내포되어 있음
    // 만약 find를 사용했다면, findByIdOrElseThrow 로 없으면 exception 던진다는걸 명시하는게 일반적
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public UserEntity getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    public UserEntity create(UserCreateDto userCreateDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userCreateDto.getEmail());
        userEntity.setNickname(userCreateDto.getNickname());
        userEntity.setAddress(userCreateDto.getAddress());
        userEntity.setStatus(UserStatus.PENDING);
        userEntity.setCertificationCode(UUID.randomUUID().toString()); // FIXME: UUID 랜덤이라 테스트 할 방법이 없음.
        userEntity = userRepository.save(userEntity);
        String certificationUrl = generateCertificationUrl(userEntity);
        sendCertificationEmail(userCreateDto.getEmail(), certificationUrl);
        return userEntity;
    }

    @Transactional
    public UserEntity update(long id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = getById(id);
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

    // FIXME: 이 메서드를 테스트하고 싶지만 private이라 할 수 없음! 어떻게 해야 할까?
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