package depromeet.test.Haneum.user.controller.response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import depromeet.test.Haneum.user.domain.User;
import depromeet.test.Haneum.user.domain.UserStatus;

public class UserResponseTest {

    @Test
    public void User으로_응답을_생성할_수_있다() {
        // given
        User user = User.builder()
            .id(1L)
            .email("ummdev03@gmail.com")
            .nickname("lee")
            .address("suwon")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(100L)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        // when
        UserResponse userResponse = UserResponse.from(user);

        // then
        assertThat(userResponse.getId()).isEqualTo(1);
        assertThat(userResponse.getEmail()).isEqualTo("ummdev03@gmail.com");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
    }
}
