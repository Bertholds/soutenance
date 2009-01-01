package com.codetreatise.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "personnel")
public class Personel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private String nom;
	private String prenom;
	private String sexe;
	private String telephone;
	private String fonction;
	private String matricule;
	private String email;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "personel_poste", joinColumns = @JoinColumn(name = "id_personel"), inverseJoinColumns = @JoinColumn(name = "id_poste"))
	private List<Poste> postes = new ArrayList<Poste>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "personel_departement", joinColumns = @JoinColumn(name = "id_personel"), inverseJoinColumns = @JoinColumn(name = "id_departement"))
	private List<Departement> departements = new ArrayList<Departement>();

	public List<Poste> getPostes() {
		return postes;
	}

	public void addPostes(List<Poste> poste) {
		this.postes = poste;
	}

	public List<Departement> getDepartements() {
		return departements;
	}

	public void addDepartements(List<Departement> departements) {
		this.departements = departements;
	}

	public Personel() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return nom + " " + prenom;
	}

}
