package depromeet.test.Haneum.common.infrastructure;

import java.util.UUID;
import org.springframework.stereotype.Component;

import depromeet.test.Haneum.common.service.port.UuidHolder;

@Component
public class SystemUuidHolder implements UuidHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
