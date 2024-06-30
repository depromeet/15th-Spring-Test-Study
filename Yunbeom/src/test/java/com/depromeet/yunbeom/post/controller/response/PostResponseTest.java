package com.depromeet.yunbeom.post.controller.response;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;

class PostResponseTest {

	@Test
	public void Post로_응답을_생성할_수_있다() {
		// given
		Post post = Post.builder()
			.content("helloworld")
			.writer(User.builder()
				.email("uiurihappy@naver.com")
				.nickname("uiurihappy")
				.address("Seoul")
				.status(UserStatus.ACTIVE)
				.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
				.build())
			.build();

		// when
		PostResponse postResponse = PostResponse.from(post);

		// then
		assertThat(postResponse.getContent()).isEqualTo("helloworld");
		assertThat(postResponse.getWriter().getEmail()).isEqualTo("uiurihappy@naver.com");
		assertThat(postResponse.getWriter().getNickname()).isEqualTo("uiurihappy");
		assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

}