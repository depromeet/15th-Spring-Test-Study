package com.depromeet.yunbeom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
class YunbeomApplicationTests {

	@Test
	void contextLoads() {
	}
}
