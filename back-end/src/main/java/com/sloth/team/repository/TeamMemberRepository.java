package com.sloth.team.repository;

import com.sloth.domain.user.domain.User;
import com.sloth.team.domain.Team;
import com.sloth.team.domain.TeamMember;

import java.util.List;

public interface TeamMemberRepository {

    /* TeamMember 객체 저장 */
    void save(TeamMember teamMember);

    /* Id -> TeamMember 객체 조회 */
    TeamMember findById(Long id);

    /* Member -> TeamMember 객체 조회 */
    List<TeamMember> findByMember(User user);

    /* Member -> TeamMember 객체 조회 */
    List<TeamMember> findByTeam(Team team);

    /* TeamMember 객체 삭제 */
    void remove(TeamMember teamMember);
}
