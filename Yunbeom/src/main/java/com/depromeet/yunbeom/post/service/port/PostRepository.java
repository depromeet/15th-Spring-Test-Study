package com.depromeet.yunbeom.post.service.port;

import java.util.Optional;

import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.infrastructure.PostEntity;

public interface PostRepository {
	Optional<Post> findById(long id);

	Post save(Post postEntity);
}
