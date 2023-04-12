package com.sloth.member.controller;

import com.sloth.member.dto.LoginDto;
import com.sloth.member.dto.MemberDetailDto;
import com.sloth.member.dto.NewMemberDto;
import com.sloth.member.dto.ResponseDto;
import com.sloth.member.service.CheckUsernameService;
import com.sloth.member.service.GetMemberDetailService;
import com.sloth.member.service.LoginService;
import com.sloth.member.service.NewMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController              // RestController 선언
@RequestMapping("/api")  // URI 매핑
public class MemberController {

    private final CheckUsernameService checkUsernameService;
    private final NewMemberService newMemberService;
    private final GetMemberDetailService getMemberDetailService;
    private final LoginService loginService;

    public MemberController(CheckUsernameService checkUsernameService, NewMemberService newMemberService, GetMemberDetailService getMemberDetailService, LoginService loginService) {
        this.checkUsernameService = checkUsernameService;
        this.newMemberService = newMemberService;
        this.getMemberDetailService = getMemberDetailService;
        this.loginService = loginService;
    }

    @PostMapping("/check/username")
    /* 아이디 중복 확인 */
    public ResponseEntity<?> checkUsername(@RequestParam String username) {

        // 아이디 사용 가능
        if (checkUsernameService.check(username)) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .success(true)
                            .message("username: " + username + " is available.")
                            .build()
                    );
        }
        // 아이디 사용 불가능
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseDto.builder()
                        .success(false)
                        .message("username: " + username + " is not available.")
                        .build()
                );
    }

    @PostMapping("/members")
    /* 회원가입 요청 */
    public ResponseEntity<?> newMember(NewMemberDto newMemberDto) {

        // 회원 가입 성공
        if (newMemberService.newMember(newMemberDto)) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .success(true)
                            .message("Success to sign up.")
                            .build()
                    );
        }
        // 회원 가입 실패
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseDto.builder()
                        .success(false)
                        .message("Fail to sign up.")
                        .build()
                );
    }

    @GetMapping("/members/{id}")
    /* 회원 정보 조회 */
    public ResponseEntity<?> getMemberDetail(@PathVariable Long id) {

        MemberDetailDto result = getMemberDetailService.getDetail(id);

        // 회원 정보 조회 성공
        if (result != null) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .success(true)
                            .message("Member information has been retrieved.")
                            .data(result)
                            .build()
                    );
        }
        // 회원 정보 조회 실패
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseDto.builder()
                        .success(false)
                        .message("There is no member.")
                        .build()
                );
    }

    @PostMapping("/login")
    /* 로그인 요청 */
    public ResponseEntity<?> login(LoginDto loginDto) {

        String result = loginService.login(loginDto);

        // 로그인 성공
        if (result != null) {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .success(true)
                            .message("Login success.")
                            .data(result)
                            .build()
                    );
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
