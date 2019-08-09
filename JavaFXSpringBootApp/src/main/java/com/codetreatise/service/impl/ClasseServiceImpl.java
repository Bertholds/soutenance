package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Classe;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class ClasseServiceImpl implements GlobalService<Classe> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Classe update(Classe classe) {
		Classe newClasse = em.merge(classe);
		return newClasse;
	}

}
