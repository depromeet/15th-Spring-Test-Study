package com.depromeet.yunbeom.post.controller.response;

import com.depromeet.yunbeom.post.domain.Post;
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

    public static PostResponse from(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setContent(post.getContent());
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setModifiedAt(post.getModifiedAt());
        postResponse.setWriter(UserResponse.from(post.getWriter()));
        return postResponse;
    }
}
