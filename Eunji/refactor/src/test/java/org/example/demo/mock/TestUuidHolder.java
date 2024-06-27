package org.example.demo.mock;

import lombok.RequiredArgsConstructor;
import org.example.demo.common.service.port.UuidHolder;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    @Override
    public String random() {
        return uuid;
    }
}