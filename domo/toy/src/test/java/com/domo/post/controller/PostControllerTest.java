package com.domo.post.controller;

import com.domo.post.domain.PostUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

//    @Test
//    void 사용자는_게시물을_단건_조회_할_수_있다() throws Exception {
//        mockMvc.perform(get("/api/posts/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.content").value("Hello, world!"))
//                .andExpect(jsonPath("$.writer.id").isNumber())
//                .andExpect(jsonPath("$.writer.email").value("me@dev-domo.com"))
//                .andExpect(jsonPath("$.writer.nickname").value("domo"));
//    }
//
//    @Test
//    void 사용자는_존재하지_않는_게시물을_조회할_경우_에러가_발생한다() throws Exception {
//        mockMvc.perform(get("/api/posts/1123123"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Posts에서 ID 1123123를 찾을 수 없습니다."));
//    }
//
//
//    @Test
//    void 사용자는_게시물을_수정_할_수_있다() throws Exception {
//        PostUpdate postUpdate = PostUpdate.builder()
//                .content("foobar")
//                        .build();
//
//        mockMvc.perform(put("/api/posts/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(postUpdate)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.content").value("foobar"))
//                .andExpect(jsonPath("$.writer.id").isNumber())
//                .andExpect(jsonPath("$.writer.email").value("me@dev-domo.com"))
//                .andExpect(jsonPath("$.writer.nickname").value("domo"));
//    }
}