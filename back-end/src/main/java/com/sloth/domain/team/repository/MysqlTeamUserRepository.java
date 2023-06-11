package com.sloth.domain.team.repository;

import com.sloth.domain.team.domain.Team;
import com.sloth.domain.team.domain.TeamUser;
import com.sloth.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlTeamUserRepository implements TeamUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(TeamUser teamUser) {   // 객체 저장
        entityManager.persist(teamUser);
    }

    @Override
    public TeamUser findById(Long id) { // 아이디로 객체 조회
        return entityManager.find(TeamUser.class, id);
    }

    @Override
    public List<TeamUser> findAll() {   // 모든 객체 조회

        String jpql = "SELECT teamUser FROM TeamUser teamUser";

        return entityManager
                .createQuery(jpql, TeamUser.class)
                .getResultList();
    }

    @Override
    public List<TeamUser> findAllByTeam(Team team) {    // 팀으로 객체 조회

        String jpql = "SELECT teamUser FROM TeamUser teamUser WHERE teamUser.team = :team";

        return entityManager
                .createQuery(jpql, TeamUser.class)
                .setParameter("team", team)
                .getResultList();
    }

    @Override
    public List<TeamUser> findAllByUser(User user) {    // 유저로 객체 조회

        String jpql = "SELECT teamUser FROM TeamUser teamUser WHERE teamUser.user = :user";

        return entityManager
                .createQuery(jpql, TeamUser.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public void remove(TeamUser teamUser) { // 객체 삭제
        entityManager.remove(teamUser);
    }
}
