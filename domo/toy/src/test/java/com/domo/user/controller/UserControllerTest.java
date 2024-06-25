package com.domo.user.controller;

import com.domo.user.domain.UserStatus;
import com.domo.user.domain.UserUpdate;
import com.domo.user.infstructure.UserEntity;
import com.domo.user.infstructure.UserRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql("/sql/user-controller-test-data.sql"),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_제거된채_전달받을_수_있다() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("me@dev-domo.com"))
                .andExpect(jsonPath("$.nickname").value("domo"))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }


    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_API호출할_경우_404응답을_받는다() throws Exception {
        mockMvc.perform(get("/api/users/112412412"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Users에서 ID 112412412를 찾을 수 없습니다."));
    }

    @Test
    void 사용자는_인증코드로_계정을_활성화할_수_있다() throws Exception {
        mockMvc.perform(get("/api/users/2/verify")
                        .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
                .andExpect(status().isFound());
        UserEntity userEntity = userRepository.findById(2L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_가져온다() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .header("EMAIL", "me@dev-domo.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("me@dev-domo.com"))
                .andExpect(jsonPath("$.nickname").value("domo"))
                .andExpect(jsonPath("$.address").value("Seoul"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("domo짱")
                .address("Pangyo")
                .build();

        mockMvc.perform(put("/api/users/me")
                        .header("EMAIL", "me@dev-domo.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("me@dev-domo.com"))
                .andExpect(jsonPath("$.nickname").value("domo짱"))
                .andExpect(jsonPath("$.address").value("Pangyo"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}