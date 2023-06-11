package com.sloth.domain.team.repository;

import com.sloth.domain.team.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlTeamRepository implements TeamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Team team) {   // 객체 저장
        entityManager.persist(team);
    }

    @Override
    public Team findById(Long id) { // 아이디로 객체 조회
        return entityManager.find(Team.class, id);
    }

    @Override
    public Team findByName(String name) {   // 이름으로 객체 조회

        String jpql = "SELECT team FROM Team team WHERE team.name = :name";

        return entityManager
                .createQuery(jpql, Team.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Team> findAll() {   // 모든 객체 조회

        String jpql = "SELECT team FROM Team team";

        return entityManager
                .createQuery(jpql, Team.class)
                .getResultList();
    }

    @Override
    public void remove(Team team) { // 객체 삭제
        entityManager.remove(team);
    }
}
