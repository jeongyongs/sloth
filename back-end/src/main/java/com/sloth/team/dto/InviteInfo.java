package com.sloth.team.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InviteInfo {

    private Long id;
    private String teamName;
    private String teamOwner;
    private Long expired;
}
