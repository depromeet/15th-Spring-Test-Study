package com.depromeet.yunbeom.post.service;

import org.springframework.stereotype.Service;

import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.domain.PostCreate;
import com.depromeet.yunbeom.post.domain.PostUpdate;
import com.depromeet.yunbeom.post.service.port.PostRepository;
import com.depromeet.yunbeom.user.domain.User;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserServiceImpl userServiceImpl;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User writer = userServiceImpl.getById(postCreate.getWriterId());
        Post post = Post.from(postCreate, writer);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate);
        return postRepository.save(post);
    }
}