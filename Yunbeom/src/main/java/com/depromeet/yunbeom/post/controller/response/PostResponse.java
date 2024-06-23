package com.depromeet.yunbeom.post.controller.response;

import com.depromeet.yunbeom.user.controller.response.UserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private UserResponse writer;
}
