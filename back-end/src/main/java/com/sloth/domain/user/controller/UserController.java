package com.sloth.domain.user.controller;

import com.sloth.domain.user.dto.SignupDto;
import com.sloth.domain.user.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final SignupService signupService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupDto data) throws Exception {  // 회원가입 API
        signupService.signup(data);
    }
}

//(@RequestParam String username)
//(HttpServletRequest request)
