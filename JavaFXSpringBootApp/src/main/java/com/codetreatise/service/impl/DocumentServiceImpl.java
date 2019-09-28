package com.codetreatise.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Document;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class DocumentServiceImpl implements GlobalService<Document> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Document update(Document document) {
		Document newDocument = em.merge(document); 
		return newDocument;
	}
	
}
