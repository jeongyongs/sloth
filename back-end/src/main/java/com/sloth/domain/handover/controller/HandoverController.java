package com.sloth.domain.handover.controller;

import com.sloth.domain.handover.dto.*;
import com.sloth.domain.handover.service.HandoverManageService;
import com.sloth.domain.handover.service.MemberForTransferService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HandoverController {

    private final HandoverManageService handoverManageService;
    private final MemberForTransferService memberForTransferService;

    @PostMapping("/handovers")
    public void create(HttpServletRequest request, @RequestBody HandoverDto data) throws Exception {
        handoverManageService.create(request, data);
    }

    @GetMapping("/handovers")
    public List<HandoverViewDto> getList(HttpServletRequest request, @RequestParam Long teamId) throws Exception {
        return handoverManageService.getList(request, teamId);
    }

	@DeleteMapping("/handovers/{id}")
	public void removeHandover(HttpServletRequest request, @PathVariable Long id) throws Exception {
		handoverManageService.removeHandover(request, id);
	}

	@GetMapping("/handovers/{id}")
	public HandoverDetailDto getDetailed(HttpServletRequest request, @PathVariable Long id) throws Exception {
		return handoverManageService.getDetailed(request, id);
	}

	@GetMapping("/handovers/{id}/members")
	public List<MemberForTransferDto> getMembers(HttpServletRequest request, @PathVariable Long id, @RequestParam String search) throws Exception {
		return memberForTransferService.getList(request, id, search);
	}

	@GetMapping("/handovers/transferee")
	public List<HandoverTransfereeViewDto> getHandoverList(HttpServletRequest request, @RequestParam Long teamId) throws Exception {
		return handoverManageService.getHandoverList(request, teamId);
	}
}
