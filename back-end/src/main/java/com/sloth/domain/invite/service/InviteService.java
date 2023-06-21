package com.sloth.domain.invite.service;

import com.sloth.domain.invite.domain.Invite;
import com.sloth.domain.invite.repository.InviteRepository;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;

    public List<Invite> getInviteByTeam(Team team) {
        return inviteRepository.findAllByTeam(team);
    }

    public List<Invite> getInviteByUser(User user) {
        return inviteRepository.findAllByUser(user);
    }

    public Invite getInviteByTeamAndUser(Team team, User user) {
        return inviteRepository.findByTeamAndUser(team, user);
    }

    public Invite getInviteById(Long id) {
        return inviteRepository.findById(id);
    }

    @Transactional
    public void remove(Invite invite) {
        inviteRepository.remove(invite);
    }
}
