package com.sloth.global.auth.service;

import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.dto.CredentialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public String login(CredentialDto data) {   // 로그인
        CredentialDto credential = userService.getCredential(data.getUsername());
        if (bCryptPasswordEncoder.matches(data.getPassword(), credential.getPassword())) {
            return jwtService.create(credential.getUsername());
        }
        return null;
    }
}
