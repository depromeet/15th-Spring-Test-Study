package com.depromeet.nahyeon.post.service;

import java.time.Clock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.depromeet.nahyeon.post.infrastructure.PostEntity;
import com.depromeet.nahyeon.post.service.port.PostRepository;
import com.depromeet.nahyeon.user.infrastructure.UserEntity;
import com.depromeet.nahyeon.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public PostEntity getById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	@Transactional
	public PostEntity create(PostCreate postCreateDto) {
		UserEntity userEntity = userService.getById(postCreateDto.getWriterId());
		PostEntity postEntity = new PostEntity();
		postEntity.setWriter(userEntity);
		postEntity.setContent(postCreateDto.getContent());
		postEntity.setCreatedAt(Clock.systemUTC().millis());
		return postRepository.save(postEntity);
	}

	@Transactional
	public PostEntity update(long id, PostUpdate postUpdateDto) {
		PostEntity postEntity = getById(id);
		postEntity.setContent(postUpdateDto.getContent());
		postEntity.setModifiedAt(Clock.systemUTC().millis());
		return postRepository.save(postEntity);
	}
}