package org.example.demo.common.infrastructure;

import java.time.Clock;
import org.example.demo.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }
}