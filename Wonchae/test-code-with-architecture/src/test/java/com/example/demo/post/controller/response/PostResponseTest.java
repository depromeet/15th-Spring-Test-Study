package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {
    @Test
    public void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("kok202@naver.com")
                        .nickname("kok202")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        Assertions.assertThat(post.getContent()).isEqualTo("helloworld");
        Assertions.assertThat(post.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        Assertions.assertThat(post.getWriter().getNickname()).isEqualTo("kok202");
        Assertions.assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);

    }
}