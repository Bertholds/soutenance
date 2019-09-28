package com.codetreatise.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pointage")
public class Pointage implements Serializable {

	private static final long serialVersionUID = 314908906925300101L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id_pointage;
	private String date;
	private String heurArriver;
	private String heurDepart;
	private float  heureTravail;
	@OneToOne
	private Tache tache;
	@OneToOne
	private Utilisateur utilisateur;
	@OneToOne
	private Personel personel;

	public Pointage() {
		super();
	}
	
	public float getHeureTravail() {
		return heureTravail;
	}

	public void setHeureTravail(float heureTravail) {
		this.heureTravail = heureTravail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeurArriver() {
		return heurArriver;
	}

	public void setHeurArriver(String heurArriver) {
		this.heurArriver = heurArriver;
	}

	public String getHeurDepart() {
		return heurDepart;
	}

	public void setHeurDepart(String heurDepart) {
		this.heurDepart = heurDepart;
	}

	public Tache getTache() {
		return tache;
	}

	public void setTache(Tache tache) {
		this.tache = tache;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Long getId_pointage() {
		return id_pointage;
	}

	public Personel getPersonel() {
		return personel;
	}

	public void setPersonel(Personel personel) {
		this.personel = personel;
	}

}
