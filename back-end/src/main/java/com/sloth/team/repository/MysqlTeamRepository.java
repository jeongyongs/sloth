package com.sloth.team.repository;

import com.sloth.team.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlTeamRepository implements TeamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    /* Team 객체 저장 */
    public void save(Team team) {

        entityManager.persist(team);
    }

    @Override
    /* Id -> Team 객체 조회 */
    public Team findById(Long id) {

        return entityManager.find(Team.class, id);
    }

    @Override
    /* Name -> Team 객체 조회 */
    public Team findByName(String name) {

        String jpql = "SELECT team FROM Team team WHERE team.name = :name";

        try {
            return entityManager
                    .createQuery(jpql, Team.class)
                    .setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    /* 모든 Team 객체 조회 */
    public List<Team> findAll() {

        String jpql = "SELECT team FROM Team team";

        try {
            return entityManager
                    .createQuery(jpql, Team.class)
                    .getResultList();

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    /* Id -> Team 객체 삭제 */
    public void remove(Team team) {

        entityManager.remove(team);
    }
}
