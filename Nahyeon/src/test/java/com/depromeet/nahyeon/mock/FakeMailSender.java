package com.depromeet.nahyeon.mock;

import com.depromeet.nahyeon.user.service.port.MailSender;

public class FakeMailSender implements MailSender {

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
