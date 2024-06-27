package com.depromeet.nahyeon.common.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.depromeet.nahyeon.common.service.port.UuidHolder;

@Component
public class SystemUuidHolder implements UuidHolder {
	@Override
	public String random() {
		return UUID.randomUUID().toString();
	}
}
