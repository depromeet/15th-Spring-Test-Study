package com.domo.service;

import com.domo.exception.ResourceNotFoundException;
import com.domo.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.yml")
@SqlGroup({
    @Sql("/sql/user-service-test-data.sql"),
    @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})

class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        // given
        String email = "me@dev-domo.com";

        // when
        UserEntity userEntity = userService.getByEmail(email);

        // then
        assertThat(userEntity.getNickname()).isEqualTo("domo");
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
        UserEntity userEntity = userService.getById(1);

        // then
        assertThat(userEntity.getNickname()).isEqualTo("domo");
    }

    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
        // given
        // when, then
        assertThatThrownBy(() -> userService.getById(2))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}