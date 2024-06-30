package com.depromeet.yunbeom.mock;

import com.depromeet.yunbeom.common.service.port.ClockHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

	private final long millis;
	@Override
	public long millis() {
		return millis;
	}
}
