package com.depromeet.yunbeom.post.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.service.port.PostRepository;

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
		return postJpaRepository.save(PostEntity.from(post)).toModel();
	}
}
