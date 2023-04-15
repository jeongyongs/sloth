package com.sloth.team.repository;

import com.sloth.member.domain.Member;
import com.sloth.team.domain.InviteTeam;
import com.sloth.team.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlInviteTeamRepository implements InviteTeamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    /* InviteTeam 객체 저장 */
    public void save(InviteTeam inviteTeam) {

        entityManager.persist(inviteTeam);
    }

    @Override
    /* Id -> InviteTeam 객체 조회 */
    public InviteTeam findById(Long id) {

        return entityManager.find(InviteTeam.class, id);
    }

    @Override
    /* Team -> InviteTeam 객체 조회 */
    public List<InviteTeam> findByNTeam(Team team) {

        String jpql = "SELECT inviteTeam FROM InviteTeam inviteTeam WHERE inviteTeam.team = :team";
        List<InviteTeam> inviteTeams =
                entityManager.createQuery(jpql, InviteTeam.class).setParameter("team", team).getResultList();
        // 조회 성공
        if (!inviteTeams.isEmpty()) {
            return inviteTeams;
        }
        // 조회 실패
        return null;
    }

    @Override
    /* Member -> InviteTeam 객체 조회 */
    public List<InviteTeam> findByMember(Member member) {

        String jpql = "SELECT inviteTeam FROM InviteTeam inviteTeam WHERE inviteTeam.member = :member";
        List<InviteTeam> inviteTeams =
                entityManager.createQuery(jpql, InviteTeam.class).setParameter("member", member).getResultList();
        // 조회 성공
        if (!inviteTeams.isEmpty()) {
            return inviteTeams;
        }
        // 조회 실패
        return null;
    }

    @Override
    /* InviteTeam 객체 삭제 */
    public void remove(InviteTeam inviteTeam) {

        entityManager.remove(inviteTeam);
    }
}
