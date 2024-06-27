package com.depromeet.yunbeom.post.controller.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PostResponseTest {

	@Test
	public void Post로_응답을_생성할_수_있다() {
		// given
		PostResponse postResponse = new PostResponse();

		// when
		postResponse.setId(1L);
		postResponse.setContent("content");
		postResponse.setCreatedAt(1L);
		postResponse.setModifiedAt(1L);

		// then
		assertEquals(1L, postResponse.getId());
		assertEquals("content", postResponse.getContent());
		assertEquals(1L, postResponse.getCreatedAt());
		assertEquals(1L, postResponse.getModifiedAt());
	}

}