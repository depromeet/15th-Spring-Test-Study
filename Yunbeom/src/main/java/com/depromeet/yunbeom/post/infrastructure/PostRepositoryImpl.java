package com.depromeet.yunbeom.post.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.depromeet.yunbeom.post.service.port.PostRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

	private final PostJpaRepository postJpaRepository;

	@Override
	public Optional<PostEntity> findById(long id) {
		return postJpaRepository.findById(id);
	}

	@Override
	public PostEntity save(PostEntity postEntity) {
		return postJpaRepository.save(postEntity);
	}
}
