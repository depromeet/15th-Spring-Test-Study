package com.depromeet.yunbeom.mock;

import com.depromeet.yunbeom.common.service.port.UuidHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

	private final String uuid;

	@Override
	public String random() {
		return uuid;
	}
}
