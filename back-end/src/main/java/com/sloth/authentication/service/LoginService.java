package com.sloth.authentication.service;

import com.sloth.member.domain.Member;
import com.sloth.authentication.dto.LoginDto;
import com.sloth.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public LoginService(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository, JwtService jwtService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    public String login(LoginDto loginDto) {

        Member foundMember = memberRepository.findByUsername(loginDto.getUsername());

        // 로그인 정보 일치
        if (foundMember != null && bCryptPasswordEncoder.matches(loginDto.getPassword(), foundMember.getPassword())) {

            return jwtService.createJwt(loginDto.getUsername());
        }
        // 로그인 정보 불일치
        return null;
    }
}
