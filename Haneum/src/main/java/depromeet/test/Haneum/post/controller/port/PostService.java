package depromeet.test.Haneum.post.controller.port;

import depromeet.test.Haneum.post.domain.Post;
import depromeet.test.Haneum.post.domain.PostCreate;
import depromeet.test.Haneum.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);

    Post create(PostCreate postCreate);

    Post update(long id, PostUpdate postUpdate);
}
