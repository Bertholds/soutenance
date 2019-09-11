package com.codetreatise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.AbscenceStudent;

@Repository
public interface AbscenceStudentRepository extends JpaRepository<AbscenceStudent, Long> {

	
	@Query("select sum(a.quantite) from AbscenceStudent a where a.etudiant.id=(:id)")
    public int getTotalSum(@Param("id")Long id);
	
	@Query("select sum(a.quantite) from AbscenceStudent a where a.etudiant.id=(:id) and a.mois=(:mois)")
    public int getTotalMoisSum(@Param("id")Long id, @Param("mois")int mois);
	
	@Query("select sum(a.justifier) from AbscenceStudent a where a.etudiant.id=(:id) and a.mois=(:mois)")
    public int getTotalJustifierMoisSum(@Param("id")Long id, @Param("mois")int mois);
	
	@Query("select sum(a.justifier) from AbscenceStudent a where a.etudiant.id=(:id)")
    public int getTotalJustifierAnnuelsSum(@Param("id")Long id);
	

	
	@Query("Select a from AbscenceStudent a where a.etudiant.classe.name=(:name)")
	public List<AbscenceStudent> findByClasse(@Param("name")String name);

}
//select max(`id_abscence`)
//, min(`dates`)
//, sum(`justifier`)
//, sum(`quantite`)
//, `etudiant_id`
//from abscence_student
//group by `etudiant_id` where `dates` between date1 and date2