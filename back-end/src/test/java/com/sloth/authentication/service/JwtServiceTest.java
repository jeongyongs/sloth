package com.sloth.authentication.service;

import com.sloth.global.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    @DisplayName("JWT 테스트")
    void JwtTest() {

        // given
        String username = "sloth";

        // when
        String token = jwtService.create(username);
        boolean result = jwtService.validate(token);
        Claims claims = jwtService.getUsername(token);

        // then
        Assertions.assertTrue(result);
        Assertions.assertEquals("sloth.com", claims.getIssuer());
        Assertions.assertEquals(username, claims.getSubject());
    }
}