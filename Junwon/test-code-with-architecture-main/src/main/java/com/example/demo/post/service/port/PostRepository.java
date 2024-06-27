package com.example.demo.post.service.port;

import java.util.Optional;

import com.example.demo.post.infrastructure.PostEntity;

public interface PostRepository {
	Optional<PostEntity> findById(long id);

	PostEntity save(PostEntity postEntity);
}
