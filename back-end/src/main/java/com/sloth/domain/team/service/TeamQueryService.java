package com.sloth.domain.team.service;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamQueryService {

    private final TeamRepository teamRepository;

    public Team getTeamById(Long id) {  // 아이디로 팀 객체 조회
        return teamRepository.findById(id);
    }
}
