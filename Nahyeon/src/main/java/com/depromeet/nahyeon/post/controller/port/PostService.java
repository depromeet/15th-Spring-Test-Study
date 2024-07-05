package com.depromeet.nahyeon.post.controller.port;

import com.depromeet.nahyeon.post.domain.Post;
import com.depromeet.nahyeon.post.domain.PostCreate;
import com.depromeet.nahyeon.post.domain.PostUpdate;

public interface PostService {

	Post getById(long id);

	Post create(PostCreate postCreate);

	Post update(long id, PostUpdate postUpdate);
}
