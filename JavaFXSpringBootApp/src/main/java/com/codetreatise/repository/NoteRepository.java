package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Note;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	
	@Query("select n from Note n where n.matiere.nom=(:nom)")
      public java.util.List<Note> findByClasse(@Param("nom")String nom);
}
