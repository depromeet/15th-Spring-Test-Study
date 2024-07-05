package com.depromeet.yunbeom.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.yunbeom.mock.FakeMailSender;

class CertificationServiceTest {

	@Test
	public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
		// given
		FakeMailSender fakeMailSender = new FakeMailSender();
		CertificationService certificationService = new CertificationService(fakeMailSender);

		// when
		certificationService.send("uiurihappy@naver.com", 1L, "1234asda-1321dada-12dascasc");

		// then
		assertThat(fakeMailSender.email).isEqualTo("uiurihappy@naver.com");
		assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
		assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=1234asda-1321dada-12dascasc");
	}

}