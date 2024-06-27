package com.depromeet.nahyeon.mock;

import com.depromeet.nahyeon.common.service.port.UuidHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

	private final String uuid;

	@Override
	public String random() {
		return uuid;
	}
}
