package com.sloth.domain.user.service;

import com.sloth.domain.invite.domain.Invite;
import com.sloth.domain.invite.service.InviteService;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.service.TeamQueryService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.dto.UserForInviteDto;
import com.sloth.domain.user.repository.UserRepository;
import com.sloth.global.auth.dto.CredentialDto;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TeamQueryService teamQueryService;
    private final InviteService inviteService;

    public CredentialDto getCredential(String username) {   // 자격 증명 조회
        User user = userRepository.findByUsername(username);
        return new CredentialDto(user.getUsername(), user.getPassword());
    }

    public User getUserByUsername(String username) {    // 유저명으로 객체 조회
        return userRepository.findByUsername(username);
    }

    public String getName(HttpServletRequest request) throws Exception {    // 이름 조회
        String username = jwtService.getUsername(request);
        User user = getUserByUsername(username);
        return user.getName();
    }

    public User getUserById(Long id) {  // 아이디로 객체 조회
        return userRepository.findById(id);
    }

    public List<UserForInviteDto> getUsersBySearch(HttpServletRequest request, String search, Long teamId) throws Exception {
        String username = jwtService.getUsername(request);
        User currentUser = getUserByUsername(username);
        Team team = teamQueryService.getTeamById(teamId);

        if (team.getLeader() == currentUser) { // 리더 검증
            List<Invite> invites = inviteService.getInviteByTeam(team);
            List<User> alreadyInvitedUsers = invites.stream()
                    .map(Invite::getUser)
                    .toList();
            List<User> users = userRepository.findAllByUsernameNotJoined(search, teamId);

            return users.stream()
                    .map(user -> {
                        boolean invited = alreadyInvitedUsers.contains(user);
                        return UserForInviteDto.builder()
                                .username(user.getUsername())
                                .name(user.getName())
                                .invited(invited)
                                .build();
                    })
                    .toList();
        }
        throw new Exception("리더 외 조회 불가능");
    }
}
