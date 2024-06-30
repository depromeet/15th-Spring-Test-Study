package com.depromeet.yunbeom.common.infrastructure;

import java.time.Clock;

import org.springframework.stereotype.Component;

import com.depromeet.yunbeom.common.service.port.ClockHolder;

@Component
public class SystemClockHolder implements ClockHolder {
	@Override
	public long millis() {
		return Clock.systemUTC().millis();
	}
}
