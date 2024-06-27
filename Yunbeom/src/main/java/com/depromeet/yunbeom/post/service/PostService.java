package com.depromeet.yunbeom.post.service;

import java.time.Clock;

import org.springframework.stereotype.Service;

import com.depromeet.yunbeom.post.service.port.PostRepository;
import com.depromeet.yunbeom.user.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.post.domain.PostCreate;
import com.depromeet.yunbeom.post.domain.PostUpdate;
import com.depromeet.yunbeom.post.infrastructure.PostEntity;
import com.depromeet.yunbeom.post.infrastructure.PostJpaRepository;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;
import com.depromeet.yunbeom.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreate postCreate) {
        UserEntity userEntity = userService.getById(postCreate.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreate.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdate postUpdate) {
        PostEntity postEntity = getById(id);
        postEntity.setContent(postUpdate.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}