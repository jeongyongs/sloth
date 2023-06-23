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
public class HandoverTransfereeViewDto {

    private Long HandoverId;
    private Date transferDate;
    private String title;
    private String transferor;
    private String transferee;
    private boolean state;
}
