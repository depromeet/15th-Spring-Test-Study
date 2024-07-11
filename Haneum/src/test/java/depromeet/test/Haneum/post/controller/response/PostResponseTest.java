package depromeet.test.Haneum.post.controller.response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import depromeet.test.Haneum.post.domain.Post;
import depromeet.test.Haneum.user.domain.User;
import depromeet.test.Haneum.user.domain.UserStatus;

public class PostResponseTest {

    @Test
    public void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
            .content("helloworld")
            .writer(User.builder()
                .email("ummdev03@gmail.com")
                .nickname("lee")
                .address("suwon")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build())
            .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("ummdev03@gmail.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("lee");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
