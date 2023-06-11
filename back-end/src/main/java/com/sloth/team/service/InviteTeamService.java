package com.sloth.team.service;

import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;
import com.sloth.team.repository.InviteTeamRepository;
import com.sloth.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class InviteTeamService {

    private final InviteTeamRepository inviteTeamRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    /* 팀 초대 */
    @Transactional
    public boolean invite(String name, String username) {

        // 초대 만료 시간 24시간
        Long expired = new Date(System.currentTimeMillis()).getTime() + 1000 * 60 * 60 * 24;
        Team team = teamRepository.findByName(name);
        User user = userRepository.findByUsername(username);

        if (team == null) {
            return false;
        }

        if (user == null) {
            return false;
        }

        InviteTeam inviteTeam = InviteTeam.builder().team(team).user(user).expired(expired).build();

        try {
            inviteTeamRepository.save(inviteTeam);
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}
