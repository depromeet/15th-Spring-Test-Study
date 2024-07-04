package com.depromeet.yunbeom.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.user.service.port.UserRepository;

public class FakeUserRepository implements UserRepository {

	private final AtomicLong autoIncrementId = new AtomicLong(0);
	/**
	 * 소형 테스트는 단일 스레드에서 실행되기에 동기화를 걱정할 필요가 없다
	 */
	// private final List<User> data = Collections.syncronizedList(new ArrayList<>());
	private final List<User> data = new ArrayList<>();

	@Override
	public User getById(long id) {
		return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
	}

	@Override
	public Optional<User> findById(long id) {
		// list에서 Id값이 일치하는 것을 찾아서 반환
		return data.stream().filter(item -> item.getId().equals(id)).findAny();
	}

	@Override
	public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
		return data.stream().filter(item -> item.getId().equals(id) && item.getStatus() == userStatus).findAny();
	}

	@Override
	public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
		return data.stream().filter(item -> item.getEmail().equals(email) && item.getStatus() == userStatus).findAny();
	}

	@Override
	public User save(User user) {
		if (user.getId() == null || user.getId() == 0) {
			User newUser = User.builder()
				.id(autoIncrementId.incrementAndGet())
				.email(user.getEmail())
				.nickname(user.getNickname())
				.address(user.getAddress())
				.certificationCode(user.getCertificationCode())
				.status(user.getStatus())
				.lastLoginAt(user.getLastLoginAt())
				.build();
			data.add(newUser);
			return newUser;
		} else {
			data.removeIf(item -> Objects.equals(item.getId(), user.getId()));
			data.add(user);
			return user;
		}
	}
}
