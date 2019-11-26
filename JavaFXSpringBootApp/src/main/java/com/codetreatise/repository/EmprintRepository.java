package com.codetreatise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Document;
import com.codetreatise.bean.Emprint;
import java.lang.String;

@Repository
public interface EmprintRepository extends JpaRepository<Emprint, Long> {
	
	@Query("select e from Emprint e where e.document.categorie.libele=(:libele)")
      public List<Emprint> findByCategory(@Param("libele")String libele);
	
	@Query("select e from Emprint e where e.status='Non disponible' order by e.id_emprint desc ")
	public List<Emprint> findByStatus();
	
	@Query("select e from Emprint e where e.status='Disponible' order by e.id_emprint desc ")
	public List<Emprint> findByStatus2();
	
	@Query("select e from Emprint e where e.document= (:document)")
	public List<Emprint> findByDocumentId(@Param("document")Document document);
	
	@Query("select e from Emprint e order by e.id_emprint desc ")
	public List<Emprint> getAll();

}
