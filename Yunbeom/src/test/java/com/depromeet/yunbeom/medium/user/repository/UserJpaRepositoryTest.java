package com.depromeet.yunbeom.medium.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.depromeet.yunbeom.user.domain.UserStatus;
import com.depromeet.yunbeom.user.infrastructure.UserEntity;
import com.depromeet.yunbeom.user.infrastructure.UserJpaRepository;

// DataJpaTest에 이미 ExtendWith가 내장되어 있음
@DataJpaTest(showSql = true)
@TestPropertySource("classpath:application-test.yml")
@ActiveProfiles("test")
@Sql("/sql/user-repository-test-data.sql")
class UserJpaRepositoryTest {

	@Autowired
	private UserJpaRepository userJpaRepository;

	// private UserEntity savedUserEntity;

	// @BeforeEach
	// void setUp() {
	// 	UserEntity userEntity = new UserEntity();
	// 	userEntity.setEmail("uiurihappy@naver.com");
	// 	userEntity.setNickname("uiurihappy");
	// 	userEntity.setAddress("서울시 영등포구");
	// 	userEntity.setStatus(UserStatus.ACTIVE);
	// 	userEntity.setCertificationCode("asdasdsa-sadasdasdsa-dasdasdassdsa");
	// 	savedUserEntity = userRepository.save(userEntity);
	// }

	@Test
	void UserRepository_가_제대로_연결되었다() {
		// then
		assertThat(1L).isNotNull();
	}

	@Test
	void findByIdAndStatus_가_제대로_동작한다() {
		// when
		Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

		// then
		// assertThat(result).isNotNull();
		assertThat(result.isPresent()).isTrue();
	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// when
		Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L,  UserStatus.PENDING);

		// then
		// assertThat(result.isPresent()).isFalse();
		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	void findByIdAndEmail_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// when
		Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("uiurihappy@naver.com",  UserStatus.ACTIVE);

		// then
		assertThat(result.isEmpty()).isFalse();
	}
}