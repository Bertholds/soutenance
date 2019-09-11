package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Utilisateur;

@Repository
@Transactional
public class OperationServiceImpl  {

	@PersistenceContext
	private EntityManager em;
	
	public void save(Utilisateur operaion) {
		em.persist(operaion);
	}

}
