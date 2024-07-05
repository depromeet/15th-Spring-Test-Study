package com.domo.user.controller.port;

import com.domo.user.domain.User;
import com.domo.user.domain.UserCreate;
import com.domo.user.domain.UserUpdate;

public interface UserReadService {
    User getByEmail(String email);
    User getById(long id);
}
