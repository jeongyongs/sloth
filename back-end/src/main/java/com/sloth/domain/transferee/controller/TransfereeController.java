package com.sloth.domain.transferee.controller;

import com.sloth.domain.transferee.dto.TransfereeViewDto;
import com.sloth.domain.transferee.service.TransfereeViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransfereeController {

    private final TransfereeViewService transfereeViewService;

    @GetMapping("/transferees")
    public List<TransfereeViewDto> getList(HttpServletRequest request, @RequestParam Long teamId) throws Exception {
        return transfereeViewService.findByUser(request, teamId);
    }
}
