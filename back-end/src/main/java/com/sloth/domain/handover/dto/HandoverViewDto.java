package com.sloth.domain.handover.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandoverViewDto {

    private Long HandoverId;
    private Date createDate;
    private String title;
    private String transferor;
    private boolean state;
}
