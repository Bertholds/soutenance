package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Categorie;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class CategorieServiceImpl implements GlobalService<Categorie> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Categorie update(Categorie categorie) {
		Categorie newCategorie = em.merge(categorie);
		return newCategorie;
	}

}
