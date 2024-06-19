package com.example.demo.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.UserStatus;

@DataJpaTest(showSql = true)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void UserRepository_가_제대로_연결되었다(){
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("study7823@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("study7823");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// when
		UserEntity result = userRepository.save(userEntity);

		// then
		assertThat(result.getId()).isNotNull();
	}

	@Test
	void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다(){
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("study7823@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("study7823");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// when
		userRepository.save(userEntity);
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();

	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다(){
		// given
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("study7823@gmail.com");
		userEntity.setAddress("Seoul");
		userEntity.setNickname("study7823");
		userEntity.setStatus(UserStatus.ACTIVE);
		userEntity.setCertificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// when
		userRepository.save(userEntity);
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
		assertThat(result.isPresent()).isFalse(); // 위와 동일

	}

}
