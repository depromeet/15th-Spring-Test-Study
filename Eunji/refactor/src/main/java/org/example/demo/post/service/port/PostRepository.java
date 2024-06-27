package org.example.demo.post.service.port;

import java.util.Optional;
import org.example.demo.post.domain.Post;

public interface PostRepository {
    Optional<Post> findById(long id);

    Post save(Post post);
}