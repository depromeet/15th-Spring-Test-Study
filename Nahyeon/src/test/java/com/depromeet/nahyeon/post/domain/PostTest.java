package com.depromeet.nahyeon.post.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class PostTest {

	@Test
	public void PostCreate으로_게시물을_만들_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1)
			.content("helloworld")
			.build();
		User writer = User.builder()
			.email("nahyeon@gmail.com")
			.nickname("nahyeon")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
			.build();

		// when
		Post post = Post.of(writer, postCreate, new TestClockHolder(0));

		// then
		assertThat(post.getContent()).isEqualTo("helloworld");
		assertThat(post.getWriter().getEmail()).isEqualTo("nahyeon@gmail.com");
		assertThat(post.getWriter().getNickname()).isEqualTo("nahyeon");
		assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
		assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaaaaaa-aaaaaaaa");
	}

	@Test
	public void Post는_PostUpdate_객체로_데이터를_업데이트_할_수_있다() {
		// given
		// when
		// then
	}
}