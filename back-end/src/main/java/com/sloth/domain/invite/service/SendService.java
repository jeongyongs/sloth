package com.sloth.domain.invite.service;

import com.sloth.domain.invite.domain.Invite;
import com.sloth.domain.invite.dto.SendDto;
import com.sloth.domain.invite.repository.InviteRepository;
import com.sloth.domain.member.service.MemberService;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.service.TeamQueryService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SendService {

    private final InviteRepository inviteRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final TeamQueryService teamQueryService;
    private final InviteService inviteService;
    private final MemberService memberService;

    @Transactional
    public void sendInvite(HttpServletRequest request, SendDto data) throws Exception {
        String username = jwtService.getUsername(request);
        User currentUser = userService.getUserByUsername(username);
        Team team = teamQueryService.getTeamById(data.getTeamId());

        if (team.getLeader() == currentUser) {  // 리더 검증
            User user = userService.getUserByUsername(data.getUsername());
            Invite invite = Invite.builder()
                    .team(team)
                    .user(user)
                    .sendDate(new Date())
                    .build();
            inviteRepository.save(invite);
            return;
        }
        throw new Exception("리더 외 초대 불가능");
    }

    @Transactional
    public void cancelInvite(HttpServletRequest request, Long teamId, String targetUsername) throws Exception {
        String username = jwtService.getUsername(request);
        User currentUser = userService.getUserByUsername(username);
        Team team = teamQueryService.getTeamById(teamId);

        if (team.getLeader() == currentUser) {  // 리더 검증
            User target = userService.getUserByUsername(targetUsername);
            Invite invite = inviteService.getInviteByTeamAndUser(team, target);
            inviteService.remove(invite);
            return;
        }
        throw new Exception("리더 외 초대 취소 불가능");
    }
}
