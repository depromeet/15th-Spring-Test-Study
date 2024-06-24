package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    public void UserCreate_객체로_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@kakao.com")
                .address("Pangyo")
                .nickname("kok202")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        // then
        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getEmail()).isEqualTo("kok202@kakao.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("kok202");
        Assertions.assertThat(user.getAddress()).isEqualTo("Pangyo");
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        Assertions.assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    public void UserUpdate_객체로_데이터를_업데이트할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kok202-k")
                .address("Pangyo")
                .build();

        // when
        user = user.update(userUpdate);

        // then
        Assertions.assertThat(user.getId()).isEqualTo(1L);
        Assertions.assertThat(user.getEmail()).isEqualTo("kok202@kakao.com");
        Assertions.assertThat(user.getAddress()).isEqualTo("Pangyo");
        Assertions.assertThat(user.getNickname()).isEqualTo("kok202-k");
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        Assertions.assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        Assertions.assertThat(user.getLastLoginAt()).isEqualTo(100L);
    }

    @Test
    public void 로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        Assertions.assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    public void 유효한_인증코드로_계정을_활성화할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
    @Test
    public void 잘못된_인증코드로_계정을_활성화하면_에러를_던진다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when

        // then
        Assertions.assertThatThrownBy(() -> {
            user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}