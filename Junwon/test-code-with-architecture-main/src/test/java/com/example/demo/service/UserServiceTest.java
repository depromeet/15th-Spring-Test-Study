package com.example.demo.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserStatus;
import com.example.demo.model.dto.UserCreateDto;
import com.example.demo.model.dto.UserUpdateDto;
import com.example.demo.repository.UserEntity;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private JavaMailSender javaMailSender;

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

	@Test
	void userCreateDto_를_이용하여_유저를_생성할_수_있다(){
		// given
		UserCreateDto userCreateDto = UserCreateDto.builder()
			.email("kok202@kakao.com")
			.address("Gyeongi")
			.nickname("kok202-k")
			.build();

		BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

		// when
		UserEntity result = userService.create(userCreateDto);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
		// assertThat(result.getCertificationCode()).isEqualTo("T.T");
	}

	@Test
	void userUpdateDto_를_이용하여_유저를_수정할_수_있다(){
		// given
		UserUpdateDto userUpdateDto = UserUpdateDto.builder()
			.address("Incheon")
			.nickname("kok202-n")
			.build();

		// when
		userService.update(1, userUpdateDto);

		// then
		UserEntity userEntity = userService.getById(1);
		assertThat(userEntity.getId()).isNotNull();
		assertThat(userEntity.getAddress()).isEqualTo("Incheon");
		assertThat(userEntity.getNickname()).isEqualTo("kok202-n");
	}

}
