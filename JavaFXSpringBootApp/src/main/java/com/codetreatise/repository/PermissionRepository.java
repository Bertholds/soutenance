package com.codetreatise.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Permission;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("select count(p.id_permission) from Permission p where p.personel.id=(:id)")
	public int getTotalPermissionPersonnel(@Param("id")Long id);
    
    @Query("select count(p.id_permission) from Permission p where p.etudiant.id=(:id)")
	public int getTotalPermissionEtudiant(@Param("id")Long id);
    
    @Query("select sum(p.duree) from Permission p where p.personel.id=(:id)")
	public int getTotalDureePersonel(@Param("id")Long id);
    
    @Query("select sum(p.duree) from Permission p where p.etudiant.id=(:id)")
   	public int getTotalDureeEtudiant(@Param("id")Long id);
}
