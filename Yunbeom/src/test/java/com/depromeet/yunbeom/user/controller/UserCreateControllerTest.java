package com.depromeet.yunbeom.user.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.depromeet.yunbeom.user.domain.UserCreate;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	// 테스트 실행한 후에 실행합니다.
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserCreateControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private JavaMailSender mailSender;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("ybchar@kakao.com")
			.nickname("ybchar")
			.address("Pangyo")
			.build();
		BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

		// when
		// then
		mockMvc.perform(
				post("/api/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(userCreate)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.email").value("ybchar@kakao.com"))
			.andExpect(jsonPath("$.nickname").value("ybchar"))
			.andExpect(jsonPath("$.status").value("PENDING"));
	}

}