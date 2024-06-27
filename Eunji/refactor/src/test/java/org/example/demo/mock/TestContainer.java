package org.example.demo.mock;

import lombok.Builder;
import org.example.demo.common.service.port.ClockHolder;
import org.example.demo.common.service.port.UuidHolder;
import org.example.demo.post.controller.PostController;
import org.example.demo.post.controller.PostCreateController;
import org.example.demo.post.controller.port.PostService;
import org.example.demo.post.service.PostServiceImpl;
import org.example.demo.post.service.port.PostRepository;
import org.example.demo.user.controller.MyInfoController;
import org.example.demo.user.controller.UserController;
import org.example.demo.user.controller.UserCreateController;
import org.example.demo.user.service.CertificationService;
import org.example.demo.user.service.UserServiceImpl;
import org.example.demo.user.service.port.MailSender;
import org.example.demo.user.service.port.UserRepository;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostService postService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final MyInfoController myInfoController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
            .postRepository(this.postRepository)
            .userRepository(this.userRepository)
            .clockHolder(clockHolder)
            .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
            .uuidHolder(uuidHolder)
            .clockHolder(clockHolder)
            .userRepository(this.userRepository)
            .certificationService(this.certificationService)
            .build();
        this.userController = UserController.builder()
            .userService(userService)
            .build();
        this.myInfoController = MyInfoController.builder()
            .userService(userService)
            .build();
        this.userCreateController = UserCreateController.builder()
            .userService(userService)
            .build();
        this.postController = PostController.builder()
            .postService(postService)
            .build();
        this.postCreateController = PostCreateController.builder()
            .postService(postService)
            .build();
    }
}