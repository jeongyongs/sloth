package com.sloth.team.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.Team;
import com.sloth.team.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class InviteTeamServiceTest {

    @Autowired
    InviteTeamService inviteTeamService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    @DisplayName("팀 초대 서비스 테스트")
    @Transactional
    void inviteTeamServiceTest() {

        // given
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        Team team = Team.builder().name("Sloth").owner(user).build();
        teamRepository.save(team);

        // when
        boolean result = inviteTeamService.invite(team.getName(), user.getUsername());

        // then
        Assertions.assertTrue(result);
    }
}
