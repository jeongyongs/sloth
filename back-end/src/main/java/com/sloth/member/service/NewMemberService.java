package com.sloth.member.service;

import com.sloth.member.domain.Member;
import com.sloth.member.dto.NewMemberDto;
import com.sloth.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewMemberService {

    private final MemberRepository memberRepository;
    private final CheckUsernameService checkUsernameService;
    private final BCryptPasswordEncoder encoder;

    public NewMemberService(MemberRepository memberRepository, CheckUsernameService checkUsernameService, BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.checkUsernameService = checkUsernameService;
        this.encoder = encoder;
    }

    @Transactional
    /* 회원 가입 */
    public boolean newMember(NewMemberDto newMemberDto) {

        // 아이디 중복 확인
        if (checkUsernameService.check(newMemberDto.getUsername())) {

            Member member = Member.builder()
                    .username(newMemberDto.getUsername())
                    .password(encoder.encode(newMemberDto.getPassword()))
                    .name(newMemberDto.getName())
                    .email(newMemberDto.getEmail())
                    .phone(newMemberDto.getPhone())
                    .build();

            try {
                memberRepository.save(member);
                return true; // 레포지토리 저장 성공

            } catch (Exception e) {

                return false; // 레포지토리 저장 실패
            }
        }
        return false; // 아이디 사용 불가능
    }
}
