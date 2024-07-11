package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    public void User로_응답을_생성할_수_있다() {

        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        UserResponse userResponse = UserResponse.from(user);

        // then
        Assertions.assertThat(userResponse.getId()).isEqualTo(1);
        Assertions.assertThat(userResponse.getEmail()).isEqualTo("kok202@naver.com");
        Assertions.assertThat(userResponse.getNickname()).isEqualTo("kok202");
        Assertions.assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        Assertions.assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
    }
}