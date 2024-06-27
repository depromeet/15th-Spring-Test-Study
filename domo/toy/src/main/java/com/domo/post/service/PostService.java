package com.domo.post.service;

import java.time.Clock;

import com.domo.common.domain.exception.ResourceNotFoundException;
import com.domo.post.domain.Post;
import com.domo.post.domain.PostCreate;
import com.domo.post.domain.PostUpdate;
import com.domo.post.infstructure.PostEntity;
import com.domo.post.service.port.PostRepository;
import com.domo.user.domain.User;
import com.domo.user.infstructure.UserEntity;
import com.domo.user.service.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User writer = userService.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getPostById(id);
        post = post.update(id, postUpdate);
        return postRepository.save(post);
    }
}