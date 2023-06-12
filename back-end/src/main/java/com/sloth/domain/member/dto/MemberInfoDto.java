package com.sloth.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoDto {

    private Long id;
    private String username;
    private String name;
    private String role;
    private Date joinDate;
}
