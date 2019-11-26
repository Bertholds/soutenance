package com.codetreatise.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Note;
import com.codetreatise.service.GlobalService;

@Repository
@Transactional
public class NoteServiceImpl implements GlobalService<Note> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Note update(Note note) {
		Note newNote = em.merge(note);
		return newNote;
	}
	
	public List<Note> findByClasseAndMatiereOverOrder(String matiere, String classe ){
		javax.persistence.Query query = em.createQuery("select n from Note n where n.matiere.nom=(:nom) and n.classe.name=(:classe) ORDER BY n.moyenne DESC");
	    query.setParameter("nom", matiere);
	    query.setParameter("classe", classe);
	    query.setMaxResults(10);
	    return query.getResultList();
	}

}
