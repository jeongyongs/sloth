package com.sloth.global.auth.service;

import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.dto.CredentialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;

    public Authentication authenticate(String token) throws Exception {  // 인증
        if (jwtService.isAvailable(token)) {
            String username = jwtService.getUsername(token);
            CredentialDto credential = userService.getCredential(username);
            return new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword(), null);
        }
        throw new Exception("유효하지 않은 토큰");
    }
}
