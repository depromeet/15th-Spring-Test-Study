package com.depromeet.yunbeom.user.service;

import org.springframework.stereotype.Service;

import com.depromeet.yunbeom.user.service.port.MailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationService {

	// 굳이 Controller같은 외부 호출이 없으니 굳이 추상화하지 않는다.
	private final MailSender mailSender;

	private String generateCertificationUrl(long userId, String certificationCode) {
		return "http://localhost:8080/api/users/" + userId + "/verify?certificationCode=" + certificationCode;
	}

	public void send(String email, Long id, String certificationCode) {
		String certificationUrl = generateCertificationUrl(id, certificationCode);
		String title = "Please certify your email address";
		String content = "Please click the following link to certify your email address: " + certificationUrl;
		mailSender.send(email, title, content);
	}
}
