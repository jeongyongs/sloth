package com.sloth.domain.transferee.service;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.transferee.repository.TransfereeRepository;
import com.sloth.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransfereeService {

	private final TransfereeRepository transfereeRepository;

	public List<Transferee> findByUser(User user) {
		return transfereeRepository.findAllByUser(user);
	}

	public List<Transferee> findByUserAndTeam(Team team, User user) {
		return transfereeRepository.findAllByTeamAndUser(team, user);
	}

	public List<Transferee> findByHandover(Handover handover) {
		return transfereeRepository.findAllByHandover(handover);
	}

	@Transactional
	public void save(Transferee transferee) {
		transfereeRepository.save(transferee);
	}

	@Transactional
	public void remove(Transferee transferee) {
		transfereeRepository.remove(transferee);
	}

	public Transferee findByHandoverAndUser(Handover handover, User user) {
		return transfereeRepository.findByHandoverAndUser(handover, user);
	}

	@Transactional
	public void removeAllByHandover(Handover handover) {
		transfereeRepository.removeAllByHandover(handover);
	}
}
