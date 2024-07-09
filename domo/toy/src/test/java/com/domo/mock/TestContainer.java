package com.domo.mock;

import com.domo.common.service.port.ClockHolder;
import com.domo.common.service.port.UuidHolder;
import com.domo.post.controller.PostController;
import com.domo.post.controller.PostCreateController;
import com.domo.post.controller.port.PostService;
import com.domo.post.service.PostServiceImpl;
import com.domo.post.service.port.PostRepository;
import com.domo.user.controller.UserController;
import com.domo.user.controller.UserCreateController;
import com.domo.user.controller.port.*;
import com.domo.user.service.CertificationService;
import com.domo.user.service.UserServiceImpl;
import com.domo.user.service.port.MailSender;
import com.domo.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {
    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    private final UserService userService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;
    private final PostService postService;
    private final CertificationService certificationService;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(postRepository)
                .userRepository(userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .certificationService(certificationService)
                .userRepository(userRepository)
                .build();
        this.userService = userService;
        this.userController = UserController.builder()
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
