package com.sloth.member.controller;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("아이디 중복 확인")
    void checkRequest() throws Exception {

        // given
        String username = "sloth";

        // when
        mockMvc.perform(post("/api/member/check").param("username", username))
                .andDo(print())

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("username: " + username + " is available."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("회원가입 요청")
    @Transactional
    void joinRequest() throws Exception {

        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "sloth");
        params.add("password", "q1w2e3r4");
        params.add("name", "Jeongyong Lee");
        params.add("email", "jeongyongs@sloth.com");
        params.add("phone", "000-0000-0000");

        // when
        mockMvc.perform(post("/api/member").params(params))
                .andDo(print())

                // then
                .andExpect(status().isOk());

        // when
        mockMvc.perform(post("/api/member").params(params))
                .andDo(print())

                // then
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("회원 정보 조회 테스트")
    @Transactional
    void getMemberDetailTest() throws Exception {

        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "sloth");
        params.add("password", "q1w2e3r4");
        params.add("name", "Jeongyong Lee");
        params.add("email", "jeongyongs@sloth.com");
        params.add("phone", "000-0000-0000");

        mockMvc.perform(post("/api/member").params(params));
        User user = userRepository.findByUsername("sloth");
        String jwt = jwtService.create("sloth");

        // when
        mockMvc.perform(get("/api/member").header("Authorization", "bearer " + jwt))
                .andDo(print())

                // then
                .andExpect(status().isOk());
    }
}
