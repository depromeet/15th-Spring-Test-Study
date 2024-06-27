package com.domo.post.domain;

import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void PostCreate객체로_게시물을_만들_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("게시글 내용")
                .build();
        User writer = User.builder()
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        Post post = Post.from(writer, postCreate);

        // then
        assertThat(post.getContent()).isEqualTo("게시글 내용");
        assertThat(post.getWriter()).isEqualTo(writer);
        assertThat(post.getWriter().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("domo");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
}