package com.depromeet.nahyeon.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.depromeet.nahyeon.model.dto.PostCreateDto;
import com.depromeet.nahyeon.model.dto.PostUpdateDto;
import com.depromeet.nahyeon.repository.PostEntity;

@SpringBootTest
@TestPropertySource("classpath:test-application.yml")
@SqlGroup({
	@Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

	@Autowired
	private PostService postService;

	@Test
	void getById_는_존재하는_게시물을_내려준다() {
		// given
		// when
		PostEntity result = postService.getById(1);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).isEqualTo("helloworld");
		assertThat(result.getWriter().getNickname()).isEqualTo("nahyeonee99");
	}

	@Test
	void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
		// given
		PostCreateDto postCreateDto = PostCreateDto.builder()
			.writerId(1L)
			.content("test content")
			.build();

		// when
		PostEntity result = postService.create(postCreateDto);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getContent()).isEqualTo("test content");
		assertThat(result.getCreatedAt()).isGreaterThan(0);
	}

	@Test
	void postUpdateDto_를_이용하여_게시물을_생성할_수_있다() {
		// given
		PostUpdateDto postUpdateDto = PostUpdateDto.builder()
			.content("updated content :)")
			.build();

		// when
		postService.update(1, postUpdateDto);

		// then
		PostEntity postEntity = postService.getById(1);
		assertThat(postEntity.getContent()).isEqualTo("updated content :)");
		assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
	}
}