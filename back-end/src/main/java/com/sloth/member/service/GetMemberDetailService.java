package com.sloth.member.service;

import com.sloth.member.domain.Member;
import com.sloth.member.dto.MemberDetailDto;
import com.sloth.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class GetMemberDetailService {

    private final MemberRepository memberRepository;

    public GetMemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 정보 조회 */
    public MemberDetailDto getDetail(String username) {

        Member foundMember = memberRepository.findByUsername(username);

        // 조회 성공
        if (foundMember != null) {

            return MemberDetailDto.builder()
                    .username(foundMember.getUsername())
                    .name(foundMember.getName())
                    .email(foundMember.getEmail())
                    .phone(foundMember.getPhone())
                    .build();
        }
        // 조회 실패
        return null;
    }
}
