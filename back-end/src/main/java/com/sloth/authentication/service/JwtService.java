package com.sloth.authentication.service;

import com.sloth.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final MemberRepository memberRepository;

    /* JWT 생성 */
    public String createJwt(String username) {

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("sloth.com")
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60L)))
                .signWith(key)
                .compact();
    }

    /* JWT 유효성 검사 */
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    /* 아이디 확인 */
    public Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
