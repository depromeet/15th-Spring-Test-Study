package com.example.demo.post.service;

import com.example.demo.mock.*;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostServiceTest {

	private PostService postService;

	@BeforeEach
	void init(){
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		FakePostRepository fakePostRepository = new FakePostRepository();
		this.postService = PostService.builder()
				.postRepository(fakePostRepository)
				.userRepository(fakeUserRepository)
				.clockHolder(new TestClockHolder(1678530673958L))
				.build();
//		데이터 추가
		User user1 = User.builder()
				.id(1L)
				.email("kok202@naver.com")
				.nickname("kok202")
				.address("Seoul")
				.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
				.status(UserStatus.ACTIVE)
				.lastLoginAt(0L)
				.build();
		User user2 = User.builder()
				.id(2L)
				.email("kok303@naver.com")
				.nickname("kok303")
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
	void getById_는_게시물을_가져온다(){
		// given
		// when
		Post result = postService.getById(1);

		// then
		assertThat(result.getContent()).isEqualTo("helloworld");
		assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
	}

	@Test
	void postCreateDto_를_이용하여_게시물을_생성할_수_있다(){
		// given
		PostCreate postCreate = PostCreate.builder()
				.writerId(1)
				.content("foobar")
				.build();

		// when
		Post result = postService.create(postCreate);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getContent()).isEqualTo("foobar");
		assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
	}

	@Test
	void postUpdateDto_를_이용하여_게시물을_수정할_수_있다(){
		// given
		PostUpdate postUpdate = PostUpdate.builder()
			.content("hello world :)")
			.build();

		// when
		postService.update(1, postUpdate);

		// then
		Post post = postService.getById(1);
		assertThat(post.getContent()).isEqualTo("hello world :)");
		assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
	}

}
