package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Departement;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class DepartementServiceImpl implements GlobalService<Departement> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Departement update(Departement departement) {
		Departement newdepartement = em.merge(departement); 
		return newdepartement;
	}
	
}
