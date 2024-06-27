package depromeet.test.Haneum.mock;


import depromeet.test.Haneum.common.service.port.ClockHolder;
import depromeet.test.Haneum.common.service.port.UuidHolder;
import depromeet.test.Haneum.post.controller.PostController;
import depromeet.test.Haneum.post.controller.PostCreateController;
import depromeet.test.Haneum.post.controller.port.PostService;
import depromeet.test.Haneum.post.service.PostServiceImpl;
import depromeet.test.Haneum.post.service.port.PostRepository;
import depromeet.test.Haneum.user.controller.MyInfoController;
import depromeet.test.Haneum.user.controller.UserController;
import depromeet.test.Haneum.user.controller.UserCreateController;
import depromeet.test.Haneum.user.service.CertificationService;
import depromeet.test.Haneum.user.service.UserServiceImpl;
import depromeet.test.Haneum.user.service.port.MailSender;
import depromeet.test.Haneum.user.service.port.UserRepository;
import lombok.Builder;

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
