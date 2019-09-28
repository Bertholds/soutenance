package com.codetreatise.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Permission;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class PermissionServiceImpl implements GlobalService<Permission> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Permission update(Permission permission) {
		Permission newpermission = em.merge(permission);
		return newpermission;
	}
	
	public List<Permission> findAllLimitBy(int limit) {
		Query query = em.createQuery("select p from Permission p");
		query.setFirstResult(1);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
