package com.domo.medium;

import com.domo.user.domain.User;
import com.domo.user.domain.UserStatus;
import com.domo.user.infstructure.UserEntity;
import com.domo.user.service.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus로_유저_데이터를_찾아올_수_있다() {
        // given
        // when
        Optional<User> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus는_데이터가_없으면_Optional_Empty를_내려준다() {
        // given
        // when
        Optional<User> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

        // then
        assertThat(result.isPresent()).isFalse();
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus로_유저_데이터를_찾아올_수_있다() {
        // given
        // when
        Optional<User> result = userRepository.findByEmailAndStatus("me@dev-domo.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus는_데이터가_없으면_Optional_Empty를_내려준다() {
        // given
        // when
        Optional<User> result = userRepository.findByEmailAndStatus("me@dev-domo.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }
}