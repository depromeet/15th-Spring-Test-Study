package com.depromeet.nahyeon.user.controller.port;

import com.depromeet.nahyeon.user.domain.User;
import com.depromeet.nahyeon.user.domain.UserUpdate;

public interface UserUpdateService {
	User update(long id, UserUpdate userUpdate);
}
