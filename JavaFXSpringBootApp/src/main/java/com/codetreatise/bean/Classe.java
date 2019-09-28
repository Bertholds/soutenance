package com.codetreatise.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "classe")
public class Classe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_classe;
	private String name;
	private String niveau;
	private String titulaire;
	private String chef;
	private String sous_chef;
	private String delegue1;
	private String delegue2;
	@Transient
	private Long total;

	@OneToMany(mappedBy = "classe", fetch = FetchType.EAGER)
	Collection<Etudiant> etudiant;

//	@ManyToMany(mappedBy = "classes")
//	private Collection<Matiere> matieres = new ArrayList<Matiere>();

	public Classe() {
		super();
	}
	
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
//	public Collection<Matiere> getMatieres() {
//		return matieres;
//	}
//
//	public void setMatieres(Collection<Matiere> matieres) {
//		this.matieres = matieres;
//	}

	public String getNom() {
		return name;
	}

	public void setNom(String nom) {
		this.name = nom;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getTitulaire() {
		return titulaire;
	}

	public void setTitulaire(String titulaire) {
		this.titulaire = titulaire;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}

	public String getSous_chef() {
		return sous_chef;
	}

	public void setSous_chef(String sous_chef) {
		this.sous_chef = sous_chef;
	}

	public String getDelegue1() {
		return delegue1;
	}

	public void setDelegue1(String delegue1) {
		this.delegue1 = delegue1;
	}

	public String getDelegue2() {
		return delegue2;
	}

	public void setDelegue2(String delegue2) {
		this.delegue2 = delegue2;
	}

	public Long getId_classe() {
		return id_classe;
	}

	public Collection<Etudiant> getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Collection<Etudiant> etudiant) {
		this.etudiant = etudiant;
	}

}
