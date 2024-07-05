package com.depromeet.nahyeon.mock;

import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.common.service.port.UuidHolder;
import com.depromeet.nahyeon.post.controller.PostController;
import com.depromeet.nahyeon.post.controller.PostCreateController;
import com.depromeet.nahyeon.post.controller.port.PostService;
import com.depromeet.nahyeon.post.service.PostServiceImpl;
import com.depromeet.nahyeon.post.service.port.PostRepository;
import com.depromeet.nahyeon.user.controller.MyInfoController;
import com.depromeet.nahyeon.user.controller.UserController;
import com.depromeet.nahyeon.user.controller.UserCreateController;
import com.depromeet.nahyeon.user.controller.port.UserService;
import com.depromeet.nahyeon.user.service.CertificationService;
import com.depromeet.nahyeon.user.service.UserServiceImpl;
import com.depromeet.nahyeon.user.service.port.MailSender;
import com.depromeet.nahyeon.user.service.port.UserRepository;

import lombok.Builder;

public class TestContainer {

	public final MailSender mailSender;
	public final UserRepository userRepository;
	public final PostRepository postRepository;
	public final UserService userService;
	public final PostService postService;
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

		UserServiceImpl userService = UserServiceImpl.builder()
			.userRepository(this.userRepository)
			.certificationService(new CertificationService(this.mailSender))
			.uuidHolder(uuidHolder)
			.clockHolder(clockHolder)
			.build();
		this.userService = userService;
		this.userController = UserController.builder()
			.userService(this.userService)
			.build();
		this.myInfoController = MyInfoController.builder()
			.userService(this.userService)
			.build();
		this.userCreateController = UserCreateController.builder()
			.userService(this.userService)
			.build();
		this.postController = PostController.builder()
			.postService(this.postService)
			.build();
		this.postCreateController = PostCreateController.builder()
			.postService(this.postService)
			.build();
	}
}
