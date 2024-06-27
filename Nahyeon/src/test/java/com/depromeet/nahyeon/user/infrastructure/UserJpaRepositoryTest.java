package com.depromeet.nahyeon.user.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.depromeet.nahyeon.user.domain.UserStatus;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.yml")
@Sql("/sql/user-repository-test-data.sql")
public class UserJpaRepositoryTest {

	@Autowired
	private UserJpaRepository userRepository;

	@Test
	void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
		// given
		// when
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// given
		// when
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
		// given
		// when
		Optional<UserEntity> result = userRepository.findByEmailAndStatus("nahyeonee99@gmail.com", UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// given
		// when
		Optional<UserEntity> result = userRepository.findByEmailAndStatus("nahyeonee99@gmail.com", UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();
	}
}
