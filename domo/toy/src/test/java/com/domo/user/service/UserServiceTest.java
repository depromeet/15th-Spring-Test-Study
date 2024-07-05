package com.domo.user.service;

import com.domo.common.domain.exception.CertificationCodeNotMatchedException;
import com.domo.common.domain.exception.ResourceNotFoundException;
import com.domo.mock.FakeMailSender;
import com.domo.mock.FakeUserRepository;
import com.domo.mock.TestClockHolder;
import com.domo.mock.TestUuidHolder;
import com.domo.user.domain.User;
import com.domo.user.domain.UserCreate;
import com.domo.user.domain.UserStatus;
import com.domo.user.domain.UserUpdate;
import com.domo.user.service.CertificationService;
import com.domo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserService.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .certificationService(new CertificationService(fakeMailSender))
                .userRepository(fakeUserRepository)
                .build();
        fakeUserRepository.save(
                User.builder()
                        .id(1L)
                        .email("me@dev-domo.com")
                        .nickname("domo")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .build()
        );
        fakeUserRepository.save(
                User.builder()
                        .id(2L)
                        .email("me@dev-domo1.com")
                        .nickname("domo1")
                        .address("Seoul")
                        .status(UserStatus.PENDING)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                        .build()
        );
    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        String email = "me@dev-domo.com";

        // when
        User user = userService.getByEmail(email);

        // then
        assertThat(user.getNickname()).isEqualTo("domo");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        String email = "me@dev-domo1.com";

        // when, then
        assertThatThrownBy(() -> userService.getByEmail(email))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        // when
        User user = userService.getById(1);

        // then
        assertThat(user.getNickname()).isEqualTo("domo");
    }

    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        // when, then
        assertThatThrownBy(() -> userService.getById(2))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto를_이용하여_유저를_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("me@dev-domo2.com")
                .address("Seoul")
                .nickname("domo2")
                .build();

        // when
        User result = userService.create(userCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void userUpdateDto를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("Inchon")
                .nickname("domo짱")
                .build();

        // when
        User result = userService.update(1, userUpdate);

        // then
        User user = userService.getById(1);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAddress()).isEqualTo("Inchon");
        assertThat(result.getNickname()).isEqualTo("domo짱");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // when
        userService.login(1);

        // then
        User user = userService.getById(1);
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void PENDING상태인_사용자는_인증코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING상태인_사용자는_잘못된_인증코드를_받으면_에러가_발생한다() {
        // given
        // when, hen
        assertThatThrownBy(() -> userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}