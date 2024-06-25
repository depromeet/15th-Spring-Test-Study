package com.domo.post.controller;

import com.domo.post.domain.PostCreate;
import com.domo.post.controller.response.PostResponse;
import com.domo.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostCreateController {

    private final PostService postService;
    private final PostController postController;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreate postCreate) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postController.toResponse(postService.create(postCreate)));
    }
}