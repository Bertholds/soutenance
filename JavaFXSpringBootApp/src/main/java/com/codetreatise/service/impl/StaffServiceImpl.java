package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Personel;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class StaffServiceImpl implements GlobalService<Personel> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Personel update(Personel personel) {
		Personel newpersonel = em.merge(personel); 
		return newpersonel;
	}

}
