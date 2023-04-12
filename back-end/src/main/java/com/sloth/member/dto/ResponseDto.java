package com.sloth.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseDto {

    private final boolean success;
    private final String message;
    private final Object data;
}
