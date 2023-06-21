package com.sloth.domain.invite.controller;

import com.sloth.domain.invite.dto.InviteDto;
import com.sloth.domain.invite.dto.SendDto;
import com.sloth.domain.invite.service.InviteManageService;
import com.sloth.domain.invite.service.SendService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InviteController {

    private final SendService sendService;
    private final InviteManageService inviteManageService;

    @PostMapping("/invite")
    public void send(HttpServletRequest request, @RequestBody SendDto data) throws Exception {
        sendService.sendInvite(request, data);
    }

    @DeleteMapping("/invite")
    public void cancel(HttpServletRequest request, @RequestParam Long teamId, @RequestParam String username) throws Exception {
        sendService.cancelInvite(request, teamId, username);
    }

    @GetMapping("/invites")
    public List<InviteDto> getInvites(HttpServletRequest request) throws Exception {
        return inviteManageService.getMyInvites(request);
    }

    @PostMapping("/invites/{inviteId}")
    public void acceptInvite(HttpServletRequest request, @PathVariable Long inviteId) throws Exception {
        inviteManageService.acceptInvite(request, inviteId);
    }

    @DeleteMapping("/invites/{inviteId}")
    public void declineInvite(HttpServletRequest request, @PathVariable Long inviteId) throws Exception {
        inviteManageService.declineInvite(request, inviteId);
    }
}
