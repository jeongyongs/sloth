package com.sloth.member.repository;

import com.sloth.member.domain.Member;

import java.util.List;

public interface MemberRepository {

    /* Member 객체 저장 */
    void save(Member member);

    /* Id -> Member 객체 조회 */
    Member findById(Long id);

    /* Username -> Member 객체 조회 */
    Member findByUsername(String username);

    /* 모든 Member 객체 조회 */
    List<Member> findAll();

    /* Id -> Member 객체 삭제 */
    void remove(Member member);
}
