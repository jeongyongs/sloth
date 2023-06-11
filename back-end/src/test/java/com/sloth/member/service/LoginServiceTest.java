package com.sloth.member.service;

import com.sloth.global.auth.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    LoginService loginService;
    @Autowired
    NewMemberService newMemberService;

    @Test
    @DisplayName("로그인 테스트")
    @Transactional
    void LoginTest() {

        // given
        LoginDto loginDto1 = LoginDto.builder()
                .username("sloth1")
                .password("q1w2e3r4")
                .build();

        LoginDto loginDto2 = LoginDto.builder()
                .username("sloth2")
                .password("q1w2e3r4")
                .build();

        NewMemberDto newMemberDto = NewMemberDto.builder()
                .username("sloth1")
                .password("q1w2e3r4")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        newMemberService.newMember(newMemberDto);

        // when
        String jwt1 = loginService.login(loginDto1);
        System.out.println(jwt1);
        String jwt2 = loginService.login(loginDto2);

        // then
        Assertions.assertNotNull(jwt1);
        Assertions.assertNull(jwt2);
    }
}
