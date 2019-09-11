package com.codetreatise.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Pointage;

@Repository
@Transactional
public interface PointageRepository extends JpaRepository<Pointage, Long> {
    @Query("select p.heureTravail from Pointage p where p.personel.id=(:id) and p.date like(:date)")
	public List<String> loadAllHeurTravailFilterByMonthForOnePerson(@Param("id")Long id, @Param("date")String date);
}
