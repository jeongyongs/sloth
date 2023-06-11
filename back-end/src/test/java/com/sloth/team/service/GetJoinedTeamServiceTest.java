package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.Team;
import com.sloth.team.domain.TeamMember;
import com.sloth.team.dto.TeamInfo;
import com.sloth.team.repository.TeamMemberRepository;
import com.sloth.team.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class GetJoinedTeamServiceTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    GetJoinedTeamService getJoinedTeamService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Test
    @DisplayName("가입한 팀 조회 테스트")
    @Transactional
    void getJoinedTeamServiceTest() {

        // given
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        Team team1 = Team.builder().name("Sloth1").owner(user).build();
        Team team2 = Team.builder().name("Sloth2").owner(user).build();
        Team team3 = Team.builder().name("Sloth3").owner(user).build();
        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
        TeamMember teamMember1 = TeamMember.builder().team(team1).user(user).build();
        TeamMember teamMember2 = TeamMember.builder().team(team2).user(user).build();
        TeamMember teamMember3 = TeamMember.builder().team(team3).user(user).build();
        teamMemberRepository.save(teamMember1);
        teamMemberRepository.save(teamMember2);
        teamMemberRepository.save(teamMember3);
        String jwt = jwtService.create("jeongyongs");

        // when
        List<TeamInfo> list = getJoinedTeamService.getList(jwt);

        // then
        for (TeamInfo teamInfo : list) {
            System.out.println(teamInfo.getName());
        }
    }
}
