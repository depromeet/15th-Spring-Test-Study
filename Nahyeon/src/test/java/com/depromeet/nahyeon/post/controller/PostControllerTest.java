package com.depromeet.nahyeon.post.controller;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.mock.TestContainer;
import com.depromeet.nahyeon.post.controller.response.PostResponse;
import com.depromeet.nahyeon.post.domain.Post;
import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class PostControllerTest {

	@Test
	void 사용자는_게시물을_단건_조회_할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(323424142L))
			.build();
		User user = testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(100L)
			.build());
		testContainer.postRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.createdAt(323424142L)
			.modifiedAt(323424142L)
			.writer(user)
			.build());

		// when
		ResponseEntity<PostResponse> result = testContainer.postController.getById(1L);

		// then
		assertThat(result.getStatusCode().value()).isEqualTo(200);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getId()).isEqualTo(1);
		assertThat(result.getBody().getContent()).isEqualTo("helloworld");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("nahyeonee99");
	}

	@Test
	void 사용자는_존재하지_않는_게시물을_조회할_경우_에러가_난다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(323424142L))
			.build();

		// when
		// then
		Assertions.assertThatThrownBy(() -> {
			testContainer.postController.getById(100L);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void 사용자는_게시물을_수정할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(323424142L))
			.build();
		User user = testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(100L)
			.build());
		testContainer.postRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.createdAt(100L)
			.writer(user)
			.build());
		PostUpdate postUpdate = PostUpdate.builder()
			.content("update post! :)")
			.build();

		// when
		ResponseEntity<PostResponse> result = testContainer.postController.update(1L,
			postUpdate);

		// then
		assertThat(result.getStatusCode().value()).isEqualTo(200);
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getId()).isEqualTo(1);
		assertThat(result.getBody().getContent()).isEqualTo("update post! :)");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("nahyeonee99");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
		assertThat(result.getBody().getModifiedAt()).isEqualTo(323424142L);
	}
}
