package com.example.demo.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserEntity;

@SpringBootTest
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	void getByEmail_은_ACTIVE_상태인_유저를_찾아올_수_있다(){
		// given
		String email = "kok202@naver.com";

		// when
		UserEntity result = userService.getByEmail(email);

		// then
		assertThat(result.getNickname()).isEqualTo("kok202");
	}

	@Test
	void getByEmail_은_PENDING_상태인_유저를_찾아올_수_없다(){
		// given
		String email = "kok303@naver.com";

		// when
		// then
		assertThatThrownBy(() -> {
			UserEntity result = userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void getById_는_ACTIVE_상태인_유저를_찾아올_수_있다(){
		// given
		// when
		UserEntity result = userService.getById(1);

		// then
		assertThat(result.getNickname()).isEqualTo("kok202");
	}

	@Test
	void getById_는_PENDING_상태인_유저를_찾아올_수_없다(){
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			UserEntity result = userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);
	}
}
