package com.depromeet.nahyeon.mock;

import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.common.service.port.UuidHolder;
import com.depromeet.nahyeon.post.controller.PostController;
import com.depromeet.nahyeon.post.controller.PostCreateController;
import com.depromeet.nahyeon.post.controller.port.PostService;
import com.depromeet.nahyeon.post.service.PostServiceImpl;
import com.depromeet.nahyeon.post.service.port.PostRepository;
import com.depromeet.nahyeon.user.controller.UserController;
import com.depromeet.nahyeon.user.controller.UserCreateController;
import com.depromeet.nahyeon.user.controller.port.AuthenticationService;
import com.depromeet.nahyeon.user.controller.port.UserCreateService;
import com.depromeet.nahyeon.user.controller.port.UserReadService;
import com.depromeet.nahyeon.user.controller.port.UserUpdateService;
import com.depromeet.nahyeon.user.service.CertificationService;
import com.depromeet.nahyeon.user.service.UserServiceImpl;
import com.depromeet.nahyeon.user.service.port.MailSender;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.Builder;

public class TestContainer {

	public final MailSender mailSender;
	public final UserRepository userRepository;
	public final PostRepository postRepository;
	public final UserReadService userReadService;
	public final UserCreateService userCreateService;
	public final UserUpdateService userUpdateService;
	public final AuthenticationService authenticationService;
	public final PostService postService;
	public final UserController userController;
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

		UserServiceImpl userService = UserServiceImpl.builder()
			.userRepository(this.userRepository)
			.certificationService(new CertificationService(this.mailSender))
			.uuidHolder(uuidHolder)
			.clockHolder(clockHolder)
			.build();
		this.userReadService = userService;
		this.userCreateService = userService;
		this.userUpdateService = userService;
		this.authenticationService = userService;
		this.userController = UserController.builder()
			.userReadService(this.userReadService)
			.userCreateService(this.userCreateService)
			.userUpdateService(this.userUpdateService)
			.authenticationService(this.authenticationService)
			.build();
		this.userCreateController = UserCreateController.builder()
			.userCreateService(this.userCreateService)
			.build();
		this.postController = PostController.builder()
			.postService(this.postService)
			.build();
		this.postCreateController = PostCreateController.builder()
			.postService(this.postService)
			.build();
	}
}
