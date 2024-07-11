package com.domo.service;

import com.domo.model.UserStatus;
import com.domo.model.dto.PostCreateDto;
import com.domo.model.dto.PostUpdateDto;
import com.domo.model.dto.UserCreateDto;
import com.domo.model.dto.UserUpdateDto;
import com.domo.repository.PostEntity;
import com.domo.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.yml")
@SqlGroup({
        @Sql("/sql/post-service-test-data.sql"),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시물을_조회한다() {
        // given
        // when
        PostEntity result = postService.getPostById(16);

        // then
        assertThat(result.getContent()).isEqualTo("Hello, world!");
        assertThat(result.getWriter().getEmail()).isEqualTo("me@dev-domo.com");
    }

    @Test
    void postCreateDto를_이용하여_게시물을_생성할_수_있다() {
        // given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("foobar")
                .build();

        // when
        PostEntity result = postService.create(postCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("foobar");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("Hello, Domo!")
                .build();

        // when
        postService.update(11, postUpdateDto);

        // then
        PostEntity result = postService.getPostById(11);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("Hello, Domo!");
        assertThat(result.getModifiedAt()).isGreaterThan(0);

    }
}