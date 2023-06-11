package com.sloth.team.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.global.auth.service.JwtService;
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

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JwtService jwtService;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public boolean create(String jwt, String name) {

        // JWT 아이디 추출
        String username = jwtService.getUsername(jwt);

        User user = userRepository.findByUsername(username);
        Team team = Team.builder().name(name).owner(user).build();
        TeamMember teamMember = TeamMember.builder().user(user).team(team).build();

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
