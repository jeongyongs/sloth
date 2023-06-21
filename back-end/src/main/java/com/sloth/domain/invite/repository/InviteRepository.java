package com.sloth.domain.invite.repository;

import com.sloth.domain.invite.domain.Invite;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;

import java.util.List;

public interface InviteRepository {

    void save(Invite invite);   // 객체 저장

    Invite findById(Long id); // 아이디로 객체 조회

    Invite findByTeamAndUser(Team team, User user);   // 팀과 유저로 객체 조회

    List<Invite> findAll();   // 모든 객체 조회

    List<Invite> findAllByTeam(Team team);   // 팀으로 객체 조회

    List<Invite> findAllByUser(User user);   // 유저로 객체 조회

    void remove(Invite invite); // 객체 삭제
}
