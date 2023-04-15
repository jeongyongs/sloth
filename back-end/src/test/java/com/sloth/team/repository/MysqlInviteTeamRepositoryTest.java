package com.sloth.team.repository;

import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
class MysqlInviteTeamRepositoryTest {

    @Autowired
    InviteTeamRepository inviteTeamRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("MySQL 팀초대 레포지토리 테스트")
    @Transactional
    void inviteTeamRepositoryTest() {

        // given
        Member member = Member.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        Team team = Team.builder().name("Sloth").owner(member).build();
        InviteTeam inviteTeam = InviteTeam.builder().team(team).member(member).expired(new Date().getTime()).build();
        memberRepository.save(member);
        teamRepository.save(team);

        // when
        inviteTeamRepository.save(inviteTeam);
        InviteTeam foundInviteTeamById1 = inviteTeamRepository.findById(inviteTeam.getId());
        List<InviteTeam> foundInviteTeamByTeam1 = inviteTeamRepository.findByNTeam(team);
        List<InviteTeam> foundInviteTeamByMember1 = inviteTeamRepository.findByMember(member);
        inviteTeamRepository.remove(inviteTeam);
        InviteTeam foundInviteTeamById2 = inviteTeamRepository.findById(inviteTeam.getId());
        List<InviteTeam> foundInviteTeamByTeam2 = inviteTeamRepository.findByNTeam(team);
        List<InviteTeam> foundInviteTeamByMember2 = inviteTeamRepository.findByMember(member);

        // then
        Assertions.assertEquals(inviteTeam, foundInviteTeamById1);
        Assertions.assertEquals(inviteTeam, foundInviteTeamByTeam1.get(0));
        Assertions.assertEquals(inviteTeam, foundInviteTeamByMember1.get(0));
        Assertions.assertNull(foundInviteTeamById2);
        Assertions.assertNull(foundInviteTeamByTeam2);
        Assertions.assertNull(foundInviteTeamByMember2);
    }
}
