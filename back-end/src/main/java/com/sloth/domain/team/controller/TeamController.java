package com.sloth.domain.team.controller;

import com.sloth.domain.team.dto.NewTeamDto;
import com.sloth.domain.team.dto.TeamDto;
import com.sloth.domain.team.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/teams")
    public void createTeam(HttpServletRequest request, @RequestBody NewTeamDto data) throws Exception { // 팀 생성 API
        teamService.create(request, data);
    }

    @GetMapping("/teams")
    public List<TeamDto> getTeams(HttpServletRequest request) throws Exception { // 팀 리스트 조회 API
        return teamService.getTeams(request);
    }
}
