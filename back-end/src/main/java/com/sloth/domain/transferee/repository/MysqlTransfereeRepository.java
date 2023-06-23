package com.sloth.domain.transferee.repository;

import com.sloth.domain.handover.domain.Handover;
import com.sloth.domain.team.domain.Team;
import com.sloth.domain.transferee.domain.Transferee;
import com.sloth.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlTransfereeRepository implements TransfereeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Transferee transferee) { // 객체 저장
		entityManager.persist(transferee);
	}

	@Override
	public Transferee findById(Long id) { // 아이디로 객체 조회
		return entityManager.find(Transferee.class, id);
	}

	@Override
	public List<Transferee> findAll() { // 모든 객체 조회

		String jpql = "SELECT transferee FROM Transferee transferee";

		return entityManager
			.createQuery(jpql, Transferee.class)
			.getResultList();
	}

	@Override
	public List<Transferee> findAllByUser(User user) { // 유저로 객체 조회

		String jpql = "SELECT transferee FROM Transferee transferee WHERE transferee.user = :user";

		return entityManager
			.createQuery(jpql, Transferee.class)
			.setParameter("user", user)
			.getResultList();
	}

	@Override
	public List<Transferee> findAllByTeamAndUser(Team team, User user) {

		String jpql = "SELECT transferee FROM Transferee transferee" +
			" WHERE transferee.user = :user AND transferee.handover.team = :team ORDER BY transferee.transferDate DESC";

		return entityManager
			.createQuery(jpql, Transferee.class)
			.setParameter("user", user)
			.setParameter("team", team)
			.getResultList();
	}

	@Override
	public Transferee findByHandoverAndUser(Handover handover, User user) {

		String jpql = "SELECT transferee FROM Transferee transferee" +
			" WHERE transferee.user = :user AND transferee.handover = :handover";

		return entityManager
			.createQuery(jpql, Transferee.class)
			.setParameter("user", user)
			.setParameter("handover", handover)
			.getSingleResult();
	}

	@Override
	public void remove(Transferee transferee) { // 객체 삭제
		entityManager.remove(transferee);
	}

	@Override
	public List<Transferee> findAllByHandover(Handover handover) {

		String jpql = "SELECT transferee FROM Transferee transferee" +
			" WHERE transferee.handover = :handover";

		return entityManager
			.createQuery(jpql, Transferee.class)
			.setParameter("handover", handover)
			.getResultList();
	}

	@Override
	public void removeAllByHandover(Handover handover) {

		String jpql = "DELETE FROM Transferee transferee WHERE transferee.handover = :handover";

		entityManager.createQuery(jpql)
			.setParameter("handover", handover)
			.executeUpdate();
	}
}
