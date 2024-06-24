package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        User user = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(user)
                .createdAt(100L)
                .build());

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1L);

        // then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        Assertions.assertThat(result.getBody().getWriter()).isEqualTo(user);
        Assertions.assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        Assertions.assertThatThrownBy(() -> {
            testContainer.postController.getPostById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 200L)
                .build();
        User user = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(user)
                .createdAt(100L)
                .build());

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1L, PostUpdate.builder()
                .content("foobar")
                .build());

        // then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(result.getBody()).isNotNull();
        Assertions.assertThat(result.getBody().getContent()).isEqualTo("foobar");
        Assertions.assertThat(result.getBody().getWriter()).isEqualTo(user);
        Assertions.assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        Assertions.assertThat(result.getBody().getModifiedAt()).isEqualTo(200L);
    }
}
