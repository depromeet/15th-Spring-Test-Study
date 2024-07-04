package com.depromeet.yunbeom.post.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.mock.TestClockHolder;
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
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.build();

		// when
		Post post = Post.from(writer, postCreate, new TestClockHolder(1679530673958L));

		// then
		assertThat(post.getContent()).isEqualTo("helloworld");
		assertThat(post.getCreatedAt()).isEqualTo(1679530673958L);
		assertThat(post.getWriter().getEmail()).isEqualTo("uiurihappy@naver.com");
		assertThat(post.getWriter().getNickname()).isEqualTo("uiurihappy");
		assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
		assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
	}
	@Test
	public void PostUpdate로_게시물을_수정할_수_있다() {
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("foobar")
			.build();
		User writer = User.builder()
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.build();
		Post post = Post.builder()
			.id(1L)
			.content("helloworld")
			.createdAt(1678530673958L)
			.modifiedAt(0L)
			.writer(writer)
			.build();

		// when
		post = post.update(postUpdate, new TestClockHolder(1679530673958L));

		// then
		assertThat(post.getContent()).isEqualTo("foobar");
		assertThat(post.getModifiedAt()).isEqualTo(1679530673958L);
	}
}