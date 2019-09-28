package com.codetreatise.bean;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="matiere")
public class Matiere implements Serializable {

	private static final long serialVersionUID = 3895067429721912194L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_matiere;
	private String nom;
	private String classe;
	@Transient
	private Map<String, Integer> maemoire;
	private String coefficient;
	private int semestre;
	private String supervisor;
//	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(name = "matiere_classe", joinColumns = @JoinColumn(name = "id_matiere"), inverseJoinColumns = @JoinColumn(name = "id_classe"))
//	private List<Classe> classes = new ArrayList<Classe>();

	public Matiere() {
		super();
	}
	
//	public List<Classe> getClasses() {
//		return classes;
//	}
//
//	public void setClasses(List<Classe> classes) {
//		this.classes = classes;
//	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
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

	public Map<String, Integer> getMaemoire() {
		return maemoire;
	}

	public void setMaemoire(Map<String, Integer> maemoire) {
		this.maemoire = maemoire;
	}

}
