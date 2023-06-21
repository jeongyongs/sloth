package com.sloth.domain.handover.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandoverDto {

    private String title;
    private String content;
    private Long teamId;
}
