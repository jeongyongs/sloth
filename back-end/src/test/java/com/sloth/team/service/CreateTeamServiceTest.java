package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CreateTeamServiceTest {

    @Autowired
    CreateTeamService createTeamService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("팀 생성 서비스 테스트")
    @Transactional
    void createTest() {

        // given
        User user = User.builder().username("jeongyongs").password("q1w2e3r4").name("Jeongyong Lee")
                .email("Jeongyongs@sloth.com").phone("000-0000-0000").build();
        userRepository.save(user);
        String jwt = jwtService.create("jeongyongs");

        // when
        boolean result = createTeamService.create(jwt, "Sloth");

        // then
        Assertions.assertTrue(result);
    }
}
