package com.depromeet.nahyeon.user.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.depromeet.nahyeon.common.domain.exception.CertificationCodeNotMatchedException;
import com.depromeet.nahyeon.common.domain.exception.ResourceNotFoundException;
import com.depromeet.nahyeon.mock.TestClockHolder;
import com.depromeet.nahyeon.mock.TestContainer;
import com.depromeet.nahyeon.mock.TestUuidHolder;
import com.depromeet.nahyeon.user.controller.response.MyProfileResponse;
import com.depromeet.nahyeon.user.controller.response.UserResponse;
import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserStatus;
import com.depromeet.nahyeon.user.domain.UserUpdate;

public class UserControllerTest {

	@Test
	void 사용자는_특정_유저의_정보를_개인정보는_소거된_채_전달_받을_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(1287319L))
			.uuidHolder(new TestUuidHolder("aaaaaaaa-aaaaaaaa-aaaaaaaa"))
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<UserResponse> result = testContainer.userController.getUserById(1);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("nahyeonee99@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("nahyeonee99");
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(100);
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() {
		// given
		UserController userController = TestContainer.builder().build().userController;

		// when
		// then
		assertThatThrownBy(() -> {
			userController.getUserById(1);
		}).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(100L)
			.build());

		// when
		ResponseEntity<Void> result = testContainer.userController.verifyEmail(1L, "aaaaaa-aaaaaa-aaaaaa-aaaaaa");

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
		assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void 사용자는_인증_코드가_일치하지_않을_꼉우_권한_없음_에러를_내려준다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.PENDING)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(100L)
			.build());

		// when
		// then
		assertThatThrownBy(() -> {
			testContainer.userController.verifyEmail(1L, "aaaaaa-aaaaaa-aaaaaa-aaaaab");
		}).isInstanceOf(CertificationCodeNotMatchedException.class);
	}

	@Test
	void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(100L))
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeon-k@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(9879878979879887L)
			.build());

		// when
		ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("nahyeon-k@gmail.com");

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("nahyeon-k@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("nahyeonee99");
		assertThat(result.getBody().getAddress()).isEqualTo("Seoul");
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(100L);
	}

	@Test
	void 사용자는_내_정보를_수정할_수_있다() {
		// given
		TestContainer testContainer = TestContainer.builder()
			.clockHolder(new TestClockHolder(232323423L))
			.build();
		testContainer.userRepository.save(User.builder()
			.id(1L)
			.email("nahyeonee99@gmail.com")
			.nickname("nahyeonee99")
			.address("Seoul")
			.status(UserStatus.ACTIVE)
			.certificationCode("aaaaaa-aaaaaa-aaaaaa-aaaaaa")
			.lastLoginAt(9879878979879887L)
			.build());

		// when
		ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("nahyeonee99@gmail.com",
			UserUpdate.builder()
				.nickname("hyeon")
				.address("Pangyo")
				.build());

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getEmail()).isEqualTo("nahyeonee99@gmail.com");
		assertThat(result.getBody().getNickname()).isEqualTo("hyeon");
		assertThat(result.getBody().getAddress()).isEqualTo("Pangyo");
		assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(result.getBody().getLastLoginAt()).isEqualTo(9879878979879887L);
	}
}
