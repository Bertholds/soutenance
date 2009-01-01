package com.codetreatise.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Etudiant;

@javax.transaction.Transactional
public interface StudentRepository extends JpaRepository<Etudiant, Long> {

	@Query("select e from Etudiant e where e.classe.nom=(:classe) or e.classe.niveau=(:classe)")
	public ArrayList<Etudiant> findClasse(@Param("classe")String classe);

	@Query("update Etudiant  set nom=(:nom), prenom=(:prenom), matricule=(:matricule), sexe=(:sexe), classe=(:classe), parente=(:parente), naissance=(:naissance), telephone=(:telephone) where id=(:id)")
	@Modifying
	public void updateUser(@Param("nom")String nom, @Param("prenom")String prenom, @Param("matricule")String matricule, @Param("sexe")String sexe, @Param("classe")Classe classe, @Param("parente")String parent, @Param("naissance")LocalDate naissance, @Param("telephone")String phone, @Param("id")Long id);

	@Query("select max(e.id) from Etudiant e")
	public Long findLastId();
	
	@Query("select e from Etudiant e where e.id=(:id)")
	public Etudiant findByLastId(@Param("id")Long id);
	
	@Query("from Etudiant as e inner join e.classe as c group by c.niveau")
	public ResultSet groupByNiveau();
	
	//SELECT count(`id`), niveau from etudiant e\n" + 
	//"INNER JOIN classe c ON e.id_classe = c.id_classe Group by niveau;
	
}
