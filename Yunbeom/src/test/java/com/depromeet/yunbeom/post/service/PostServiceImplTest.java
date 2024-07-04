package com.depromeet.yunbeom.post.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.domain.PostCreate;
import com.depromeet.yunbeom.post.domain.PostUpdate;

class PostServiceTest {

	private PostService postService;

	@Test
	void getById는_존재하는_게시물을_내려준다() {
		// given
		// when
		Post result = postService.getById(11);

		// then
		assertThat(result.getContent()).isEqualTo("helloworld");
		assertThat(result.getWriter().getEmail()).isEqualTo("uiurihappy@naver.com");
	}

	@Test
	void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1)
			.content("foobar")
			.build();

		// when
		Post result = postService.create(postCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getContent()).isEqualTo("foobar");
		assertThat(result.getCreatedAt()).isGreaterThan(0);
	}

	@Test
	void postUpdateDto_를_이용하여_게시물을_수정할_수_있다() {
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("hello world :)")
			.build();

		// when
		postService.update(11, postUpdate);

		// then
		Post postEntity= postService.getById(11);
		assertThat(postEntity.getContent()).isEqualTo("hello world :)");
		assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
	}

}