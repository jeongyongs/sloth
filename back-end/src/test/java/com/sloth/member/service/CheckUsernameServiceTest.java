package com.sloth.member.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CheckUsernameServiceTest {

    @Autowired
    CheckUsernameService checkUsernameService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("아이디 중복 검사")
    @Transactional
    void checkUsernameTest() {

        // given
        User user = User.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .name("Jeongyong Lee")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        userRepository.save(user);

        // when
        boolean result1 = checkUsernameService.check("spring");
        boolean result2 = checkUsernameService.check(user.getUsername());

        // then
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }
}
