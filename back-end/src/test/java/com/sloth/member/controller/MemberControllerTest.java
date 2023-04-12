package com.sloth.member.controller;

import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
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
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("아이디 중복 확인")
    void checkRequest() throws Exception {

        // given
        String username = "sloth";

        // when
        mockMvc.perform(post("/api/check/username").param("username", username))
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
        mockMvc.perform(post("/api/members").params(params))
                .andDo(print())

                // then
                .andExpect(status().isOk());

        // when
        mockMvc.perform(post("/api/members").params(params))
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

        mockMvc.perform(post("/api/members").params(params));

        Member member = memberRepository.findByUsername("sloth");

        // when
        mockMvc.perform(get("/api/members/" + member.getId()))
                .andDo(print())

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 요청")
    @Transactional
    void loginRequest() throws Exception {

        // given
        MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
        params1.add("username", "sloth");
        params1.add("password", "q1w2e3r4");
        params1.add("name", "Jeongyong Lee");
        params1.add("email", "jeongyongs@sloth.com");
        params1.add("phone", "000-0000-0000");

        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add("username", "sloth");
        params2.add("password", "q1w2e3r4");

        MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>();
        params3.add("username", "sloth");
        params3.add("password", "q1w2e3r4!");

        mockMvc.perform(post("/api/members").params(params1));

        // when
        mockMvc.perform(post("/api/login").params(params2))
                .andDo(print())

                // then
                .andExpect(status().isOk());

        // when
        mockMvc.perform(post("/api/login").params(params3))
                .andDo(print())

                // then
                .andExpect(status().isConflict());
    }
}
