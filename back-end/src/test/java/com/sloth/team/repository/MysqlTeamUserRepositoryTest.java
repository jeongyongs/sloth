package com.sloth.team.repository;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.Team;
import com.sloth.team.domain.TeamMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MysqlTeamUserRepositoryTest {

    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("팀멤버 레포지토리 테스트")
    @Transactional
    void teamMemberRepositoryTest() {

        // given
        User user =
                User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee").email("jeongyongs" +
                        "@sloth.com").phone("000-0000-0000").build();
        Team team = Team.builder().owner(user).name("Sloth").build();
        TeamMember teamMember = TeamMember.builder().user(user).team(team).build();
        userRepository.save(user);
        teamRepository.save(team);

        // when
        teamMemberRepository.save(teamMember);
        TeamMember foundTeamMember1 = teamMemberRepository.findById(teamMember.getId());
        List<TeamMember> foundTeamMembersByTeam1 = teamMemberRepository.findByTeam(team);
        List<TeamMember> foundTeamMembersByMember = teamMemberRepository.findByMember(user);
        teamMemberRepository.remove(teamMember);
        TeamMember foundTeamMember2 = teamMemberRepository.findById(teamMember.getId());

        // then
        Assertions.assertEquals(teamMember, foundTeamMember1);
        Assertions.assertEquals(teamMember, foundTeamMembersByMember.get(0));
        Assertions.assertEquals(teamMember, foundTeamMembersByTeam1.get(0));
        Assertions.assertNull(foundTeamMember2);
    }
}
