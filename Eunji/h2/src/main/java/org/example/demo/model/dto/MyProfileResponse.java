package org.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.demo.model.UserStatus;

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