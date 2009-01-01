package com.codetreatise.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Personel;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
	@Query("select distinct c.nom from Classe c")
	public ArrayList<String> loadAllClass();
	
	@Query("select c from Classe c where c.nom=(:classe) or c.niveau=(:classe)")
	public ArrayList<Classe> findClasse(@Param("classe")String classe);
	
	@Query("select  p from Personel p")
	public ArrayList<Personel> loadLeader();
	
	@Query("select distinct c.niveau from Classe c")
	public ArrayList<String> loadAllNiveau();

	@Query("select c from Classe c where c.nom=(:nom)")
	public Classe findByNom(@Param("nom")String nom);

	@Query("select max(c.id_classe) from Classe c")
	public Long findLastId();
	
	@Query("select c from Classe c where c.id_classe=(:id)")
	public Classe findByLastId(@Param("id")Long id);

}
