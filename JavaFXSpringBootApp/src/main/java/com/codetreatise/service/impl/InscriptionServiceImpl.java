package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Inscription;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class InscriptionServiceImpl implements GlobalService<Inscription> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Inscription update(Inscription inscription) {
		Inscription newinscription = em.merge(inscription); 
		return newinscription;
	}
	
}
