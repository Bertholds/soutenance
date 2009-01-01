package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Inscription;


@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

	@Query("select sum(i.tranche1) from Inscription i")
	Double getTotalMontant1();
	
	@Query("select count(i.tranche1) from Inscription i where i.tranche1>0")
	Integer getTotalTranche1();
	
	@Query("select sum(i.tranche2) from Inscription i")
	Double getTotalMontant2();
	
	@Query("select count(i.tranche2) from Inscription i where i.tranche2>0")
	Integer getTotalTranche2();
	
	@Query("select sum(i.tranche3) from Inscription i")
	Double getTotalMontant3();
	
	@Query("select count(i.tranche3) from Inscription i where i.tranche3>0")
	Integer getTotalTranche3();
	
	@Query("select sum(i.tranche4) from Inscription i")
	Double getTotalMontant4();
	
	@Query("select count(i.tranche4) from Inscription i where i.tranche4>0")
	Integer getTotalTranche4();

	@Query("select sum(i.total) from Inscription i")
	Double getTotal();

}
