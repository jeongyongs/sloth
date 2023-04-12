package com.sloth.member.service;

import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CheckUsernameServiceTest {

    @Autowired
    CheckUsernameService checkUsernameService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("아이디 중복 검사")
    @Transactional
    void checkUsernameTest() {

        // given
        Member member = Member.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .name("Jeongyong Lee")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        memberRepository.save(member);

        // when
        boolean result1 = checkUsernameService.check("spring");
        boolean result2 = checkUsernameService.check(member.getUsername());

        // then
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }
}
