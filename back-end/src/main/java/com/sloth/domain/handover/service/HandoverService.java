package com.sloth.domain.handover.service;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.handover.repository.HandoverRepository;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandoverService {

    private final HandoverRepository handoverRepository;

    @Transactional
    public void save(Handover handover) {
        handoverRepository.save(handover);
    }

    public Handover findById(Long id) {
        return handoverRepository.findById(id);
    }

    public void remove(Handover handover) {
        handoverRepository.remove(handover);
    }

    public List<Handover> findByTeamAndUser(Team team, User user) {
        return handoverRepository.findByTeamAndUser(team, user);
    }
}
