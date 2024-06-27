package com.depromeet.nahyeon.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.post.domain.Post;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.post.domain.PostUpdate;
import com.depromeet.nahyeon.post.service.port.PostRepository;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;
	private final ClockHolder clockHolder;

	public Post getById(long id) {
		return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
	}

	@Transactional
	public Post create(PostCreate postCreate) {
		User user = userService.getById(postCreate.getWriterId());
		Post post = Post.of(user, postCreate, clockHolder);
		return postRepository.save(post);
	}

	@Transactional
	public Post update(long id, PostUpdate postUpdate) {
		Post post = getById(id);
		post = post.update(postUpdate, clockHolder);
		return postRepository.save(post);
	}
}