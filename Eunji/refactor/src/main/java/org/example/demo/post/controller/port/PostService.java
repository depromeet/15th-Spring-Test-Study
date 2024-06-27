package org.example.demo.post.controller.port;

import org.example.demo.post.domain.Post;
import org.example.demo.post.domain.PostCreate;
import org.example.demo.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);

    Post create(PostCreate postCreate);

    Post update(long id, PostUpdate postUpdate);
}