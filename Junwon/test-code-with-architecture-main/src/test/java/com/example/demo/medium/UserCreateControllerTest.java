package com.example.demo.medium;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

import com.example.demo.user.domain.UserCreate;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserCreateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JavaMailSender javaMailSender;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void 사용자는_회원_가입을_할_수_있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
		// given
		UserCreate userCreate = UserCreate.builder()
			.email("kok202@naver.com")
			.nickname("kok202")
			.address("Pangyo").build();
		BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

		// when
		// then
		mockMvc.perform(post("/api/users")
				.header("EMAIL", "kok202@naver.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userCreate)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.email").value("kok202@naver.com"))
			.andExpect(jsonPath("$.nickname").value("kok202"))
			.andExpect(jsonPath("$.status").value("PENDING"));
	}
}
