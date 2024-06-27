package com.domo.user.controller.response;

import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {
    @Test
    void User로_응답을_생성할_수_있다() {
// given
        User user = User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        UserResponse response = UserResponse.from(user);

        // then
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getNickname()).isEqualTo("domo");
        assertThat(response.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(response.getLastLoginAt()).isEqualTo(100);
    }
}