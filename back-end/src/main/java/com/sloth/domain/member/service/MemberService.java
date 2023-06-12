package com.sloth.domain.member.service;

import com.sloth.domain.member.domain.Member;
import com.sloth.domain.member.dto.MemberDto;
import com.sloth.domain.member.dto.MemberInfoDto;
import com.sloth.domain.member.dto.MembersDto;
import com.sloth.domain.member.repository.MemberRepository;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.service.TeamQueryService;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final TeamQueryService teamQueryService;
    private final UserService userService;

    @Transactional
    public void create(MemberDto dto) {   // 팀유저 객체 생성
        Member member = Member.builder()
                .team(dto.getTeam())
                .user(dto.getUser())
                .joinDate(new Date())
                .build();
        memberRepository.save(member);
    }

    public List<Member> findAllByUser(User user) {    // 유저로 팀유저 객체 조회
        return memberRepository.findAllByUser(user);
    }

    public void Validate(Team team, User user) {   // 팀에 소속한 멤버인지 검증
        memberRepository.findByTeamAndUser(team, user);
    }

    public MembersDto getMembers(HttpServletRequest request, Long id) throws Exception {
        Team team = teamQueryService.getTeamById(id);
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);

        Validate(team, user);  // 팀 소속 여부 검증

        List<Member> members = memberRepository.findAllByTeam(team);    // 팀원 리스트
        List<MemberInfoDto> list = members.stream() // 리스트 매핑
                .map(member -> {
                    String role = "";
                    if (member.getUser() == team.getLeader()) {
                        role = "leader";
                    }
                    return MemberInfoDto.builder()
                            .id(member.getUser().getId())
                            .username(member.getUser().getUsername())
                            .name(member.getUser().getName())
                            .role(role)
                            .joinDate(member.getJoinDate())
                            .build();
                })
                .toList();
        boolean isLeader = team.getLeader() == user;

        return MembersDto.builder()
                .isLeader(isLeader)
                .members(list)
                .build();
    }

    @Transactional
    public void kick(HttpServletRequest request, Long teamId, Long userId) throws Exception {
        Team team = teamQueryService.getTeamById(teamId);
        String username = jwtService.getUsername(request);
        User leader = userService.getUserByUsername(username);
        User user = userService.getUserById(userId);

        if (team.getLeader() == user) { // 리더 추방 불가능
            throw new Exception("리더 추방 불가능");
        }
        if (team.getLeader() == leader) {   // 팀 리더 검증
            Member member = memberRepository.findByTeamAndUser(team, user);
            memberRepository.remove(member);
            return;
        }
        throw new Exception("추방 권한 없음");
    }
}
