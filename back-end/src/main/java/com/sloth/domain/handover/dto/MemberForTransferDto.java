package com.sloth.domain.handover.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberForTransferDto {

	private String username; // 아이디
	private String name; // 이 름
	private boolean transferee; // 인수자 여부
}
