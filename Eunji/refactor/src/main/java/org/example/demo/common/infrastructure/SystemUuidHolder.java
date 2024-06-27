package org.example.demo.common.infrastructure;

import java.util.UUID;
import org.example.demo.common.service.port.UuidHolder;
import org.springframework.stereotype.Component;

@Component
public class SystemUuidHolder implements UuidHolder {
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
