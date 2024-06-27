package com.depromeet.nahyeon.post.service.port;

import java.util.Optional;

import com.depromeet.nahyeon.post.infrastructure.PostEntity;

public interface PostRepository {

	Optional<PostEntity> findById(long id);

	PostEntity save(PostEntity postEntity);
}
