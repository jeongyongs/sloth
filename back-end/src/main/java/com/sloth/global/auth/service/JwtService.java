package com.sloth.global.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getToken(HttpServletRequest request) throws Exception {    // 토큰 추출
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization.startsWith("bearer ")) {
            return authorization.substring(7);
        }
        throw new Exception("토큰이 존재하지 않음");
    }

    public String generate(String username) { // 토큰 생성
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("sloth.com")
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60L)))
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {   // 아이디 추출
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isAvailable(String token) {  // 토큰 유효성 검사
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {  // 유효기간 추출
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
