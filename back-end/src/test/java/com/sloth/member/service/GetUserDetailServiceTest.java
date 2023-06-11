package com.sloth.member.service;

import com.sloth.domain.user.domain.User;
import com.sloth.member.dto.MemberDetailDto;
import com.sloth.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GetUserDetailServiceTest {

    @Autowired
    GetMemberDetailService getMemberDetailService;
    @Autowired
    NewMemberService newMemberService;
    @Autowired
    UserRepository userRepository;

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
        User foundUser = userRepository.findByUsername("sloth");
        MemberDetailDto memberDetailDto = getMemberDetailService.getDetail(foundUser.getUsername());

        // then
        Assertions.assertEquals(member.getUsername(), memberDetailDto.getUsername());
        Assertions.assertEquals(member.getName(), memberDetailDto.getName());
        Assertions.assertEquals(member.getEmail(), memberDetailDto.getEmail());
        Assertions.assertEquals(member.getPhone(), memberDetailDto.getPhone());
    }
}
