package com.sloth.authentication.service;

import com.sloth.member.dto.NewMemberDto;
import com.sloth.member.service.NewMemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    NewMemberService newMemberService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtService jwtService;
    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("인증 테스트")
    @Transactional
    void authenticationTest() throws Exception {

        // given
        NewMemberDto newMemberDto = NewMemberDto.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();
        newMemberService.newMember(newMemberDto);
        String token = jwtService.createJwt("sloth");

        // when
        Authentication authentication = authenticationService.authenticate(token);

        // then
        Assertions.assertEquals("sloth", authentication.getPrincipal().toString());
        Assertions.assertTrue(encoder.matches("q1w2e3r4", authentication.getCredentials().toString()));
    }
}
