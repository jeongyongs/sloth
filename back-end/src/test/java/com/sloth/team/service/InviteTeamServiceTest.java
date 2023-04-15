package com.sloth.team.service;

import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import com.sloth.team.domain.Team;
import com.sloth.team.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class InviteTeamServiceTest {

    @Autowired
    InviteTeamService inviteTeamService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("팀 초대 서비스 테스트")
    @Transactional
    void inviteTeamServiceTest() {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        Team team = Team.builder().name("Sloth").owner(member).build();
        teamRepository.save(team);

        // when
        boolean result = inviteTeamService.invite(team.getName(), member.getUsername());

        // then
        Assertions.assertTrue(result);
    }
}
