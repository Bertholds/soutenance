package com.codetreatise.service.impl;

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

}
