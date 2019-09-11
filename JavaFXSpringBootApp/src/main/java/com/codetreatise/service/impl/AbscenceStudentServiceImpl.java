package com.codetreatise.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	
	public List<AbscenceStudent> listAbscence (Long id, Date depart, Date limit) {
		Query query =em.createQuery("Select max(a.id_abscence), sum(a.quantite), sum(a.justifier) , a.etudiant.id from AbscenceStudent a where a.etudiant.id=(:id) and a.dates between(:x) and(:y) ");
		query.setParameter("id", id);
		query.setParameter("x", depart);
		query.setParameter("y", limit);
		return query.getResultList();
	}

}
