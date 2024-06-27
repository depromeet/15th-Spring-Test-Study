package com.depromeet.nahyeon.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.nahyeon.user.controller.UserController;
import com.depromeet.nahyeon.post.controller.response.PostResponse;
import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.depromeet.nahyeon.post.infrastructure.PostEntity;
import com.depromeet.nahyeon.post.service.PostService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final UserController userController;

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
		return ResponseEntity.ok()
			.body(toResponse(postService.getById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @RequestBody PostUpdate postUpdateDto) {
		return ResponseEntity.ok()
			.body(toResponse(postService.update(id, postUpdateDto)));
	}

	public PostResponse toResponse(PostEntity postEntity) {
		PostResponse postResponse = new PostResponse();
		postResponse.setId(postEntity.getId());
		postResponse.setContent(postEntity.getContent());
		postResponse.setCreatedAt(postEntity.getCreatedAt());
		postResponse.setModifiedAt(postEntity.getModifiedAt());
		postResponse.setWriter(userController.toResponse(postEntity.getWriter()));
		return postResponse;
	}
}
