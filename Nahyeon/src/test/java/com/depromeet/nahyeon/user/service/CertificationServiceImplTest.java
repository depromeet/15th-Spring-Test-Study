package com.depromeet.nahyeon.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.depromeet.nahyeon.mock.FakeMailSender;

public class CertificationServiceImplTest {

	@Test
	public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {

		// given
		FakeMailSender fakeMailSender = new FakeMailSender();
		CertificationService certificationService = new CertificationService(fakeMailSender);

		// when
		certificationService.send("nahyeon@gmail.com", 1, "aaaaaaaa-aaaaaaaa-aaaaaaaa");

		// then
		assertThat(fakeMailSender.email).isEqualTo("nahyeon@gmail.com");
		assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
		assertThat(fakeMailSender.content).isEqualTo(
			"Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaaaaaa-aaaaaaaa");
	}
}