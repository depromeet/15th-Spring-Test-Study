package com.depromeet.nahyeon.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserUpdate;
import com.depromeet.nahyeon.user.infrastructure.UserEntity;
import com.depromeet.nahyeon.user.infrastructure.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestPropertySource("classpath:test-application.yml")
@SqlGroup({
	@Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void 사용자는_특정_유저의_정보를_개인정보는_소거된_채_전달_받을_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("nahyeonee99@gmail.com"))
			.andExpect(jsonPath("$.nickname").value("nahyeonee99"))
			.andExpect(jsonPath("$.address").doesNotExist())
			.andExpect(jsonPath("$.status").value(
				"ACTIVE")); // result = {"id":1,"email":"nahyeonee99@gmail.com","nickname":"nahyeonee99","status":null,"lastLoginAt":0} FIXME
	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/13234234"))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Users에서 ID 13234234를 찾을 수 없습니다."));
	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/2/verify")
				.queryParam("certificationCode", "aaaaaaaa-aaaaaaaa-aaaaaaab"))
			.andExpect(status().isFound());

		UserEntity userEntity = userRepository.findById(1L).get();
		assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않을_꼉우_권한_없음_에러를_내려준다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/2/verify")
				.queryParam("certificationCode", "aaaaaaaa-aaaaaaaa-aaaaaacc"))
			.andExpect(status().isForbidden());
	}

	@Test
	void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/me")
				.header("EMAIL", "nahyeonee99@gmail.com"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("nahyeonee99@gmail.com"))
			.andExpect(jsonPath("$.nickname").value("nahyeonee99"))
			.andExpect(jsonPath("$.address").value("Seoul"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@Test
	void 사용자는_내_정보를_수정할_수_있다() throws Exception {
		// given
		UserUpdate userUpdateDto = UserUpdate.builder()
			.nickname("nahyeon-n")
			.address("Pangyo")
			.build();

		// when
		// then
		mockMvc.perform(put("/api/users/me")
				.header("EMAIL", "nahyeonee99@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("nahyeonee99@gmail.com"))
			.andExpect(jsonPath("$.nickname").value("nahyeon-n"))
			.andExpect(jsonPath("$.address").value("Pangyo"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}
}