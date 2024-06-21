package com.depromeet.yunbeom.service;

import java.time.Clock;

import org.springframework.stereotype.Service;

import com.depromeet.yunbeom.exception.ResourceNotFoundException;
import com.depromeet.yunbeom.model.dto.PostCreateDto;
import com.depromeet.yunbeom.model.dto.PostUpdateDto;
import com.depromeet.yunbeom.repository.PostEntity;
import com.depromeet.yunbeom.repository.PostRepository;
import com.depromeet.yunbeom.repository.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreateDto postCreateDto) {
        UserEntity userEntity = userService.getById(postCreateDto.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreateDto.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdateDto postUpdateDto) {
        PostEntity postEntity = getById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}