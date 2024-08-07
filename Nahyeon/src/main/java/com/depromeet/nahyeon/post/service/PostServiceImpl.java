package com.depromeet.nahyeon.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.post.controller.port.PostService;
import com.depromeet.nahyeon.post.domain.Post;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.depromeet.nahyeon.post.service.port.PostRepository;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ClockHolder clockHolder;

	@Override
	public Post getById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	@Override
	@Transactional
	public Post create(PostCreate postCreate) {
		User user = userRepository.getById(postCreate.getWriterId());
		Post post = Post.of(user, postCreate, clockHolder);
		return postRepository.save(post);
	}

	@Override
	@Transactional
	public Post update(long id, PostUpdate postUpdate) {
		Post post = getById(id);
		post = post.update(postUpdate, clockHolder);
		return postRepository.save(post);
	}
}