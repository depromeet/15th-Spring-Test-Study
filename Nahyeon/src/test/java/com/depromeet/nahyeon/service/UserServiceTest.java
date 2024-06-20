package com.depromeet.nahyeon.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.depromeet.nahyeon.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.repository.UserEntity;

@SpringBootTest
@TestPropertySource("classpath:test-application.yml")
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "nahyeonee99@gmail.com";

		// when
		UserEntity result = userService.getByEmail(email);

		// then
		Assertions.assertThat(result.getNickname()).isEqualTo("nahyeonee99");
	}

	@Test
	void getByEmail은_PENDING_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "nahyeonee100@gmail.com";

		// when
		// then
		Assertions.assertThatThrownBy(() -> {
			userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById은_PENDING_상태인_유저는_찾아올_수_없다() {
		// given
		// when
		// then
		Assertions.assertThatThrownBy(() -> {
			UserEntity result = userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);
	}
}