package com.sloth.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDetailDto {

    private final String username;
    private final String name;
    private final String email;
    private final String phone;
}
