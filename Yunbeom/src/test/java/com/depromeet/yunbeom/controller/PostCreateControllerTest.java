package com.depromeet.yunbeom.controller;

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

import com.depromeet.yunbeom.post.domain.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
	// 테스트 실행하기 전에 실행합니다.
	@Sql(value = "/sql/post-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	// 테스트 실행한 후에 실행합니다.
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostCreateControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void 사용자는_게시물을_작성할_수_있다() throws Exception {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1)
			.content("helloworld")
			.build();

		// when
		// then
		mockMvc.perform(
				post("/api/posts")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(postCreate)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("helloworld"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("uiurihappy@naver.com"))
			.andExpect(jsonPath("$.writer.nickname").value("uiurihappy"));
	}

}