package depromeet.test.Haneum.user.controller.port;

import depromeet.test.Haneum.user.domain.User;
import depromeet.test.Haneum.user.domain.UserCreate;
import depromeet.test.Haneum.user.domain.UserUpdate;

public interface UserService {

    User getByEmail(String email);

    User getById(long id);

    User create(UserCreate userCreate);

    User update(long id, UserUpdate userUpdate);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
