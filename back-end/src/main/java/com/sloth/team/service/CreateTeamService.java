package com.sloth.team.service;

import com.sloth.authentication.service.JwtService;
import com.sloth.member.domain.Member;
import com.sloth.member.repository.MemberRepository;
import com.sloth.team.domain.Team;
import com.sloth.team.domain.TeamMember;
import com.sloth.team.repository.TeamMemberRepository;
import com.sloth.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTeamService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final JwtService jwtService;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public boolean create(String jwt, String name) {

        // JWT 아이디 추출
        String username = jwtService.getClaims(jwt).getSubject();

        Member member = memberRepository.findByUsername(username);
        Team team = Team.builder().name(name).owner(member).build();
        TeamMember teamMember = TeamMember.builder().member(member).team(team).build();

        if (teamRepository.findByName(name) != null) {
            return false;
        }

        try {
            teamRepository.save(team);
            teamMemberRepository.save(teamMember);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
