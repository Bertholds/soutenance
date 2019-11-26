package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Preinscription;


@Repository
public interface PreInscriptionRepository extends JpaRepository<Preinscription, Long> {

	@Query("select sum(p.montant) from Preinscription p")
	public double getTotalMontant();
	
	@Query("select count(p.id_preinscription) from Preinscription p")
	public int getTotalPreinscrit();
	
	@Query("select count(p.id_preinscription) from Preinscription p where p.etudiant.classe.niveau='niveau 1'")
	public int getTotalPreinscritNiveau1();
	
	@Query("select count(p.id_preinscription) from Preinscription p where p.etudiant.classe.niveau='niveau 2'")
	public int getTotalPreinscritNiveau2();
	
	@Query("select count(p.id_preinscription) from Preinscription p where p.etudiant.classe.niveau='niveau 3'")
	public int getTotalPreinscritNiveau3();
}
