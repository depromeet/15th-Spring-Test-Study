package depromeet.test.Haneum.post.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import depromeet.test.Haneum.mock.TestContainer;
import depromeet.test.Haneum.post.controller.response.PostResponse;
import depromeet.test.Haneum.post.domain.PostCreate;
import depromeet.test.Haneum.user.domain.User;
import depromeet.test.Haneum.user.domain.UserStatus;

public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
            .clockHolder(() -> 1679530673958L)
            .build();
        testContainer.userRepository.save(User.builder()
            .id(1L)
            .email("ummdev03@gmail.com")
            .nickname("lee")
            .address("suwon")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build());
        PostCreate postCreate = PostCreate.builder()
            .writerId(1)
            .content("helloworld")
            .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.create(postCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("lee");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1679530673958L);
    }
}
