package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
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
    UserRepository userRepository;
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
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        String jwt = jwtService.create(user.getUsername());
        Team team = Team.builder().name("Sloth").owner(user).build();
        teamRepository.save(team);
        InviteTeam inviteTeam = InviteTeam.builder().user(user).team(team)
                .expired(new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24).build();
        inviteTeamRepository.save(inviteTeam);
        InviteInfo inviteInfo =
                InviteInfo.builder().id(inviteTeam.getId()).teamName(team.getName()).teamOwner(user.getName()).expired(inviteTeam.getExpired()).build();

        // when
        List<InviteInfo> inviteTeams = checkInviteService.getByMember(jwt);

        // then
        Assertions.assertEquals(inviteInfo, inviteTeams.get(0));
    }
}
