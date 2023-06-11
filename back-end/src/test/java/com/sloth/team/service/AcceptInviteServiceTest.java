package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;
import com.sloth.team.domain.TeamMember;
import com.sloth.team.repository.InviteTeamRepository;
import com.sloth.team.repository.TeamMemberRepository;
import com.sloth.team.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class AcceptInviteServiceTest {

    @Autowired
    AcceptInviteService acceptInviteService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    InviteTeamRepository inviteTeamRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Test
    @DisplayName("팀 초대 수락 테스트")
    @Transactional
    void acceptInviteTest() {

        // given
        User user1 = User.builder().username("owner").password("q1w2e3r4").name("owner")
                .email("owner@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user1);
        User user2 = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user2);
        Team team1 = Team.builder().name("슬로스").owner(user1).build();
        teamRepository.save(team1);
        Team team2 = Team.builder().name("배달의 민족").owner(user1).build();
        teamRepository.save(team2);
        InviteTeam inviteTeam1 = InviteTeam.builder().team(team1).user(user2).build();
        inviteTeamRepository.save(inviteTeam1);
        InviteTeam inviteTeam2 = InviteTeam.builder().team(team2).user(user2).build();
        inviteTeamRepository.save(inviteTeam2);
        String jwt = jwtService.create(user2.getUsername());

        // when
        boolean result1 = acceptInviteService.accept(jwt, inviteTeam1.getId());
        boolean result2 = acceptInviteService.accept(jwt, inviteTeam2.getId());
        List<TeamMember> foundTeams = teamMemberRepository.findByMember(user2);

        // then
        Assertions.assertTrue(result1);
        Assertions.assertTrue(result2);
        for (TeamMember teamMember : foundTeams) {
            System.out.println(teamMember.getId() + ". " + teamMember.getTeam().getName() + "에 가입되어 있습니다.");
        }
    }
}
