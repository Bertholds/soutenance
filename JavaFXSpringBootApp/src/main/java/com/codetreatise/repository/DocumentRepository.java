package com.codetreatise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codetreatise.bean.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
	
	@Query("select d from Document d where d.categorie.libele=(:libele)")
    List<Document> findByCategory(@Param("libele")String libele);
	
	@Query("select d from Document d where d.status='Non disponible'")
    List<Document> findByStatus();
	
	@Query("select max(d.id_document) from Document d ")
    Long findMaxIdDocument();
}
