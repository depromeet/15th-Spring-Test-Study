package com.depromeet.nahyeon.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.depromeet.nahyeon.model.UserStatus;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void UserRepository_가_제대로_연결되었다() {
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("nahyeonee99@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("nahyeonee99");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaa-aaaaa-aaaaa-aaaaa");

		// when
		UserEntity result = userRepository.save(userEntity);

		// then
		assertThat(result.getId()).isNotNull();
	}

	@Test
	void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("nahyeonee99@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("nahyeonee99");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaa-aaaaa-aaaaa-aaaaa");

		// when
		userRepository.save(userEntity);
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("nahyeonee99@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("nahyeonee99");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaa-aaaaa-aaaaa-aaaaa");

		// when
		userRepository.save(userEntity);
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("nahyeonee99@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("nahyeonee99");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaa-aaaaa-aaaaa-aaaaa");

		// when
		userRepository.save(userEntity);
		Optional<UserEntity> result = userRepository.findByEmailAndStatus("nahyeonee99@gmail.com", UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("nahyeonee99@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("nahyeonee99");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaa-aaaaa-aaaaa-aaaaa");

		// when
		userRepository.save(userEntity);
		Optional<UserEntity> result = userRepository.findByEmailAndStatus("nahyeonee99@gmail.com", UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
	}
}
