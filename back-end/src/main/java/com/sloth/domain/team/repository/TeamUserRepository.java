package com.sloth.domain.team.repository;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.domain.TeamUser;
import com.sloth.domain.user.domain.User;

import java.util.List;

public interface TeamUserRepository {

    void save(TeamUser teamUser);   // 객체 저장

    TeamUser findById(Long id); // 아이디로 객체 조회

    List<TeamUser> findAll();   // 모든 객체 조회

    List<TeamUser> findAllByTeam(Team team);   // 팀으로 객체 조회

    List<TeamUser> findAllByUser(User user);   // 유저로 객체 조회

    void remove(TeamUser teamUser); // 객체 삭제
}
