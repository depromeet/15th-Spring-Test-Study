package com.depromeet.yunbeom.post.controller;

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

import com.depromeet.yunbeom.post.domain.PostUpdate;
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
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void 사용자는_게시물을_단건_조회_할_수_있다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/posts/11"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("helloworld"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("uiurihappy@naver.com"))
			.andExpect(jsonPath("$.writer.nickname").value("uiurihappy"));
	}

	@Test
	void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/posts/123456789"))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Posts에서 ID 123456789를 찾을 수 없습니다."));
	}

	@Test
	void 사용자는_게시물을_수정할_수_있다() throws Exception {
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("foobar")
			.build();

		// when
		// then
		mockMvc.perform(
				put("/api/posts/11")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(postUpdate)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("foobar"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("uiurihappy@naver.com"))
			.andExpect(jsonPath("$.writer.nickname").value("uiurihappy"));
	}
}