package com.sloth.member.service;

import com.sloth.member.dto.NewMemberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class NewMemberServiceTest {

    @Autowired
    NewMemberService newMemberService;

    @Test
    @DisplayName("회원 가입 테스트")
    @Transactional
    void newMemberTest() {

        // given
        NewMemberDto newMemberDto = NewMemberDto.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        // when
        boolean result1 = newMemberService.newMember(newMemberDto);
        boolean result2 = newMemberService.newMember(newMemberDto);

        // then
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }
}
