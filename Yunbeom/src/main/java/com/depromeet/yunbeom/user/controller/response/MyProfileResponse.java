package com.depromeet.yunbeom.user.controller.response;

import com.depromeet.yunbeom.user.domain.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProfileResponse {

    private Long id;
    private String email;
    private String nickname;
    private String address;
    private UserStatus status;
    private Long lastLoginAt;
}
