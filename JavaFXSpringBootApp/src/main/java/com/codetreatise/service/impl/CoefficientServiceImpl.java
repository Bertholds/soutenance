package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Coefficient;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class CoefficientServiceImpl implements GlobalService<Coefficient> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Coefficient update(Coefficient abscenceStudent) {
		Coefficient newCoefficient = em.merge(abscenceStudent);
		return newCoefficient;
	}

}
