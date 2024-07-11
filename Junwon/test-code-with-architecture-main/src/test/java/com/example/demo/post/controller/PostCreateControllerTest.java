package com.example.demo.post.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostCreateControllerTest {

	@Test
	void 사용자는_게시물을_작성할_수_있다() throws Exception {
		// given
		TestContainer testContainer = TestContainer.builder()
				.clockHolder(() -> 1679530673958L)
				.build();
		testContainer.userRepository.save(User.builder()
				.id(1L)
				.email("kok202@naver.com")
				.nickname("kok202")
				.address("Seoul")
				.status(UserStatus.ACTIVE)
				.certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
				.lastLoginAt(100L)
				.build());
		PostCreate postCreate = PostCreate.builder()
				.writerId(1)
				.content("helloworld")
				.build();

		// when
		ResponseEntity<PostResponse> result = testContainer.postCreateController.create(postCreate);

		// then
		assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(result.getBody()).isNotNull();
		assertThat(result.getBody().getContent()).isEqualTo("helloworld");
		assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
		assertThat(result.getBody().getCreatedAt()).isEqualTo(1679530673958L);
	}
}