package com.sloth.domain.user.repository;

import com.sloth.domain.user.domain.User;

import java.util.List;

public interface UserRepository {

    void save(User user);   // 객체 저장

    User findById(Long id); // 아이디로 객체 조회

    User findByUsername(String username);   // 유저네임으로 객체 조회

    List<User> findAll();   // 모든 객체 조회

    void remove(User user); // 객체 삭제
}
