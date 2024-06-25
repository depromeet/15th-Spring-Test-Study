package com.domo.user.service;

import com.domo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스한다() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        // when
        certificationService.send("me@dev-domo.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        assertThat(fakeMailSender.email).isEqualTo("me@dev-domo.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }

}