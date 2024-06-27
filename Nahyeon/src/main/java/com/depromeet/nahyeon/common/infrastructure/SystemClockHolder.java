package com.depromeet.nahyeon.common.infrastructure;

import java.time.Clock;

import org.springframework.stereotype.Component;

import com.depromeet.nahyeon.common.service.port.ClockHolder;

@Component
public class SystemClockHolder implements ClockHolder {

	@Override
	public long millis() {
		return Clock.systemUTC().millis();
	}
}
