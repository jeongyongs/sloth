package com.sloth.team.repository;

import com.sloth.team.domain.Team;

import java.util.List;

public interface TeamRepository {

    /* Team 객체 저장 */
    void save(Team team);

    /* Id -> Team 객체 조회 */
    Team findById(Long id);

    /* Name -> Team 객체 조회 */
    Team findByName(String name);

    /* 모든 Team 객체 조회 */
    List<Team> findAll();

    /* Team 객체 삭제 */
    void remove(Team team);
}
