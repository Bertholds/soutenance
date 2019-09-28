package com.codetreatise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codetreatise.bean.Utilisateur;

public interface UserRepository extends JpaRepository<Utilisateur, Long> {

	@Query("select u from Utilisateur u where u.login=(:login) and u.pass=(:pass) ")
	public Utilisateur authentification(@Param("login")String login, @Param("pass")String pass);
	
	@Query("select u from Utilisateur u where u.acces=(:acces) ")
	public List<Utilisateur> findByAcces(@Param("acces")String acces);

	public Utilisateur findByLogin(String login);
}
