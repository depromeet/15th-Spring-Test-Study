package com.example.demo.post.service.port;

import java.util.Optional;

import com.example.demo.post.domain.Post;

public interface PostRepository {
	Optional<Post> findById(long id);

	Post save(Post post);
}
