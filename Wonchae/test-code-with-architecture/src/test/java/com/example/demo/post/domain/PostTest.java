package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    @Test
    public void PostCreate로_게시물을_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();

        User writer = User.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1679530673958L));

        // then
        Assertions.assertThat(post.getContent()).isEqualTo("helloworld");
        Assertions.assertThat(post.getCreatedAt()).isEqualTo(1679530673958L);
        Assertions.assertThat(post.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        Assertions.assertThat(post.getWriter().getNickname()).isEqualTo("kok202");
        Assertions.assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        Assertions.assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        Assertions.assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    public void PostUpdate로_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        User writer = User.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
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
        Assertions.assertThat(post.getContent()).isEqualTo("foobar");
        Assertions.assertThat(post.getModifiedAt()).isEqualTo(1679530673958L);
    }
}