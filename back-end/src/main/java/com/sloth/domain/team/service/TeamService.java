package com.sloth.domain.team.service;

import com.sloth.domain.member.domain.Member;
import com.sloth.domain.member.dto.MemberDto;
import com.sloth.domain.member.service.MemberService;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.dto.NewTeamDto;
import com.sloth.domain.team.dto.TeamDto;
import com.sloth.domain.team.dto.TeamInfoDto;
import com.sloth.domain.team.repository.TeamRepository;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final MemberService memberService;

    @Transactional
    public void create(HttpServletRequest request, NewTeamDto data) throws Exception {  // 팀 생성
        if (data.getName().length() > 0) {    // 아이디 확인
            String username = jwtService.getUsername(request);
            User user = userService.getUserByUsername(username);
            Team team = Team.builder().name(data.getName()).leader(user).createDate(new Date()).build();
            MemberDto dto = MemberDto.builder().team(team).user(user).build();

            teamRepository.save(team);
            memberService.create(dto);
            return;
        }
        throw new Exception("사용할 수 없는 팀 이름");
    }

    public List<TeamDto> getTeams(HttpServletRequest request) throws Exception {    // 팀 리스트 조회
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        List<Member> list = memberService.findAllByUser(user);
        return list.stream()
                .map(member -> new TeamDto(member.getTeam().getId(), member.getTeam().getName()))
                .toList();
    }

    public TeamInfoDto getTeamInfo(HttpServletRequest request, Long id) throws Exception {  // 팀 정보 조회
        Team team = teamRepository.findById(id);
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        memberService.Validate(team, user);  // 팀 소속 여부 검증
        return TeamInfoDto.builder().teamName(team.getName()).leaderName(team.getLeader().getName()).createDate(team.getCreateDate()).build();
    }
}
