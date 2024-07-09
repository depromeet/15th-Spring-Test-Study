package com.domo.post.controller.port;

import com.domo.post.domain.Post;
import com.domo.post.domain.PostCreate;
import com.domo.post.domain.PostUpdate;

public interface PostService {
    Post getPostById(long id);
    Post create(PostCreate postCreate);
    Post update(long id, PostUpdate postUpdate);
}
