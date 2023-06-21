package com.sloth.domain.invite.service;

import com.sloth.domain.invite.domain.Invite;
import com.sloth.domain.invite.dto.InviteDto;
import com.sloth.domain.member.dto.MemberDto;
import com.sloth.domain.member.service.MemberService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteManageService {

    private final JwtService jwtService;
    private final UserService userService;
    private final InviteService inviteService;
    private final MemberService memberService;

    public List<InviteDto> getMyInvites(HttpServletRequest request) throws Exception {
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        List<Invite> invites = inviteService.getInviteByUser(user);
        return invites.stream()
                .map(invite -> InviteDto.builder()
                        .inviteId(invite.getId())
                        .teamId(invite.getTeam().getId())
                        .teamName(invite.getTeam().getName())
                        .leaderUsername(invite.getTeam().getLeader().getUsername())
                        .leaderName(invite.getTeam().getLeader().getName())
                        .inviteDate(new Date())
                        .build())
                .toList();
    }

    @Transactional
    public void acceptInvite(HttpServletRequest request, Long inviteId) throws Exception {
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        Invite invite = inviteService.getInviteById(inviteId);
        if (invite.getUser() == user) { // 본인 검증
            inviteService.remove(invite);
            memberService.create(MemberDto.builder()
                    .team(invite.getTeam())
                    .user(invite.getUser())
                    .build());
            return;
        }
        throw new Exception("본인 외 초대 수락 불가능");
    }

    public void declineInvite(HttpServletRequest request, Long inviteId) throws Exception {
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        Invite invite = inviteService.getInviteById(inviteId);
        if (invite.getUser() == user) { // 본인 검증
            inviteService.remove(invite);
            return;
        }
        throw new Exception("본인 외 초대 취소 불가능");
    }
}
