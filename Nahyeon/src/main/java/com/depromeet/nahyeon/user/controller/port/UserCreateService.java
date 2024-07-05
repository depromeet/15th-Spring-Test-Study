package com.depromeet.nahyeon.user.controller.port;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;

public interface UserCreateService {

	User create(UserCreate userCreate);
}
