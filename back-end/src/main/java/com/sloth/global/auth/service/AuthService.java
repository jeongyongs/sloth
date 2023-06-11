package com.sloth.global.auth.service;

import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.dto.CredentialDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Authentication authenticate(HttpServletRequest request) throws Exception {  // 인증
        if (jwtService.isAvailable(request)) {
            String username = jwtService.getUsername(request);
            CredentialDto credential = userService.getCredential(username);
            return new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword(), null);
        }
        throw new Exception("유효하지 않은 토큰");
    }

    public String generate(CredentialDto data) throws Exception {   // 토큰 발급
        CredentialDto credential = userService.getCredential(data.getUsername());
        if (bCryptPasswordEncoder.matches(data.getPassword(), credential.getPassword())) {
            return jwtService.generate(credential.getUsername());
        }
        throw new Exception("유저 정보 불일치");
    }
}
