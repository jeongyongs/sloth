package com.sloth.team.repository;

import com.sloth.domain.user.domain.User;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;

import java.util.List;

public interface InviteTeamRepository {

    /* InviteTeam 객체 저장 */
    void save(InviteTeam inviteTeam);

    /* Id -> InviteTeam 객체 조회 */
    InviteTeam findById(Long id);

    /* Team -> InviteTeam 객체 조회 */
    List<InviteTeam> findByNTeam(Team team);

    /* Member -> InviteTeam 객체 조회 */
    List<InviteTeam> findByMember(User user);

    /* InviteTeam 객체 삭제 */
    void remove(InviteTeam inviteTeam);
}
