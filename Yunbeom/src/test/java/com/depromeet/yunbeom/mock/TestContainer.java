package com.depromeet.yunbeom.mock;

import com.depromeet.yunbeom.common.service.port.ClockHolder;
import com.depromeet.yunbeom.common.service.port.UuidHolder;
import com.depromeet.yunbeom.post.controller.PostController;
import com.depromeet.yunbeom.post.controller.PostCreateController;
import com.depromeet.yunbeom.post.controller.port.PostService;
import com.depromeet.yunbeom.post.service.PostServiceImpl;
import com.depromeet.yunbeom.post.service.port.PostRepository;
import com.depromeet.yunbeom.user.controller.MyInfoController;
import com.depromeet.yunbeom.user.controller.UserController;
import com.depromeet.yunbeom.user.controller.UserCreateController;
import com.depromeet.yunbeom.user.service.CertificationService;
import com.depromeet.yunbeom.user.service.UserServiceImpl;
import com.depromeet.yunbeom.user.service.port.MailSender;
import com.depromeet.yunbeom.user.service.port.UserRepository;

import lombok.Builder;

public class TestContainer {

	// Spring IoC 컨테이너처럼 동작하는 test Container 생성
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
	// public final TestClockHolder clockHolder;
	// public final TestUuidHolder uuidHolder;

	// @Builder
	// public TestContainer(long initialMillis, String initialUuid) {
	// 	this.testClockHolder = new TestClockHolder(initialMillis);
	// 	this.testUuidHolder = new TestUuidHolder(initialUuid);
	// 	this.mailSender = new FakeMailSender();
	// 	this.userRepository = new FakeUserRepository();
	// 	this.postRepository = new FakePostRepository();
	// 	this.postService = PostServiceImpl.builder()
	// 		.postRepository(this.postRepository)
	// 		.userRepository(this.userRepository)
	// 		.clockHolder(testClockHolder)
	// 		.build();
	// 	this.certificationService = new CertificationService(this.mailSender);
	// 	UserServiceImpl userService = UserServiceImpl.builder()
	// 		.uuidHolder(testUuidHolder)
	// 		.clockHolder(testClockHolder)
	// 		.userRepository(this.userRepository)
	// 		.certificationService(this.certificationService)
	// 		.build();
	// 	this.userController = UserController.builder()
	// 		.userService(userService)
	// 		.build();
	// 	this.myInfoController = MyInfoController.builder()
	// 		.userService(userService)
	// 		.build();
	// 	this.userCreateController = UserCreateController.builder()
	// 		.userService(userService)
	// 		.build();
	// 	this.postController = PostController.builder()
	// 		.postService(postService)
	// 		.build();
	// 	this.postCreateController = PostCreateController.builder()
	// 		.postService(postService)
	// 		.build();
	// }

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