package com.sloth.member.service;

import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckUsernameService {

    private final MemberRepository memberRepository;

    public CheckUsernameService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 아이디 중복 검사 */
    public boolean check(String username) {

        Member foundMember = memberRepository.findByUsername(username);

        // true: 사용 가능
        // false: 사용 불가능
        return foundMember == null;
    }
}
