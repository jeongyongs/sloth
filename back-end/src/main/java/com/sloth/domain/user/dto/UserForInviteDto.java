package com.sloth.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForInviteDto {

    private String username;    // 아이디
    private String name;    // 이름
    private boolean invited;    // 초대 여부
}
