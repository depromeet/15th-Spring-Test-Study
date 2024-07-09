package com.domo.post.controller;

import com.domo.mock.TestContainer;
import com.domo.post.controller.response.PostResponse;
import com.domo.post.domain.PostCreate;
import com.domo.user.controller.response.UserResponse;
import com.domo.user.domain.User;
import com.domo.user.domain.UserCreate;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
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
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("foobar")
                .build();

        ResponseEntity<PostResponse> response = testContainer.postCreateController.createPost(postCreate);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getContent()).isEqualTo("foobar");
        assertThat(response.getBody().getWriter().getId()).isEqualTo(1);
        assertThat(response.getBody().getWriter().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getWriter().getNickname()).isEqualTo("domo");
    }
}