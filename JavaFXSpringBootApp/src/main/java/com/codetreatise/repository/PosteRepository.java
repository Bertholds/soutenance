package com.codetreatise.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Departement;
import com.codetreatise.bean.Poste;

@Repository
@Transactional
public interface PosteRepository extends JpaRepository<Poste, Long> {

	@Query("select distinct p from Poste p")
	public ArrayList<Poste> loadAllPoste();
	
	@Query("select p from Poste p where p.libele=(:libele)")
	public Poste findPoste(@Param("libele")String libele);
	
	public List<Poste> findByDepartement(Departement departement);
	
	@Query("select p from Poste p where p.libele=(:libele)")
	public Poste findByLibele(@Param("libele")String libele);
}
