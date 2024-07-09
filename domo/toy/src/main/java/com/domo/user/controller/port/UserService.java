package com.domo.user.controller.port;

import com.domo.user.domain.User;
import com.domo.user.domain.UserCreate;
import com.domo.user.domain.UserUpdate;

public interface UserService {
    User getByEmail(String email);
    User getById(long id);
    User create(UserCreate userCreate);
    User update(long id, UserUpdate userUpdate);
    void login(long id);
    void verifyEmail(long id, String certificationCode);
}
