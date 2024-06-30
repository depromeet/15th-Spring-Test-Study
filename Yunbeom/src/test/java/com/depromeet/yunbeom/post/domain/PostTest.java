package com.depromeet.yunbeom.post.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;

class PostTest {

	@Test
	public void PostCreate으로_게시물을_만들_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1)
			.content("helloworld")
			.build();
		User writer = User.builder()
			.id(1L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.build();

		// when
		Post post = Post.from(postCreate, writer);

		// then
		assertThat(post.getContent()).isEqualTo("helloworld");
		assertThat(post.getWriter().getEmail()).isEqualTo("uiurihappy@naver.com");
		assertThat(post.getWriter().getNickname()).isEqualTo("uiurihappy");
		assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
		assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
	}
}