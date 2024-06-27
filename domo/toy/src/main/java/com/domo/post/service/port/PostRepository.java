package com.domo.post.service.port;

import com.domo.post.domain.Post;
import com.domo.post.infstructure.PostEntity;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(long id);

    Post save(Post postEntity);
}
