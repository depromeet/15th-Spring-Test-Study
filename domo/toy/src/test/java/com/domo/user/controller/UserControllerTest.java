package com.domo.user.controller;

import com.domo.common.domain.exception.CertificationCodeNotMatchedException;
import com.domo.common.domain.exception.ResourceNotFoundException;
import com.domo.mock.TestContainer;
import com.domo.user.controller.port.UserReadService;
import com.domo.user.controller.response.MyProfileResponse;
import com.domo.user.controller.response.UserResponse;
import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import com.domo.user.domain.UserUpdate;
import com.domo.user.infstructure.UserEntity;
import com.domo.user.infstructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {
    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_제거된채_전달받을_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                        .id(1L)
                        .email("me@dev-domo.com")
                        .nickname("domo")
                        .address("Seoul")
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .status(UserStatus.ACTIVE)
                        .lastLoginAt(1678530673958L)
                        .build());

        // when
        ResponseEntity<UserResponse> response = testContainer.userController.getUserById(1);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getNickname()).isEqualTo("domo");
        assertThat(response.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(response.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
    }


    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_API호출할_경우_404응답을_받는다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when, then
        assertThatThrownBy(() -> testContainer.userController.getUserById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Users에서 ID 1를 찾을 수 없습니다.");
    }

    @Test
    void 사용자는_인증코드로_계정을_활성화할_수_있다() {
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.PENDING)
                .lastLoginAt(1678530673958L)
                .build());

        // when
        ResponseEntity<Void> response = testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증코드가_일치하지_않을경우_에러를_발생시킨다() {
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.PENDING)
                .lastLoginAt(1678530673958L)
                .build());

        // when, then
        assertThatThrownBy(() -> testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac"))
                .isInstanceOf(CertificationCodeNotMatchedException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_가져온다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .build());

        // when
        ResponseEntity<MyProfileResponse> response = testContainer.userController.getMyInfo("me@dev-domo.com");

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getNickname()).isEqualTo("domo");
        assertThat(response.getBody().getAddress()).isEqualTo("Seoul");
        assertThat(response.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(response.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("me@dev-domo.com")
                .nickname("domo")
                .address("Seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(1678530673958L)
                .build());

        // when
        ResponseEntity<MyProfileResponse> response = testContainer.userController.updateMyInfo("me@dev-domo.com", UserUpdate.builder()
                .nickname("domo1")
                .address("Pangyo")
                .build());

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getNickname()).isEqualTo("domo1");
        assertThat(response.getBody().getAddress()).isEqualTo("Pangyo");
        assertThat(response.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(response.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
    }
}