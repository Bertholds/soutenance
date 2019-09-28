package com.codetreatise.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Courier;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class CourierServiceImpl implements GlobalService<Courier> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Courier update(Courier courier) {
		Courier newCourier = em.merge(courier);
		return newCourier;
	}
	
	public List<Courier> findAllLimitBy(int limit) {
		Query query = em.createQuery("select c from Courier c");
		query.setFirstResult(1);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
