package com.sloth.member.service;

import com.sloth.member.domain.Member;
import com.sloth.member.dto.MemberDetailDto;
import com.sloth.member.dto.NewMemberDto;
import com.sloth.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GetMemberDetailServiceTest {

    @Autowired
    GetMemberDetailService getMemberDetailService;
    @Autowired
    NewMemberService newMemberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 정보 조회 테스트")
    @Transactional
    void GetMemberDetailTest() {

        // given
        NewMemberDto member = NewMemberDto.builder()
                .username("sloth")
                .password("q1w2e3r4")
                .name("Jeongyong Lee")
                .email("jeongyongs@sloth.com")
                .phone("000-0000-0000")
                .build();

        // when
        newMemberService.newMember(member);
        Member foundMember = memberRepository.findByUsername("sloth");
        MemberDetailDto memberDetailDto = getMemberDetailService.getDetail(foundMember.getUsername());

        // then
        Assertions.assertEquals(member.getUsername(), memberDetailDto.getUsername());
        Assertions.assertEquals(member.getName(), memberDetailDto.getName());
        Assertions.assertEquals(member.getEmail(), memberDetailDto.getEmail());
        Assertions.assertEquals(member.getPhone(), memberDetailDto.getPhone());
    }
}
