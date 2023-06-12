package com.sloth.domain.user.controller;

import com.sloth.domain.user.dto.SignupDto;
import com.sloth.domain.user.service.SignupService;
import com.sloth.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final SignupService signupService;
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupDto data) throws Exception {  // 회원가입 API
        signupService.signup(data);
    }

    @GetMapping("/users/me")
    public String getName(HttpServletRequest request) throws Exception {    // 유저 이름 조회 API
        return userService.getName(request);
    }
}
