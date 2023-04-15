package com.sloth.member.repository;

import com.sloth.member.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MysqlMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 레포지토리 CRUD 테스트")
    @Transactional
    void crudTest() {

        // given
        Member member = Member.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .name("Jeongyong Lee")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        // when
        memberRepository.save(member);
        Member foundMember1 = memberRepository.findById(member.getId());
        Member foundMember2 = memberRepository.findByUsername("sloth");
        List<Member> foundMembers1 = memberRepository.findAll();

        memberRepository.remove(member);
        Member foundMember3 = memberRepository.findById(member.getId());
        Member foundMember4 = memberRepository.findByUsername("sloth");
        List<Member> foundMembers2 = memberRepository.findAll();

        // then
        Assertions.assertEquals(member, foundMember1);
        Assertions.assertEquals(member, foundMember2);
        Assertions.assertEquals(member, foundMembers1.get(0));

        Assertions.assertNull(foundMember3);
        Assertions.assertNull(foundMember4);
        Assertions.assertTrue(foundMembers2.isEmpty());
    }
}
