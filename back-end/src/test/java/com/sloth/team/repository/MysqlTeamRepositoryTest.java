package com.sloth.team.repository;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MysqlTeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("팀 레포지토리 테스트")
    @Transactional
    void teamRepositoryTest() {

        // given
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        Team team = Team.builder().name("Sloth").owner(user).build();
        userRepository.save(user);

        // when
        teamRepository.save(team);
        Team foundTeam1 = teamRepository.findByName("Sloth");
        Team foundTeam2 = teamRepository.findById(foundTeam1.getId());
        List<Team> foundTeams = teamRepository.findAll();
        teamRepository.remove(team);
        Team foundTeam3 = teamRepository.findByName("Sloth");

        // then
        Assertions.assertEquals(team, foundTeam1);
        Assertions.assertEquals(team, foundTeam2);
        Assertions.assertEquals(team, foundTeams.get(0));
        Assertions.assertNull(foundTeam3);
    }
}