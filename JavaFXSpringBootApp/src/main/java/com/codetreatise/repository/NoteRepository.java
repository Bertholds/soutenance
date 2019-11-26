package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Note;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	
	@Query("select n from Note n where n.matiere.nom=(:nom) and n.classe.name=(:classe)")
      public java.util.List<Note> findByClasseAndMatiere(@Param("nom")String nom, @Param("classe")String classe);

	@Query("select count(n.id_note) from Note n where n.matiere.nom=(:nom) and n.classe.name=(:classe)")
	public int getTotalEtudiant(@Param("nom")String nom, @Param("classe")String classe);
	
	@Query("select count(n.id_note) from Note n where n.note>=10 and n.matiere.nom=(:nom) and n.classe.name=(:classe)")
	public int getSucces(@Param("nom")String nom, @Param("classe")String classe);
	
	@Query("select count(n.id_note) from Note n where n.note<=10 and n.matiere.nom=(:nom) and n.classe.name=(:classe)")
	public int getEchec(@Param("nom")String nom, @Param("classe")String classe);
}
