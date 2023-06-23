package com.sloth.domain.transferee.repository;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.user.domain.User;

import java.util.List;

public interface TransfereeRepository {

    void save(Transferee transferee);   // 객체 저장

    Transferee findById(Long id); // 아이디로 객체 조회

    List<Transferee> findAll();   // 모든 객체 조회

    List<Transferee> findAllByUser(User user);   // 유저로 객체 조회

    List<Transferee> findAllByTeamAndUser(Team team, User user);   // 팀과 유저로 객체 조회

	Transferee findByHandoverAndUser(Handover handover, User user);

	void remove(Transferee transferee); // 객체 삭제

    List<Transferee> findAllByHandover(Handover handover);

	void removeAllByHandover(Handover handover);
}
