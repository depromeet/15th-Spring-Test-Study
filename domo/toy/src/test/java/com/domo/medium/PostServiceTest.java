package com.domo.medium;

import com.domo.common.service.port.ClockHolder;
import com.domo.mock.*;
import com.domo.post.domain.Post;
import com.domo.post.domain.PostCreate;
import com.domo.post.domain.PostUpdate;
import com.domo.post.service.PostService;
import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import com.domo.user.service.CertificationService;
import com.domo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class PostServiceTest {
    private PostService postService;

    @BeforeEach
    void setUp() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        ClockHolder clockHolder = new TestClockHolder(1678530673958L);
        this.postService = PostService.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(clockHolder)
                .build();
        User user1 = User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(1678530673958L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("me@dev-domo1.com")
                .nickname("domo1")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(1678530673958L)
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);

        fakePostRepository.save(Post.builder()
                .id(16L)
                .content("Hello, world!")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(user1)
                .build());
    }
    @Test
    void getById는_존재하는_게시물을_조회한다() {
        // given
        // when
        Post result = postService.getPostById(16);

        // then
        assertThat(result.getContent()).isEqualTo("Hello, world!");
        assertThat(result.getWriter().getEmail()).isEqualTo("me@dev-domo.com");
    }

    @Test
    void postCreateDto를_이용하여_게시물을_생성할_수_있다() {
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
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("Hello, Domo!")
                .build();

        // when
        postService.update(16, postUpdate);

        // then
        Post result = postService.getPostById(16);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("Hello, Domo!");
        assertThat(result.getModifiedAt()).isEqualTo(1678530673958L);
    }
}