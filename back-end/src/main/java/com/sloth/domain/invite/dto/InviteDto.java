package com.sloth.domain.invite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteDto {

    private Long inviteId;
    private Long teamId;
    private String teamName;
    private String leaderUsername;
    private String leaderName;
    private Date inviteDate;
}
