package com.sloth.authentication.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class authenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

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

        mockMvc.perform(post("/api/member").params(params1));

        // when
        mockMvc.perform(post("/api/auth").params(params2))
                .andDo(print())

                // then
                .andExpect(status().isOk());

        // when
        mockMvc.perform(post("/api/auth").params(params3))
                .andDo(print())

                // then
                .andExpect(status().isConflict());
    }
}
