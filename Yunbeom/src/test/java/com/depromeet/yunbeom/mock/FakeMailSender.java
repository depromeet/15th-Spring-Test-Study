package com.depromeet.yunbeom.mock;

import com.depromeet.yunbeom.user.service.port.MailSender;

public class FakeMailSender implements MailSender {

	// 실제 값 확인 용도
	public String email;
	public String title;
	public String content;

	@Override
	public void send(String email, String title, String content) {
		this.email = email;
		this.title = title;
		this.content = content;
	}
}
