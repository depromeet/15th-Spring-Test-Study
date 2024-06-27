package org.example.demo.user.controller.port;

import org.example.demo.user.domain.User;
import org.example.demo.user.domain.UserCreate;
import org.example.demo.user.domain.UserUpdate;

public interface UserService {

    User getByEmail(String email);

    User getById(long id);

    User create(UserCreate userCreate);

    User update(long id, UserUpdate userUpdate);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}