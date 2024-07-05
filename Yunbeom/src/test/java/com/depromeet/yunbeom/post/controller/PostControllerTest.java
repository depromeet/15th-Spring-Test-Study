package com.depromeet.yunbeom.post.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.depromeet.yunbeom.mock.TestContainer;
import com.depromeet.yunbeom.post.controller.response.PostResponse;
import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.domain.PostUpdate;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;

class PostControllerTest {

	@Test
	void 사용자는_게시물을_단건_조회_할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		User user = User.builder()
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build();
		testContainer.userRepository.save(user);
		testContainer.postRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.writer(user)
			.createdAt(100L)
			.build());

		// when
		ResponseEntity<PostResponse> result = testContainer.postController.getById(1L);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).isEqualTo("helloworld");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("uiurihappy");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
	}

	@Test
	void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.postController.getById(1L);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void 사용자는_게시물을_수정할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(() -> 200L)
			.build();
		User user = User.builder()
			.id(1L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.lastLoginAt(100L)
			.build();
		testContainer.userRepository.save(user);
		testContainer.postRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.writer(user)
			.createdAt(100L)
			.build());

		// when
		ResponseEntity<PostResponse> result = testContainer.postController.update(1L, PostUpdate.builder()
			.content("foobar")
			.build());

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).isEqualTo("foobar");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("uiurihappy");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
		assertThat(result.getBody().getModifiedAt()).isEqualTo(200L);
	}
}