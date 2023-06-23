package com.sloth.domain.handover.service;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.handover.dto.HandoverDetailDto;
import com.sloth.domain.handover.dto.HandoverDto;
import com.sloth.domain.handover.dto.HandoverTransfereeViewDto;
import com.sloth.domain.handover.dto.HandoverViewDto;
import com.sloth.domain.member.domain.Member;
import com.sloth.domain.member.service.MemberService;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.service.TeamQueryService;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.transferee.service.TransfereeService;
import com.sloth.domain.user.domain.User;
import com.sloth.domain.user.service.UserService;
import com.sloth.global.auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HandoverManageService {

	private final HandoverService handoverService;
	private final JwtService jwtService;
	private final UserService userService;
	private final TeamQueryService teamQueryService;
	private final MemberService memberService;
	private final TransfereeService transfereeService;

	public void create(HttpServletRequest request, HandoverDto data) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Team team = teamQueryService.getTeamById(data.getTeamId());
		Member member = memberService.findByTeamAndUser(team, user);

		if (member.getUser() == user) {
			Handover handover = Handover.builder()
				.team(team)
				.transferor(user)
				.title(data.getTitle())
				.content(data.getContent())
				.createDate(new Date())
				.build();
			handoverService.save(handover);
			return;
		}
		throw new Exception("가입하지 않은 팀에 인수인계 작성 불가능");
	}

	public List<HandoverViewDto> getList(HttpServletRequest request, Long teamId) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Team team = teamQueryService.getTeamById(teamId);
		List<Handover> handovers = handoverService.findByTeamAndUser(team, user);

		return handovers.stream()
			.map(handover -> HandoverViewDto.builder()
				.HandoverId(handover.getId())
				.createDate(handover.getCreateDate())
				.title(handover.getTitle())
				.transferor(handover.getTransferor().getName())
				.state(handover.isState())
				.build())
			.toList();
	}

	public void removeHandover(HttpServletRequest request, Long id) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Handover handover = handoverService.findById(id);

		if (handover.getTransferor() == user) {
			transfereeService.removeAllByHandover(handover);
			handoverService.remove(handover);
			return;
		}
		throw new Exception("작성자 외 삭제 불가");
	}

	public HandoverDetailDto getDetailed(HttpServletRequest request, Long id) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Handover handover = handoverService.findById(id);
		boolean auth = false;

		try {
			Transferee transferee = transfereeService.findByHandoverAndUser(handover, user);
			auth = true;
		} catch (Exception ignored) {
		}

		if (handover.getTransferor() == user || auth) {
			return HandoverDetailDto.builder()
				.title(handover.getTitle())
				.content(handover.getContent())
				.build();
		}
		throw new Exception("열람 권한 없음");
	}

	public List<HandoverTransfereeViewDto> getHandoverList(HttpServletRequest request, Long teamId) throws Exception {
		String username = jwtService.getUsername(request);
		User user = userService.getUserByUsername(username);
		Team team = teamQueryService.getTeamById(teamId);
		List<Transferee> transferees = transfereeService.findByUserAndTeam(team, user);
		List<Handover> handovers = transferees.stream()
			.map(Transferee::getHandover)
			.toList();

		return handovers.stream()
			.map(handover -> HandoverTransfereeViewDto.builder()
				.HandoverId(handover.getId())
				.transferDate(handover.getCreateDate())
				.title(handover.getTitle())
				.transferor(handover.getTransferor().getName())
				.transferee(user.getName())
				.state(handover.isState())
				.build())
			.toList();
	}
}
