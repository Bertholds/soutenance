package com.codetreatise.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Preinscription;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class preInscriptionServiceImpl implements GlobalService<Preinscription> {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Preinscription update(Preinscription preinscription) {
		Preinscription newpreinscription = em.merge(preinscription);
		return newpreinscription;
	}

}
