package com.depromeet.yunbeom.user.service;

import org.springframework.stereotype.Service;

import com.depromeet.yunbeom.user.controller.port.CertificationService;
import com.depromeet.yunbeom.user.service.port.MailSender;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

	private final MailSender mailSender;

	private String generateCertificationUrl(long userId, String certificationCode) {
		return "http://localhost:8080/api/users/" + userId + "/verify?certificationCode=" + certificationCode;
	}

	@Override
	public void send(String email, Long id, String certificationCode) {
		String certificationUrl = generateCertificationUrl(id, certificationCode);
		String title = "Please certify your email address";
		String content = "Please click the following link to certify your email address: " + certificationUrl;
		mailSender.send(email, title, content);
	}
}
