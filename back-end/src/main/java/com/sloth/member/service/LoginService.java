package com.sloth.member.service;

import com.sloth.member.domain.Member;
import com.sloth.member.dto.LoginDto;
import com.sloth.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public LoginService(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
    }

    private String createJwt(String username) {

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("sloth.com")
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60L)))
                .signWith(key)
                .compact();
    }

    public String login(LoginDto loginDto) {

        Member foundMember = memberRepository.findByUsername(loginDto.getUsername());

        // 로그인 정보 일치
        if (foundMember != null && bCryptPasswordEncoder.matches(loginDto.getPassword(), foundMember.getPassword())) {

            return createJwt(loginDto.getUsername());
        }
        // 로그인 정보 불일치
        return null;
    }
}
