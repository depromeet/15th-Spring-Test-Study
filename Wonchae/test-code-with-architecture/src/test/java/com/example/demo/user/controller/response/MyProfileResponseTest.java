package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyProfileResponseTest {

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
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then
        Assertions.assertThat(myProfileResponse.getId()).isEqualTo(1);
        Assertions.assertThat(myProfileResponse.getEmail()).isEqualTo("kok202@naver.com");
        Assertions.assertThat(myProfileResponse.getNickname()).isEqualTo("kok202");
        Assertions.assertThat(myProfileResponse.getAddress()).isEqualTo("Seoul");
        Assertions.assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        Assertions.assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }
}