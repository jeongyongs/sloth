package com.sloth.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder
public class NewMemberDto {

    @NonNull
    private final String username;
    @NonNull
    private final String password;
    private final String name;
    private final String email;
    private final String phone;
}
