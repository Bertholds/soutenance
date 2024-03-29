package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.AbscenceStudent;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class AbscenceStudentServiceImpl implements GlobalService<AbscenceStudent> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public AbscenceStudent update(AbscenceStudent abscenceStudent) {
		AbscenceStudent newAbscenceStudent = em.merge(abscenceStudent);
		return newAbscenceStudent;
	}

}
