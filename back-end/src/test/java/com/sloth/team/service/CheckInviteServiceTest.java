package com.sloth.team.service;

import com.sloth.authentication.service.JwtService;
import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;
import com.sloth.team.dto.InviteInfo;
import com.sloth.team.repository.InviteTeamRepository;
import com.sloth.team.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
class CheckInviteServiceTest {

    @Autowired
    CheckInviteService checkInviteService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InviteTeamRepository inviteTeamRepository;
    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("멤버 -> 팀 초대 확인")
    @Transactional
    void checkInviteTeamByMemberTest() {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        memberRepository.save(member);
        String jwt = jwtService.createJwt(member.getUsername());
        Team team = Team.builder().name("Sloth").owner(member).build();
        teamRepository.save(team);
        InviteTeam inviteTeam = InviteTeam.builder().member(member).team(team)
                .expired(new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24).build();
        inviteTeamRepository.save(inviteTeam);
        InviteInfo inviteInfo =
                InviteInfo.builder().id(inviteTeam.getId()).teamName(team.getName()).teamOwner(member.getName()).expired(inviteTeam.getExpired()).build();

        // when
        List<InviteInfo> inviteTeams = checkInviteService.getByMember(jwt);

        // then
        Assertions.assertEquals(inviteInfo, inviteTeams.get(0));
    }
}
