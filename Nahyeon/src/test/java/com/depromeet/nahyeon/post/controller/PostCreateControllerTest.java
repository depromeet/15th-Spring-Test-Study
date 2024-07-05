package com.depromeet.nahyeon.post.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.mock.TestContainer;
import com.depromeet.nahyeon.post.controller.response.PostResponse;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class PostCreateControllerTest {

	@Test
	void 사용자는_게시물을_작성할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(323424142L))
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(100L)
			.build());
		PostCreate postCreate = PostCreate.builder()
			.content("post content")
			.writerId(1)
			.build();

		// when
		ResponseEntity<PostResponse> result = testContainer.postCreateController.createPost(postCreate);

		// then
		assertThat(result.getStatusCode().value()).isEqualTo(201);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getId()).isEqualTo(1L);
		assertThat(result.getBody().getCreatedAt()).isEqualTo(323424142L);
		assertThat(result.getBody().getWriter().getId()).isEqualTo(1L);
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("nahyeonee99");
	}
}
