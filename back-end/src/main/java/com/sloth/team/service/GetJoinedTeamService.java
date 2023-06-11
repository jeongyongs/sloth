package com.sloth.team.service;

import com.sloth.global.auth.service.JwtService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.TeamMember;
import com.sloth.team.dto.TeamInfo;
import com.sloth.team.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetJoinedTeamService {

    private final TeamMemberRepository teamMemberRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public List<TeamInfo> getList(String jwt) {

        String username = jwtService.getUsername(jwt);
        User user = userRepository.findByUsername(username);
        List<TeamMember> teams = teamMemberRepository.findByMember(user);

        // 만약 가입한 팀이 없다면
        if (teams.isEmpty()) {
            return null;
        }

        // TeamMember to TeamInfo 매핑
        List<TeamInfo> teamInfos = new ArrayList<>();
        for (TeamMember teamMember : teams) {
            teamInfos.add(TeamInfo.builder().name(teamMember.getTeam().getName()).build());
        }
        return teamInfos;
    }
}
