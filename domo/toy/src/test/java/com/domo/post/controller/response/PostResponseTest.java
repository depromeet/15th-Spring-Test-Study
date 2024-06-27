package com.domo.post.controller.response;

import com.domo.post.domain.Post;
import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {
    @Test
    void Post로_응답을_생성할_수_있다() {
        // post
        User writer = User.builder()
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        Post post = Post.builder()
                .content("게시글 내용")
                .writer(writer)
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("게시글 내용");
        assertThat(post.getWriter().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("domo");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}