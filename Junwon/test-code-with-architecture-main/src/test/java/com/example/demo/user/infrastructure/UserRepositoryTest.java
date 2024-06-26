package com.example.demo.user.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.infrastructure.UserRepository;

@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
@TestPropertySource("classpath:test-application.properties")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다(){
		// given

		// when

		Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();

	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다(){
		// given

		// when

		Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
		assertThat(result.isPresent()).isFalse(); // 위와 동일

	}

	@Test
	void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다(){
		// given

		// when

		Optional<UserEntity> result = userRepository.findByEmailAndStatus("kok202@naver.com", UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();

	}

	@Test
	void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다(){
		// given

		// when

		Optional<UserEntity> result = userRepository.findByEmailAndStatus("kok202@naver.com", UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
		assertThat(result.isPresent()).isFalse(); // 위와 동일

	}
}
