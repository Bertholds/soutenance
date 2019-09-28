package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Utilisateur;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class UserServiceImpl implements GlobalService<Utilisateur> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Utilisateur update(Utilisateur utilisateur) {
		Utilisateur newUtilisateur = em.merge(utilisateur);
		return newUtilisateur;
	}

}
