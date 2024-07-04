package com.depromeet.nahyeon.medium;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class HealthCheckControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void 헬스_체크_응답이_200으로_내려온다() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/health_check.html"))
			.andExpect(status().isOk());
	}
}