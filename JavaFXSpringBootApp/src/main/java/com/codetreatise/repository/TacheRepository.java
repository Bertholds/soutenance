package com.codetreatise.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Tache;

@Repository
@Transactional
public interface TacheRepository extends JpaRepository<Tache, Long> {

	@Query("update Tache set libele=(:libele) where id_tache=(:id)")
	@Modifying
	public void updateTask(@Param("libele")String libele, @Param("id")Long id);
	
	@Query("select t from Tache t where t.libele=(:libele)")
	public Tache findByLibele(@Param("libele")String libele);
}
