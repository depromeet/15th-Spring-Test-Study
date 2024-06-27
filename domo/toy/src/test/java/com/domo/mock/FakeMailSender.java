package com.domo.mock;

import com.domo.user.service.port.MailSender;

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
