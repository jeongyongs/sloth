package com.sloth.domain.handover.controller;

import com.sloth.domain.handover.dto.HandoverDto;
import com.sloth.domain.handover.dto.HandoverViewDto;
import com.sloth.domain.handover.service.HandoverManageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HandoverController {

    private final HandoverManageService handoverManageService;

    @PostMapping("/handovers")
    public void create(HttpServletRequest request, @RequestBody HandoverDto data) throws Exception {
        handoverManageService.create(request, data);
    }

    @GetMapping("/handovers")
    public List<HandoverViewDto> getList(HttpServletRequest request, @RequestParam Long teamId) throws Exception {
        return handoverManageService.getList(request, teamId);
    }
}
