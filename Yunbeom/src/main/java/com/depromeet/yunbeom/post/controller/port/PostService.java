package com.depromeet.yunbeom.post.controller.port;

import com.depromeet.yunbeom.post.domain.Post;
import com.depromeet.yunbeom.post.domain.PostCreate;
import com.depromeet.yunbeom.post.domain.PostUpdate;

public interface PostService {

	Post getById(long id);

	Post create(PostCreate postCreate);

	Post update(long id, PostUpdate postUpdate);
}
