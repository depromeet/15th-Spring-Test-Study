package com.depromeet.yunbeom.post.service;

import org.springframework.stereotype.Service;

import com.depromeet.yunbeom.common.service.port.ClockHolder;
import com.depromeet.yunbeom.post.controller.port.PostService;
import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.domain.PostCreate;
import com.depromeet.yunbeom.post.domain.PostUpdate;
import com.depromeet.yunbeom.post.service.port.PostRepository;
import com.depromeet.yunbeom.user.controller.port.UserService;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.user.service.UserServiceImpl;
import com.depromeet.yunbeom.user.service.port.UserRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User writer = userRepository.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate, clockHolder);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }
}