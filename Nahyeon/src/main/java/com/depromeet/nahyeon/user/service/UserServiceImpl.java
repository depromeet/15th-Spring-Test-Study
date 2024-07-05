package com.depromeet.nahyeon.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.common.service.port.UuidHolder;
import com.depromeet.nahyeon.user.controller.port.AuthenticationService;
import com.depromeet.nahyeon.user.controller.port.UserCreateService;
import com.depromeet.nahyeon.user.controller.port.UserReadService;
import com.depromeet.nahyeon.user.controller.port.UserUpdateService;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserUpdate;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserCreateService, UserReadService, UserUpdateService, AuthenticationService {

	private final UserRepository userRepository;
	private final CertificationService certificationService;
	private final UuidHolder uuidHolder;
	private final ClockHolder clockHolder;

	@Override
	public User getById(long id) {
		return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
			.orElseThrow(() -> new ResourceNotFoundException("Users", email));
	}

	@Override
	@Transactional
	public User create(UserCreate userCreate) {
		User user = User.from(userCreate, uuidHolder);
		user = userRepository.save(user);
		certificationService.send(user.getEmail(), user.getId(), user.getCertificationCode());
		return user;
	}

	@Override
	@Transactional
	public User update(long id, UserUpdate userUpdate) {
		User user = getById(id);
		user = user.update(userUpdate);
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void login(long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
		user = user.login(clockHolder);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void verifyEmail(long id, String certificationCode) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Users", id));
		user = user.certificate(certificationCode);
		userRepository.save(user);
	}
}
