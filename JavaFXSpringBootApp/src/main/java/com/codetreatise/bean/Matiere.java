package com.codetreatise.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="matiere")
public class Matiere implements Serializable {

	private static final long serialVersionUID = 3895067429721912194L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_matiere;
	private String nom;
	private String niveau;
	private int coefficient;
	private int semestre;
	private String supervisor;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "matiere_classe", joinColumns = @JoinColumn(name = "id_matiere"), inverseJoinColumns = @JoinColumn(name = "id_classe"))
	private List<Classe> classes = new ArrayList<Classe>();

	public Matiere() {
		super();
	}
	
	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public Long getId_matiere() {
		return id_matiere;
	}

}
