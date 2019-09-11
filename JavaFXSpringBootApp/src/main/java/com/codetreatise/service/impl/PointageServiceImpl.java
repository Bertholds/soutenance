package com.codetreatise.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Pointage;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class PointageServiceImpl implements GlobalService<Pointage> {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Pointage update(Pointage pointage) {
		Pointage newPointage = new Pointage();
		newPointage = em.merge(pointage);
		return newPointage;
	}
	
	public List<Pointage> findAllLimitBy(int limit) {
		Query query = em.createQuery("select p from Pointage p");
		query.setFirstResult(1);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
