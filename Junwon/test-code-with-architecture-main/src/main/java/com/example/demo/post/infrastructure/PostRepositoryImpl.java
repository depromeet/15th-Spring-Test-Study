package com.example.demo.post.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

	private final PostJpaRepository postJpaRepository;

	@Override
	public Optional<Post> findById(long id) {
		return postJpaRepository.findById(id).map(PostEntity::toModel);
	}

	@Override
	public Post save(Post post) {
		return postJpaRepository.save(PostEntity.fromModel(post)).toModel();
	}
}
