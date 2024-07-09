package com.domo.user.controller;

import com.domo.mock.TestContainer;
import com.domo.user.controller.response.UserResponse;
import com.domo.user.domain.UserCreate;
import com.domo.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserCreateControllerTest {

    @Test
    void 사용자는_회원가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() {
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .clockHolder(() -> 1678530673958L)
                .build();
        UserCreate userCreate = UserCreate.builder()
                .email("me@dev-domo.com")
                .nickname("domo짱")
                .address("Pangyo")
                .build();
        ResponseEntity<UserResponse> response = testContainer.userCreateController.createUser(userCreate);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("me@dev-domo.com");
        assertThat(response.getBody().getNickname()).isEqualTo("domo짱");
        assertThat(response.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
    }
}