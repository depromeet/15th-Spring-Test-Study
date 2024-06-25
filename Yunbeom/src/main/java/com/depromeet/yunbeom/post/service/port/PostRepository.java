package com.depromeet.yunbeom.post.service.port;

import java.util.Optional;

import com.depromeet.yunbeom.post.infrastructure.PostEntity;

public interface PostRepository {
	Optional<PostEntity> findById(long id);

	PostEntity save(PostEntity postEntity);
}
