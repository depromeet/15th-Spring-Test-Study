package com.domo.user.controller.response;

import com.domo.user.domain.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;
}
