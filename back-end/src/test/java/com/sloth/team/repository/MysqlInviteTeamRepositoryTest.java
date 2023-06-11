package com.sloth.team.repository;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
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
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("MySQL 팀초대 레포지토리 테스트")
    @Transactional
    void inviteTeamRepositoryTest() {

        // given
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        Team team = Team.builder().name("Sloth").owner(user).build();
        InviteTeam inviteTeam = InviteTeam.builder().team(team).user(user).expired(new Date().getTime()).build();
        userRepository.save(user);
        teamRepository.save(team);

        // when
        inviteTeamRepository.save(inviteTeam);
        InviteTeam foundInviteTeamById1 = inviteTeamRepository.findById(inviteTeam.getId());
        List<InviteTeam> foundInviteTeamByTeam1 = inviteTeamRepository.findByNTeam(team);
        List<InviteTeam> foundInviteTeamByMember1 = inviteTeamRepository.findByMember(user);
        inviteTeamRepository.remove(inviteTeam);
        InviteTeam foundInviteTeamById2 = inviteTeamRepository.findById(inviteTeam.getId());
        List<InviteTeam> foundInviteTeamByTeam2 = inviteTeamRepository.findByNTeam(team);
        List<InviteTeam> foundInviteTeamByMember2 = inviteTeamRepository.findByMember(user);

        // then
        Assertions.assertEquals(inviteTeam, foundInviteTeamById1);
        Assertions.assertEquals(inviteTeam, foundInviteTeamByTeam1.get(0));
        Assertions.assertEquals(inviteTeam, foundInviteTeamByMember1.get(0));
        Assertions.assertNull(foundInviteTeamById2);
        Assertions.assertNull(foundInviteTeamByTeam2);
        Assertions.assertNull(foundInviteTeamByMember2);
    }
}
