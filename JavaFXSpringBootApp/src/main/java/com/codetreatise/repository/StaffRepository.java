package com.codetreatise.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Departement;
import com.codetreatise.bean.Personel;

@Repository
@Transactional
public interface StaffRepository extends JpaRepository<Personel, Long> {

	@Query("select max(p.id) from Personel p")
	public Long findLastId();
	
	@Query("select p from Personel p where p.id=(:id)")
	public Personel findByLastId(@Param("id")Long id);
	
	@Query("select p from Personel p where p.departements =(:departement) ")
	public List<Personel> findOther(@Param("departement")Departement departement);
	
}
