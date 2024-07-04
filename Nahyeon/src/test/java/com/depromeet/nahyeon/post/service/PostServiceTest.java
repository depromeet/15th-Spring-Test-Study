package com.depromeet.nahyeon.post.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.mock.FakeMailSender;
import com.depromeet.nahyeon.mock.FakePostRepository;
import com.depromeet.nahyeon.mock.FakeUserRepository;
import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.post.domain.Post;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;

class PostServiceTest {

	private PostService postService;

	@BeforeEach
	void init() {
		FakePostRepository fakePostRepository = new FakePostRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		this.postService = PostService.builder()
			.postRepository(fakePostRepository)
			.userRepository(fakeUserRepository)
			.clockHolder(new TestClockHolder(4000))
			.build();

		User userA = fakeUserRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaaa")
			.status(UserStatus.ACTIVE)
			.lastLoginAt(0L)
			.build());
		User userB = fakeUserRepository.save(User.builder()
			.id(2L)
			.email("nahyeonee100@gmail.com")
			.nickname("nahyeonee100")
			.address("Seoul")
			.certificationCode("aaaaaaaa-aaaaaaaa-aaaaaaab")
			.status(UserStatus.PENDING)
			.lastLoginAt(0L)
			.build());

		fakePostRepository.save(Post.builder()
			.id(1L)
			.content("test 1")
			.createdAt(0L)
			.modifiedAt(1L)
			.writer(userA)
			.build());
		fakePostRepository.save(Post.builder()
			.id(2L)
			.content("test 2")
			.createdAt(0L)
			.modifiedAt(1L)
			.writer(userB)
			.build());
	}

	@Test
	void getById_는_존재하는_게시물을_내려준다() {
		// given
		// when
		Post result = postService.getById(1);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).isEqualTo("test 1");
		assertThat(result.getWriter().getNickname()).isEqualTo("nahyeonee99");
	}

	@Test
	void postCreate_를_이용하여_게시물을_생성할_수_있다() {
		// given
		PostCreate postCreate = PostCreate.builder()
			.writerId(1L)
			.content("test 1")
			.build();

		// when
		Post result = postService.create(postCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getContent()).isEqualTo("test 1");
		assertThat(result.getCreatedAt()).isGreaterThan(0);
	}

	@Test
	void postUpdate_를_이용하여_게시물을_생성할_수_있다() {
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("updated content :)")
			.build();

		// when
		postService.update(1, postUpdate);

		// then
		Post postEntity = postService.getById(1);
		assertThat(postEntity.getContent()).isEqualTo("updated content :)");
		assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
	}
}