package com.sloth.domain.handover.service;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.handover.dto.MemberForTransferDto;
import com.sloth.domain.member.domain.Member;
import com.sloth.domain.member.service.MemberService;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.transferee.service.TransfereeService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberForTransferService {

	private final JwtService jwtService;
	private final UserService userService;
	private final HandoverService handoverService;
	private final MemberService memberService;
	private final TransfereeService transfereeService;

	public List<MemberForTransferDto> getList(HttpServletRequest request, Long id, String search) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Handover handover = handoverService.findById(id);

		if (handover.getTransferor() == user) {
			List<Member> members = memberService.findAllBySearch(search, handover.getTeam());
			List<Transferee> transferees = transfereeService.findByHandover(handover);

			List<User> users = transferees.stream()
				.map(Transferee::getUser)
				.toList();

			return members.stream()
				.filter(member -> member.getUser() != handover.getTransferor())
				.map(member -> MemberForTransferDto.builder()
					.username(member.getUser().getUsername())
					.name(member.getUser().getName())
					.transferee(users.contains(member.getUser()))
					.build())
				.toList();
		}
		throw new Exception("인계자 외 인수자 조회 불가");
	}
}
