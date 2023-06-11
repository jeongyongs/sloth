package com.sloth.member.repository;

import com.sloth.domain.user.repository.UserRepository;
import com.sloth.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MysqlUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("멤버 레포지토리 CRUD 테스트")
    @Transactional
    void crudTest() {

        // given
        User user = User.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .name("Jeongyong Lee")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        // when
        userRepository.save(user);
        User foundUser1 = userRepository.findById(user.getId());
        User foundUser2 = userRepository.findByUsername("sloth");
        List<User> foundMembers1 = userRepository.findAll();

        userRepository.remove(user);
        User foundUser3 = userRepository.findById(user.getId());
        User foundUser4 = userRepository.findByUsername("sloth");
        List<User> foundMembers2 = userRepository.findAll();

        // then
        Assertions.assertEquals(user, foundUser1);
        Assertions.assertEquals(user, foundUser2);
        Assertions.assertEquals(user, foundMembers1.get(0));

        Assertions.assertNull(foundUser3);
        Assertions.assertNull(foundUser4);
        Assertions.assertTrue(foundMembers2.isEmpty());
    }
}
