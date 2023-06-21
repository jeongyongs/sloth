package com.sloth.domain.transferee.service;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.service.TeamQueryService;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.transferee.dto.TransfereeViewDto;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransfereeViewService {

    private final TransfereeService transfereeService;
    private final JwtService jwtService;
    private final UserService userService;
    private final TeamQueryService teamQueryService;

    public List<TransfereeViewDto> findByUser(HttpServletRequest request, Long teamId) throws Exception {
        String username = jwtService.getUsername(request);
        User user = userService.getUserByUsername(username);
        Team team = teamQueryService.getTeamById(teamId);
        List<Transferee> transferees = transfereeService.findByUserAndTeam(team, user);
        return transferees.stream()
                .map(transferee -> TransfereeViewDto.builder()
                        .transferDate(transferee.getTransferDate())
                        .title(transferee.getHandover().getTitle())
                        .transferor(transferee.getHandover().getTransferor().getName())
                        .transferee(transferee.getUser().getName())
                        .state(transferee.isState())
                        .build())
                .toList();
    }
}
