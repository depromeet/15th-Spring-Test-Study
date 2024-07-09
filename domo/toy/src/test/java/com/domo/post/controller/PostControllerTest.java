package com.domo.post.controller;

import com.domo.common.domain.exception.ResourceNotFoundException;
import com.domo.mock.TestContainer;
import com.domo.post.controller.response.PostResponse;
import com.domo.post.domain.Post;
import com.domo.post.domain.PostUpdate;
import com.domo.user.controller.response.UserResponse;
import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1678530673958L)
                .build());
        testContainer.postRepository.save(testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("Hello, world!")
                .writer(testContainer.userRepository.findById(1L).get())
                .build()));


        ResponseEntity<PostResponse> response = testContainer.postController.getPostById(1);


        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getContent()).isEqualTo("Hello, world!");
        assertThat(response.getBody().getWriter().getId()).isEqualTo(1);
        assertThat(response.getBody().getWriter().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getWriter().getNickname()).isEqualTo("domo");
    }

    @Test
    void 사용자는_존재하지_않는_게시물을_조회할_경우_에러가_발생한다() {
        TestContainer testContainer = TestContainer.builder()
                .build();

        assertThatThrownBy(() -> testContainer.postController.getPostById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Posts에서 ID 1를 찾을 수 없습니다.");
    }


    @Test
    void 사용자는_게시물을_수정_할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1678530673958L)
                .build());
        testContainer.postRepository.save(testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("Hello, world!")
                .writer(testContainer.userRepository.findById(1L).get())
                .build()));

        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        // when
        ResponseEntity<PostResponse> response = testContainer.postController.updatePost(1, postUpdate);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getContent()).isEqualTo("foobar");
        assertThat(response.getBody().getWriter().getId()).isEqualTo(1);
        assertThat(response.getBody().getWriter().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getWriter().getNickname()).isEqualTo("domo");
    }
}