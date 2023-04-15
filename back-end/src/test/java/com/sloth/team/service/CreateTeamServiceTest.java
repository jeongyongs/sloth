package com.sloth.team.service;

import com.sloth.authentication.service.JwtService;
import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CreateTeamServiceTest {

    @Autowired
    CreateTeamService createTeamService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("팀 생성 서비스 테스트")
    @Transactional
    void createTest() {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        String jwt = jwtService.createJwt("jeongyongs");

        // when
        boolean result = createTeamService.create(jwt, "Sloth");

        // then
        Assertions.assertTrue(result);
    }
}
