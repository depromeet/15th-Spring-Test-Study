package com.domo.mock;

import com.domo.common.service.port.ClockHolder;
import com.domo.common.service.port.UuidHolder;
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
    public final UserReadService userReadService;
    public final UserCreateService userCreateService;
    public final UserUpdateService userUpdateService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final AuthenticationService authenticationService;


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
        this.userReadService = userService;
        this.userCreateService = userService;
        this.userUpdateService = userService;
        this.authenticationService = userService;
        this.userController = UserController.builder()
                .userReadService(userReadService)
                .userCreateService(userCreateService)
                .userUpdateService(userUpdateService)
                .authenticationService(authenticationService)
                .build();
        this.userCreateController = UserCreateController.builder()
                .userCreateService(userCreateService)
                .build();
    }
}
