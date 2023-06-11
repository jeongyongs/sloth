package com.sloth.global.auth.controller;

import com.sloth.global.auth.dto.CredentialDto;
import com.sloth.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody CredentialDto data) throws Exception { // 로그인 API
        return authService.generate(data);
    }
}
