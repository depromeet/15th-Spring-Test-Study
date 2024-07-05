package com.depromeet.nahyeon.user.controller.port;

import com.depromeet.nahyeon.user.domain.User;

public interface UserReadService {

	User getById(long id);

	User getByEmail(String email);

}
