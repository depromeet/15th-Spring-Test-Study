package com.depromeet.nahyeon.post.service.port;

import java.util.Optional;

import com.depromeet.nahyeon.post.domain.Post;

public interface PostRepository {

	Optional<Post> findById(long id);

	Post save(Post post);
}
