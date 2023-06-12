package com.sloth.domain.member.repository;

import com.sloth.domain.member.domain.Member;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlMemberRepository implements MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Member member) {   // 객체 저장
        entityManager.persist(member);
    }

    @Override
    public Member findById(Long id) { // 아이디로 객체 조회
        return entityManager.find(Member.class, id);
    }

    @Override
    public Member findByTeamAndUser(Team team, User user) { // 팀과 유저로 객체 조회

        String jpql = "SELECT member FROM Member member WHERE member.team = :team AND member.user = :user";

        return entityManager
                .createQuery(jpql, Member.class)
                .setParameter("team", team)
                .setParameter("user", user)
                .getSingleResult();
    }

    @Override
    public List<Member> findAll() {   // 모든 객체 조회

        String jpql = "SELECT member FROM Member member";

        return entityManager
                .createQuery(jpql, Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findAllByTeam(Team team) {    // 팀으로 객체 조회

        String jpql = "SELECT member FROM Member member WHERE member.team = :team";

        return entityManager
                .createQuery(jpql, Member.class)
                .setParameter("team", team)
                .getResultList();
    }

    @Override
    public List<Member> findAllByUser(User user) {    // 유저로 객체 조회

        String jpql = "SELECT member FROM Member member WHERE member.user = :user";

        return entityManager
                .createQuery(jpql, Member.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public void remove(Member member) { // 객체 삭제
        entityManager.remove(member);
    }
}
