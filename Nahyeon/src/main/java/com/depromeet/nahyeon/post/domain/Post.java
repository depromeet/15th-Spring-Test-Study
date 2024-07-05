package com.depromeet.nahyeon.post.domain;

import com.depromeet.nahyeon.common.service.port.ClockHolder;
import com.depromeet.nahyeon.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

	private final Long id;
	private final String content;
	private final Long createdAt;
	private final Long modifiedAt;
	private final User writer;

	@Builder
	public Post(Long id, String content, Long createdAt, Long modifiedAt, User writer) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.writer = writer;
	}

	public static Post of(User user, PostCreate postCreate, ClockHolder clockHolder) {
		long now = clockHolder.millis();
		return Post.builder()
			.content(postCreate.getContent())
			.createdAt(now)
			.modifiedAt(now)
			.writer(user)
			.build();
	}

	public Post update(PostUpdate postUpdate, ClockHolder clockHolder) {
		return Post.builder()
			.id(id)
			.content(postUpdate.getContent())
			.createdAt(createdAt)
			.modifiedAt(clockHolder.millis())
			.writer(writer)
			.build();
	}
}
