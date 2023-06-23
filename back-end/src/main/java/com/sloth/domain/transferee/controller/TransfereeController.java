package com.sloth.domain.transferee.controller;

import com.sloth.domain.transferee.dto.NewTransfereeDto;
import com.sloth.domain.transferee.dto.TransfereeViewDto;
import com.sloth.domain.transferee.service.TransfereeManageService;
import com.sloth.domain.transferee.service.TransfereeService;
import com.sloth.domain.transferee.service.TransfereeViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransfereeController {

	private final TransfereeViewService transfereeViewService;
	private final TransfereeManageService transfereeManageService;

	@GetMapping("/transferees")
	public List<TransfereeViewDto> getList(HttpServletRequest request, @RequestParam Long teamId) throws Exception {
		return transfereeViewService.findByUser(request, teamId);
	}

	@PostMapping("/transferees")
	public void addTransferee(HttpServletRequest request, @RequestBody NewTransfereeDto data) throws Exception {
		transfereeManageService.saveTransferee(request, data);
	}

	@DeleteMapping("/transferees")
	public void removeTransferee(HttpServletRequest request, @RequestParam Long handoverId, @RequestParam String username) throws Exception {
		transfereeManageService.removeTransferee(request, handoverId, username);
	}
}
