package depromeet.test.Haneum.post.service.port;

import java.util.Optional;

import depromeet.test.Haneum.post.domain.Post;

public interface PostRepository {

    Optional<Post> findById(long id);

    Post save(Post post);
}
