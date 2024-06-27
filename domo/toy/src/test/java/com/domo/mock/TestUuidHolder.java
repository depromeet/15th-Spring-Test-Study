package com.domo.mock;

import com.domo.common.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {
    private final String uuid;

    @Override
    public String random() {
        return uuid;
    }
}
