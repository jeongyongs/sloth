package com.sloth.domain.transferee.service;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.transferee.repository.TransfereeRepository;
import com.sloth.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransfereeService {

    private final TransfereeRepository transfereeRepository;

    public List<Transferee> findByUser(User user) {
        return transfereeRepository.findAllByUser(user);
    }

    public List<Transferee> findByUserAndTeam(Team team, User user) {
        return transfereeRepository.findAllByTeamAndUser(team, user);
    }
}
