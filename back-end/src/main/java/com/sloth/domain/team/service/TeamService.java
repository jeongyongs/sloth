package com.sloth.domain.team.service;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.domain.TeamUser;
import com.sloth.domain.team.dto.NewTeamDto;
import com.sloth.domain.team.dto.TeamDto;
import com.sloth.domain.team.repository.TeamRepository;
import com.sloth.domain.team.repository.TeamUserRepository;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Transactional
    public void create(HttpServletRequest request, NewTeamDto data) throws Exception {  // 팀 생성
        if (data.getName().length() > 0) {    // 아이디 확인
            String username = jwtService.getUsername(request);
            User user = userService.getUserByUsername(username);
            Team team = Team.builder().name(data.getName()).leader(user).build();
            TeamUser teamUser = TeamUser.builder().team(team).user(user).build();

            teamRepository.save(team);
            teamUserRepository.save(teamUser);
            return;
        }
        throw new Exception("사용할 수 없는 팀 이름");
    }

    public List<TeamDto> getTeams(HttpServletRequest request) throws Exception {    // 팀 리스트 조회
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        List<TeamUser> list = teamUserRepository.findAllByUser(user);
        return list.stream()
                .map(teamUser -> new TeamDto(teamUser.getTeam().getId(), teamUser.getTeam().getName()))
                .toList();
    }
}
