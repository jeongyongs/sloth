package com.sloth.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDto {

    private String username;  // 아이디
    private String password;  // 비밀번호
    private String name;  // 이름
}
