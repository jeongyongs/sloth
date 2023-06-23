package com.sloth.domain.transferee.service;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.handover.service.HandoverService;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.transferee.dto.NewTransfereeDto;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransfereeManageService {

	private final JwtService jwtService;
	private final UserService userService;
	private final HandoverService handoverService;
	private final TransfereeService transfereeService;

	public void saveTransferee(HttpServletRequest request, NewTransfereeDto data) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		User target = userService.getUserByUsername(data.getUsername());
		Handover handover = handoverService.findById(data.getHandoverId());

		if (handover.getTransferor() == user) {
			transfereeService.save(Transferee.builder()
				.handover(handover)
				.user(target)
				.transferDate(new Date())
				.state(false)
				.build());
			return;
		}
		throw new Exception("인계자 외 추가 불가능");
	}

	public void removeTransferee(HttpServletRequest request, Long handoverId, String targetUsername) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Handover handover = handoverService.findById(handoverId);

		if (handover.getTransferor() == user) {
			User target = userService.getUserByUsername(targetUsername);
			Transferee transferee = transfereeService.findByHandoverAndUser(handover, target);
			transfereeService.remove(transferee);
			return;
		}
		throw new Exception("인계자 외 삭제 불가능");
	}
}
