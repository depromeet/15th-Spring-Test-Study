package com.domo.medium;

import com.domo.post.domain.Post;
import com.domo.post.domain.PostCreate;
import com.domo.post.domain.PostUpdate;
import com.domo.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("Hello, Domo!")
                .build();

        // when
        postService.update(11, postUpdate);

        // then
        Post result = postService.getPostById(11);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("Hello, Domo!");
        assertThat(result.getModifiedAt()).isGreaterThan(0);

    }
}