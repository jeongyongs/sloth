package com.sloth.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDto {

    private String username;  // 아이디
    private String password;  // 비밀번호
}
