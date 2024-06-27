package org.example.demo.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.demo.common.domain.exception.ResourceNotFoundException;
import org.example.demo.common.service.port.ClockHolder;
import org.example.demo.post.controller.port.PostService;
import org.example.demo.post.domain.Post;
import org.example.demo.post.domain.PostCreate;
import org.example.demo.post.domain.PostUpdate;
import org.example.demo.post.service.port.PostRepository;
import org.example.demo.user.domain.User;
import org.example.demo.user.service.port.UserRepository;
import org.springframework.stereotype.Service;

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