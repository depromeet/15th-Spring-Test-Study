package depromeet.test.Haneum.post.service;

import depromeet.test.Haneum.common.domain.exception.ResourceNotFoundException;
import depromeet.test.Haneum.common.service.port.ClockHolder;
import depromeet.test.Haneum.post.controller.port.PostService;
import depromeet.test.Haneum.post.domain.Post;
import depromeet.test.Haneum.post.domain.PostCreate;
import depromeet.test.Haneum.post.domain.PostUpdate;
import depromeet.test.Haneum.post.service.port.PostRepository;
import depromeet.test.Haneum.user.domain.User;
import depromeet.test.Haneum.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User writer = userRepository.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate, clockHolder);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }
}