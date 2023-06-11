package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.dto.InviteInfo;
import com.sloth.team.repository.InviteTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInviteService {

    private final InviteTeamRepository inviteTeamRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    /* 팀 초대 조회 */
    public List<InviteInfo> getByMember(String jwt) {

        String username = jwtService.getUsername(jwt);
        User user = userRepository.findByUsername(username);
        List<InviteTeam> inviteTeams = inviteTeamRepository.findByMember(user);

        // 조회 성공
        if (inviteTeams != null) {
            List<InviteInfo> inviteInfos = new ArrayList<>();

            // InviteTeam -> InviteInfo 매핑
            for (InviteTeam inviteTeam : inviteTeams) {

                // 만료기한이 지난 초대 삭제
                if (new Date(inviteTeam.getExpired()).before(new Date())) {
                    inviteTeamRepository.remove(inviteTeam);
                    continue;
                }
                inviteInfos.add(InviteInfo.builder()
                        .id(inviteTeam.getId())
                        .teamName(inviteTeam.getTeam().getName())
                        .teamOwner(inviteTeam.getTeam().getOwner().getName())
                        .expired(inviteTeam.getExpired())
                        .build()
                );
            }
            return inviteInfos;
        }
        // 조회 실패
        return null;
    }
}
