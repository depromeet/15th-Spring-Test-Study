package com.example.demo.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

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

}
