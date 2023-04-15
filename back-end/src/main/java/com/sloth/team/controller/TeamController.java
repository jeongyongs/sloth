package com.sloth.team.controller;

import com.sloth.member.dto.ResponseDto;
import com.sloth.team.dto.InviteInfo;
import com.sloth.team.dto.TeamInfo;
import com.sloth.team.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.ExportableColumn;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final CreateTeamService createTeamService;
    private final GetJoinedTeamService getJoinedTeamService;
    private final InviteTeamService inviteTeamService;
    private final CheckInviteService checkInviteService;
    private final AcceptInviteService acceptInviteService;

    @PostMapping
    /* 새 팀 만들기 */
    public ResponseDto createTeam(HttpServletRequest request, @RequestParam String name) {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        if (!createTeamService.create(jwt, name)) {
            // 팀 생성 실패
            return ResponseDto.builder()
                    .success(false)
                    .message("name: " + name + " creation failed.")
                    .build();
        }
        // 팀 생성 성공
        return ResponseDto.builder()
                .success(true)
                .message("name: " + name + " has been created.")
                .build();
    }

    @GetMapping
    /* 가입한 팀 목록 조회 */
    public ResponseDto getJoinedTeam(HttpServletRequest request) {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        List<TeamInfo> teams = getJoinedTeamService.getList(jwt);

        // 조회 성공
        if (teams != null) {
            return ResponseDto.builder()
                    .success(true)
                    .message("success")
                    .data(teams)
                    .build();
        }
        // 조회 실패
        return ResponseDto.builder()
                .success(false)
                .message("No joined team")
                .build();
    }

    @PostMapping("/invite")
    /* 팀 초대 */
    public ResponseDto inviteTeam(@RequestParam String name, @RequestParam String username) {

        boolean result = inviteTeamService.invite(name, username);
        // 초대 성공
        if (result) {
            return ResponseDto.builder()
                    .success(true)
                    .message("Invite complete.")
                    .build();
        }
        // 초대 실패
        return ResponseDto.builder()
                .success(false)
                .message("Invite failed.")
                .build();
    }

    @GetMapping("/invite")
    /* 팀 초대 확인 */
    public ResponseDto checkAllInviteByMember(HttpServletRequest request) {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        List<InviteInfo> inviteInfos = checkInviteService.getByMember(jwt);

        if (inviteInfos != null) {
            // 초대 확인 성공
            return ResponseDto.builder()
                    .success(true)
                    .message("Check invite list success.")
                    .data(inviteInfos)
                    .build();
        }
        // 초대 확인 실패
        return ResponseDto.builder()
                .success(false)
                .message("Check invite list failed.")
                .build();
    }

    @PostMapping("/accept")
    /* 팀 초대 수락 */
    public ResponseDto acceptInvite(HttpServletRequest request, @RequestParam Long inviteId) {

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        boolean result = acceptInviteService.accept(jwt, inviteId);

        // 수락 성공
        if (result) {
            return ResponseDto.builder().success(true).message("Accept invite success.").build();
        }
        // 수락 실패
        return ResponseDto.builder().success(false).message("Accept invite failed.").build();
    }
}
