package com.depromeet.nahyeon.post.controller.response;

import static org.assertj.core.api.Assertions.*;

import java.time.Clock;

import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.post.domain.Post;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

public class PostResponseTest {

	@Test
	public void Post로_응답을_생성할_수_있다() {
		// given
		long now = Clock.systemUTC().millis();
		Post post = Post.builder()
			.content("helloworld")
			.createdAt(now)
			.modifiedAt(now)
			.writer(User.builder()
				.email("nahyeon@gmail.com")
				.nickname("nahyeon")
				.address("Seoul")
				.status(UserStatus.ACTIVE)
				.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
				.build())
			.build();

		// when
		PostResponse postResponse = PostResponse.from(post);

		// then
		assertThat(postResponse.getContent()).isEqualTo("helloworld");
		assertThat(postResponse.getWriter().getEmail()).isEqualTo("nahyeon@gmail.com");
		assertThat(postResponse.getWriter().getNickname()).isEqualTo("nahyeon");
		assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}
}