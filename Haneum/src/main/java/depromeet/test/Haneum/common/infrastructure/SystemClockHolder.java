package depromeet.test.Haneum.common.infrastructure;

import java.time.Clock;
import org.springframework.stereotype.Component;

import depromeet.test.Haneum.common.service.port.ClockHolder;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }
}
