package com.domo.post.infstructure;

import com.domo.post.domain.Post;
import com.domo.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<Post> findById(long id) {
        return postJpaRepository.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.fromDomain(post)).toDomain();
    }
}
