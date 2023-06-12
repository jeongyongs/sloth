package com.sloth.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamInfoDto {

    private String teamName;  // 이름
    private String leaderName;  // 리더
    private Date createDate;    // 생성 날짜
}
