package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Coefficient;
import com.codetreatise.bean.Matiere;

public interface CoefficientRepository extends JpaRepository<Coefficient, Long> {
	
    @Query("select c from Coefficient c where c.classe=(:classe) and c.matiere=(:matiere)")  
	public Coefficient findByCLasseAndMatiere(@Param("classe")Classe classe, @Param("matiere")Matiere matiere);
    
    @Query("select sum(c.coefficient) from Coefficient c where c.classe=(:classe) ")  
   	public int findByCLasse(@Param("classe")Classe classe);
}
