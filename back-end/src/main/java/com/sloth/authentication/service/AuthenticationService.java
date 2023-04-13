package com.sloth.authentication.service;

import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    /* 사용자 인증 */
    public Authentication authenticate(String token) {

        // 토큰 유효성 검사
        if (!jwtService.validateToken(token)) {
            return null;
        }
        // 토큰 페이로드 저장
        Claims claims = jwtService.getClaims(token);
        // 사용자 존재 여부 확인
        Member member = memberRepository.findByUsername(claims.getSubject());
        if (member == null) {
            return null;
        }
        // 유효기간 확인
        if (claims.getExpiration().before(new Date())) {
            return null;
        }
        // 성공
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), member.getPassword(), null);
    }
}
