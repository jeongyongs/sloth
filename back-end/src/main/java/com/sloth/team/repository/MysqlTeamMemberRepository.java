package com.sloth.team.repository;

import com.sloth.domain.user.domain.User;
import com.sloth.team.domain.Team;
import com.sloth.team.domain.TeamMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlTeamMemberRepository implements TeamMemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    /* teamMember 객체 저장 */
    public void save(TeamMember teamMember) {

        entityManager.persist(teamMember);
    }

    @Override
    /* Id -> TeamMember 객체 조회 */
    public TeamMember findById(Long id) {

        return entityManager.find(TeamMember.class, id);
    }

    @Override
    /* Member -> TeamMember 객체 조회 */
    public List<TeamMember> findByMember(User user) {

        String jpql = "SELECT teamMember FROM TeamMember teamMember WHERE teamMember.member = :member";

        try {
            return entityManager.createQuery(jpql, TeamMember.class).setParameter("member", user).getResultList();

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    /* Member -> TeamMember 객체 조회 */
    public List<TeamMember> findByTeam(Team team) {

        String jpql = "SELECT teamMember FROM TeamMember teamMember WHERE teamMember.team = :team";

        try {
            return entityManager.createQuery(jpql, TeamMember.class).setParameter("team", team).getResultList();

        } catch (Exception e) {

            return null;
        }
    }

    @Override
    /* TeamMember 객체 삭제 */
    public void remove(TeamMember teamMember) {

        entityManager.remove(teamMember);
    }
}
