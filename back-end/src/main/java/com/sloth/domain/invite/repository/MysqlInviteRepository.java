package com.sloth.domain.invite.repository;

import com.sloth.domain.invite.domain.Invite;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlInviteRepository implements InviteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Invite invite) {   // 객체 저장
        entityManager.persist(invite);
    }

    @Override
    public Invite findById(Long id) { // 아이디로 객체 조회
        return entityManager.find(Invite.class, id);
    }

    @Override
    public Invite findByTeamAndUser(Team team, User user) { // 팀과 유저로 객체 조회

        String jpql = "SELECT invite FROM Invite invite WHERE invite.team = :team AND invite.user = :user";

        return entityManager
                .createQuery(jpql, Invite.class)
                .setParameter("team", team)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public List<Invite> findAll() {   // 모든 객체 조회

        String jpql = "SELECT invite FROM Invite invite";

        return entityManager
                .createQuery(jpql, Invite.class)
                .getResultList();
    }

    @Override
    public List<Invite> findAllByTeam(Team team) {    // 팀으로 객체 조회

        String jpql = "SELECT invite FROM Invite invite WHERE invite.team = :team";

        return entityManager
                .createQuery(jpql, Invite.class)
                .setParameter("team", team)
                .getResultList();
    }

    @Override
    public List<Invite> findAllByUser(User user) {    // 유저로 객체 조회

        String jpql = "SELECT invite FROM Invite invite WHERE invite.user = :user";

        return entityManager
                .createQuery(jpql, Invite.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public void remove(Invite invite) { // 객체 삭제
        entityManager.remove(invite);
    }
}
