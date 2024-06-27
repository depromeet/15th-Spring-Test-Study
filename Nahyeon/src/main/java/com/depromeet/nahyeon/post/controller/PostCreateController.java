package com.depromeet.nahyeon.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.post.controller.response.PostResponse;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.post.service.PostService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostCreateController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<PostResponse> createPost(@RequestBody PostCreate postCreate) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(PostResponse.from(postService.create(postCreate)));
	}
}
