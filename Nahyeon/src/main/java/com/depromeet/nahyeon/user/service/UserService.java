package com.depromeet.nahyeon.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserUpdate;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final CertificationService certificationService;

	public User getById(long id) {
		return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	public User getByEmail(String email) {
		return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
			.orElseThrow(() -> new ResourceNotFoundException("Users", email));
	}

	@Transactional
	public User create(UserCreate userCreate) {
		User user = User.from(userCreate);
		user = userRepository.save(user);
		certificationService.send(user.getEmail(), user.getId(), user.getCertificationCode());
		return user;
	}

	@Transactional
	public User update(long id, UserUpdate userUpdate) {
		User user = getById(id);
		user = user.update(userUpdate);
		return userRepository.save(user);
	}

	@Transactional
	public void login(long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
		user = user.login();
		userRepository.save(user);
	}

	@Transactional
	public void verifyEmail(long id, String certificationCode) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
		user = user.certificate(certificationCode);
		userRepository.save(user);
	}
}
