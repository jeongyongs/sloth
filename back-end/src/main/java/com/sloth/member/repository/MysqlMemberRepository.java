package com.sloth.member.repository;

import com.sloth.member.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlMemberRepository implements MemberRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    /* Member 객체 저장 */
    public void save(Member member) {

        entityManager.persist(member);
    }

    @Override
    /* Id -> Member 객체 조회 */
    public Member findById(Long id) {

        return entityManager.find(Member.class, id);
    }

    @Override
    /* Username -> Member 객체 조회 */
    public Member findByUsername(String username) {

        String jpql = "SELECT member FROM Member member WHERE member.username = :username";

        try {
            return entityManager
                    .createQuery(jpql, Member.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    /* 모든 Member 객체 조회 */
    public List<Member> findAll() {

        String jpql = "SELECT member FROM Member member";

        try {
            return entityManager
                    .createQuery(jpql, Member.class)
                    .getResultList();

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    /* Id -> Member 객체 삭제 */
    public void remove(Member member) {

        entityManager.remove(member);
    }
}
