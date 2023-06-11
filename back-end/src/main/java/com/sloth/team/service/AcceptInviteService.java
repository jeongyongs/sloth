package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.TeamMember;
import com.sloth.team.repository.InviteTeamRepository;
import com.sloth.team.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AcceptInviteService {

    private final InviteTeamRepository inviteTeamRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    /* 팀 초대 수락 */
    public boolean accept(String jwt, Long inviteId) {

        String username = jwtService.getUsername(jwt);
        User user = userRepository.findByUsername(username);
        InviteTeam inviteTeam = inviteTeamRepository.findById(inviteId);

        if (user == inviteTeam.getUser()) {
            try {
                inviteTeamRepository.remove(inviteTeam);
                teamMemberRepository.save(TeamMember.builder().user(inviteTeam.getUser()).team(inviteTeam.getTeam()).build());
                // 수락 성공
                return true;

            } catch (Exception e) {
                // 트랜젝션 중 문제
                return false;
            }
        }
        // 팀 초대 멤버와 인증 멤버가 서로 다름
        return false;
    }
}
