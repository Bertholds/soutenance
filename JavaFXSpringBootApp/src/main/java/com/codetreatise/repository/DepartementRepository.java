package com.codetreatise.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Departement;

@Repository
@Transactional
public interface DepartementRepository extends JpaRepository<Departement, Long> {

	@Query("select d from Departement d ")
	public ArrayList<Departement> loadAllDepartement();
	
	@Query("select d from Departement d where d.libele=(:libele)")
	public Departement findByLibele(@Param("libele")String libele);
	
	

}
