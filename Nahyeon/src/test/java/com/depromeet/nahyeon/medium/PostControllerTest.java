package com.depromeet.nahyeon.medium;

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

import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestPropertySource("classpath:test-application.yml")
@SqlGroup({
	@Sql(value = "/sql/post-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
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
		mockMvc.perform(get("/api/posts/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.content").value("test 1"))
			.andExpect(jsonPath("$.writer.id").isNumber());
	}

	@Test
	void 사용자는_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/posts/100"))
			.andExpect(status().isNotFound())
			.andExpect(content().string("Posts에서 ID 100를 찾을 수 없습니다."));
	}

	@Test
	void 사용자는_게시물을_수정할_수_있다() throws Exception {
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("updated content :)")
			.build();

		// when
		// then
		mockMvc.perform(put("/api/posts/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postUpdate)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.content").value("updated content :)"))
			.andExpect(jsonPath("$.writer.id").isNumber())
			.andExpect(jsonPath("$.writer.email").value("nahyeonee99@gmail.com"))
			.andExpect(jsonPath("$.writer.nickname").value("nahyeonee99"));
	}
}