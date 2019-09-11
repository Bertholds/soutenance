package com.codetreatise.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Matiere;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
	
	@Query("select m from Matiere m where m.niveau like(:niveau)")
    public ArrayList<Matiere>findNiveau(@Param("niveau")String niveau);
	
	@Query("select max(m.id_matiere) from Matiere m")
	public Long findLastId();
	
	@Query("select m from Matiere m where m.id_matiere=(:id)")
	public Matiere findByLastId(@Param("id")Long id);
	
	@Query("select m.nom from Matiere m ")
	public ArrayList<String> loadAllMatiere();
	
	@Query("select m.nom from Matiere m where m.niveau like(:nom) ")
	public ArrayList<String> loadMatiereByClasse(@Param("nom")String nom);
	
	@Query("select m from Matiere m where m.nom=(:nom) ")
	public Matiere findByNom(@Param("nom")String nom);
	
	@Query("select sum(m.coefficient) from Matiere m ")
	public int findTotalCoefficient();
}
//SELECT m.nom, c.name from matiere m join matiere_classe mc on m.id_matiere = mc.id_matiere join classe c on c.id_classe=mc.id_classe
//where c.name='Gl3b'
