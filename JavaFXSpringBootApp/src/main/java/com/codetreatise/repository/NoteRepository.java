package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	

}
