package com.sloth.team.service;

import com.sloth.authentication.service.JwtService;
import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public List<TeamInfo> getList(String jwt) {

        String username = jwtService.getClaims(jwt).getSubject();
        Member member = memberRepository.findByUsername(username);
        List<TeamMember> teams = teamMemberRepository.findByMember(member);

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
