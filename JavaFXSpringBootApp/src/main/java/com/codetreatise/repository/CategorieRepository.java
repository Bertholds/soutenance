package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codetreatise.bean.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
     @Query("select c from Categorie c where c.libele=(:libele)")	
     public Categorie findByLibele(@Param("libele")String libele);
     
     @Query("delete Categorie c where c.libele=(:libele)")	
     public void del(@Param("libele")String libele);
}
