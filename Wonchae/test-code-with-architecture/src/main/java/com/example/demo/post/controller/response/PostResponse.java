package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private Long createdAt;
    private Long modifiedAt;
    private User writer;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .writer(post.getWriter())
                .build();
    }
}
