package com.sloth.domain.handover.repository;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;

import java.util.List;

public interface HandoverRepository {

    void save(Handover handover);

    Handover findById(Long id);

    void remove(Handover handover);

    List<Handover> findByTeamAndUser(Team team, User user);
}
