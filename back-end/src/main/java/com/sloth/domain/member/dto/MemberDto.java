package com.sloth.domain.member.dto;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private Team team;  // 팀
    private User user;  // 유저
}
