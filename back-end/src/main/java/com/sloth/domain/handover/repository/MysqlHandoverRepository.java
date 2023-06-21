package com.sloth.domain.handover.repository;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlHandoverRepository implements HandoverRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Handover handover) {
        entityManager.persist(handover);
    }

    @Override
    public Handover findById(Long id) {
        return entityManager.find(Handover.class, id);
    }

    @Override
    public void remove(Handover handover) {
        entityManager.remove(handover);
    }

    @Override
    public List<Handover> findByTeamAndUser(Team team, User user) {

        String jpql = "SELECT handover FROM Handover handover " +
                "WHERE handover.team = :team AND handover.transferor=:user ORDER BY handover.createDate DESC";

        return entityManager.createQuery(jpql, Handover.class)
                .setParameter("team", team)
                .setParameter("user", user)
                .getResultList();
    }
}
