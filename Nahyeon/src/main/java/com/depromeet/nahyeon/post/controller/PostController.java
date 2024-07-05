package com.depromeet.nahyeon.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.post.controller.port.PostService;
import com.depromeet.nahyeon.post.controller.response.PostResponse;
import com.depromeet.nahyeon.post.domain.PostUpdate;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Builder
public class PostController {

	private final PostService postService;

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
		return ResponseEntity.ok()
			.body(PostResponse.from(postService.getById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @RequestBody PostUpdate postUpdate) {
		return ResponseEntity.ok()
			.body(PostResponse.from(postService.update(id, postUpdate)));
	}
}
