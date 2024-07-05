package com.depromeet.nahyeon.user.controller.port;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserCreate;
import com.depromeet.nahyeon.user.domain.UserUpdate;

public interface UserService {

	User getById(long id);

	User getByEmail(String email);

	User update(long id, UserUpdate userUpdate);

	User create(UserCreate userCreate);

	void login(long id);

	void verifyEmail(long id, String certificationCode);
}
