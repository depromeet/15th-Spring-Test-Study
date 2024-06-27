package com.depromeet.yunbeom.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.domain.UserUpdate;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;
import com.depromeet.yunbeom.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	// 테스트 실행하기 전에 실행합니다.
	@Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	// 테스트 실행한 후에 실행합니다.
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void 사용자는_특정_유저의_정보_전달받을_수_있다() throws Exception {
		// given

		// when

		// then
		mockMvc.perform(get("/api/users/{id}", 11))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(11))
			.andExpect(jsonPath("$.email").value("uiurihappy@naver.com"))
			.andExpect(jsonPath("$.nickname").value("uiurihappy"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/users/123456789"))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Users에서 ID 123456789를 찾을 수 없습니다."));
	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(
				get("/api/users/{userId}/verify", 12)
					.queryParam("certificationCode", "1234asda-1321dada-12dascascet"))
			.andExpect(status().isFound());
		UserEntity userEntity = userJpaRepository.findById(12L).get();
		assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(
				get("/api/users/{userId}/verify", 12)
					.queryParam("certificationCode", "1234asda-1321dada-12dasca213scet"))
			.andExpect(status().isForbidden());
	}

	@Test
	void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(
				get("/api/users/me")
					.header("EMAIL", "uiurihappy@naver.com"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(11))
			.andExpect(jsonPath("$.email").value("uiurihappy@naver.com"))
			.andExpect(jsonPath("$.nickname").value("uiurihappy"))
			.andExpect(jsonPath("$.address").value("Seoul"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

	@Test
	void 사용자는_내_정보를_수정할_수_있다() throws Exception {
		// given
		UserUpdate userUpdate = UserUpdate.builder()
			.nickname("ybchar-n")
			.address("Pangyo")
			.build();

		// when
		// then
		mockMvc.perform(
				put("/api/users/me")
					.header("EMAIL", "uiurihappy@naver.com")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userUpdate)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(11))
			.andExpect(jsonPath("$.email").value("uiurihappy@naver.com"))
			.andExpect(jsonPath("$.nickname").value("ybchar-n"))
			.andExpect(jsonPath("$.address").value("Pangyo"))
			.andExpect(jsonPath("$.status").value("ACTIVE"));
	}

}