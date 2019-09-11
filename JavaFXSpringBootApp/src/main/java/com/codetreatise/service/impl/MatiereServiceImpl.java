package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Matiere;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class MatiereServiceImpl implements GlobalService<Matiere> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Matiere update(Matiere matiere) {
		Matiere newMatiere = em.merge(matiere);
		return newMatiere;
	}

}
