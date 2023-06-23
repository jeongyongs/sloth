package com.sloth.domain.member.repository;

import com.sloth.domain.member.domain.Member;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.user.domain.User;

import java.util.List;

public interface MemberRepository {

    void save(Member member);   // 객체 저장

    Member findById(Long id); // 아이디로 객체 조회

    Member findByTeamAndUser(Team team, User user);   // 팀과 유저로 객체 조회

    List<Member> findAll();   // 모든 객체 조회

    List<Member> findAllByTeam(Team team);   // 팀으로 객체 조회

    List<Member> findAllByUser(User user);   // 유저로 객체 조회

    void remove(Member member); // 객체 삭제

	List<Member> findAllBySearch(String username, Team team);
}
