package com.depromeet.nahyeon.service;

import java.time.Clock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.nahyeon.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.model.dto.PostCreateDto;
import com.depromeet.nahyeon.model.dto.PostUpdateDto;
import com.depromeet.nahyeon.repository.PostEntity;
import com.depromeet.nahyeon.repository.PostRepository;
import com.depromeet.nahyeon.repository.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public PostEntity getPostById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	public PostEntity createPost(PostCreateDto postCreateDto) {
		UserEntity userEntity = userService.getByIdOrElseThrow(postCreateDto.getWriterId());
		PostEntity postEntity = new PostEntity();
		postEntity.setWriter(userEntity);
		postEntity.setContent(postCreateDto.getContent());
		postEntity.setCreatedAt(Clock.systemUTC().millis());
		return postRepository.save(postEntity);
	}

	public PostEntity updatePost(long id, PostUpdateDto postUpdateDto) {
		PostEntity postEntity = getPostById(id);
		postEntity.setContent(postUpdateDto.getContent());
		postEntity.setModifiedAt(Clock.systemUTC().millis());
		return postRepository.save(postEntity);
	}
}