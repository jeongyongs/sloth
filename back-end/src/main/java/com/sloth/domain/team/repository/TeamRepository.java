package com.sloth.domain.team.repository;

import com.sloth.domain.team.domain.Team;

import java.util.List;

public interface TeamRepository {

    void save(Team team);   // 객체 저장

    Team findById(Long id); // 아이디로 객체 조회

    Team findByName(String name);   // 이름으로 객체 조회

    List<Team> findAll();   // 모든 객체 조회

    void remove(Team team); // 객체 삭제
}
