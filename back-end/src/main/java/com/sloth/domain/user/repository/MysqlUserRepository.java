package com.sloth.domain.user.repository;

import com.sloth.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlUserRepository implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(User user) {   // 객체 저장
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) { // 아이디로 객체 조회
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {   // 유저네임으로 객체 조회

        String jpql = "SELECT user FROM User user WHERE user.username = :username";

        return entityManager
                .createQuery(jpql, User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<User> findAll() {   // 모든 객체 조회

        String jpql = "SELECT user FROM User user";

        return entityManager
                .createQuery(jpql, User.class)
                .getResultList();
    }

    @Override
    public void remove(User user) { // 객체 삭제
        entityManager.remove(user);
    }
}
