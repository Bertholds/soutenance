package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Poste;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class PosteServiceImpl implements GlobalService<Poste> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Poste update(Poste poste) {
		Poste newposte = em.merge(poste); 
		return newposte;
	}
	
}
