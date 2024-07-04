package com.depromeet.yunbeom.post.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.mock.FakePostRepository;
import com.depromeet.yunbeom.mock.FakeUserRepository;
import com.depromeet.yunbeom.mock.TestClockHolder;
import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.domain.PostCreate;
import com.depromeet.yunbeom.post.domain.PostUpdate;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.domain.UserStatus;

class PostServiceImplTest {

	private PostServiceImpl postService;

	@BeforeEach
	void init() {
		FakePostRepository fakePostRepository = new FakePostRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		this.postService = PostServiceImpl.builder()
			.postRepository(fakePostRepository)
			.userRepository(fakeUserRepository)
			.clockHolder(new TestClockHolder(1679530673958L))
			.build();
		User user1 = User.builder()
			.id(11L)
			.email("uiurihappy@naver.com")
			.nickname("uiurihappy")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build();
		User user2 = User.builder()
			.id(12L)
			.email("ybchar@naver.com")
			.nickname("ybchar")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build();
		fakeUserRepository.save(user1);
		fakeUserRepository.save(user2);
		fakePostRepository.save(Post.builder()
			.id(1L)
			.content("helloworld")
			.createdAt(1678530673958L)
			.modifiedAt(0L)
			.writer(user1)
			.build());
	}

	@Test
	void getById는_존재하는_게시물을_내려준다() {
		// given
		// when
		Post result = postService.getById(1);

		// then
		assertThat(result.getContent()).isEqualTo("helloworld");
		assertThat(result.getWriter().getEmail()).isEqualTo("uiurihappy@naver.com");
	}

	@Test
	void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(11)
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
		postService.update(1, postUpdate);

		// then
		Post postEntity= postService.getById(1);
		assertThat(postEntity.getContent()).isEqualTo("hello world :)");
		assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
	}

}