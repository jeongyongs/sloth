package com.sloth.domain.member.controller;

import com.sloth.domain.member.dto.MembersDto;
import com.sloth.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/teams/{teamId}/members")
    public MembersDto getMembers(HttpServletRequest request, @PathVariable Long teamId) throws Exception {
        return memberService.getMembers(request, teamId);
    }

    @DeleteMapping("/teams/{teamId}/members/{userId}")
    public void kick(HttpServletRequest request, @PathVariable Long teamId, @PathVariable Long userId) throws Exception {
        memberService.kick(request, teamId, userId);
    }
}
