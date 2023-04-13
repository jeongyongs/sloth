package com.sloth.authentication.controller;

import com.sloth.authentication.dto.LoginDto;
import com.sloth.authentication.service.LoginService;
import com.sloth.member.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authenticationController {

    private final LoginService loginService;

    @PostMapping
    /* 로그인 요청 */
    public ResponseEntity<?> login(LoginDto loginDto) {

        String result = loginService.login(loginDto);

        // 로그인 성공
        if (result != null) {

            return ResponseEntity.ok().body(new HashMap<String, String>() {{
                put("token", result);
            }});
        }
        // 로그인 실패
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseDto.builder()
                        .success(false)
                        .message("Login fail.")
                        .build()
                );
    }
}
