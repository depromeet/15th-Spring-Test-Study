package org.example.demo.user.controller.response;


import lombok.Builder;
import lombok.Getter;
import org.example.demo.user.domain.User;
import org.example.demo.user.domain.UserStatus;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .status(user.getStatus())
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }
}