package com.codetreatise.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "inscription")
public class Inscription implements Serializable {

	private static final long serialVersionUID = 5388290635347637322L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_inscription;
	private double tranche1;
	private double tranche2;
	private double tranche3;
	private double tranche4;
	private double total;
	@Temporal(TemporalType.DATE)
	private Date dateT1;
	@Temporal(TemporalType.DATE)
	private Date dateT2;
	@Temporal(TemporalType.DATE)
	private Date dateT3;
	@Temporal(TemporalType.DATE)
	private Date dateT4;
	@OneToOne
	private Etudiant etudiant;

	private Long id_etudiant;
	private String nom;
	private String prenom;
	private String classe;

	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public Long getId_etudiant() {
		return id_etudiant;
	}

	public void setId_etudiant(Long id_etudiant) {
		this.id_etudiant = id_etudiant;
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
	
	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Inscription() {
		super();
	}

	public double getTranche1() {
		return tranche1;
	}

	public void setTranche1(double tranche1) {
		this.tranche1 = tranche1;
	}

	public double getTranche2() {
		return tranche2;
	}

	public void setTranche2(double tranche2) {
		this.tranche2 = tranche2;
	}

	public double getTranche3() {
		return tranche3;
	}

	public void setTranche3(double tranche3) {
		this.tranche3 = tranche3;
	}

	public double getTranche4() {
		return tranche4;
	}

	public void setTranche4(double tranche4) {
		this.tranche4 = tranche4;
	}

	public Date getDateT1() {
		return dateT1;
	}

	public void setDateT1(Date dateT1) {
		this.dateT1 = dateT1;
	}

	public Date getDateT2() {
		return dateT2;
	}

	public void setDateT2(Date dateT2) {
		this.dateT2 = dateT2;
	}

	public Date getDateT3() {
		return dateT3;
	}

	public void setDateT3(Date dateT3) {
		this.dateT3 = dateT3;
	}

	public Date getDateT4() {
		return dateT4;
	}

	public void setDateT4(Date dateT4) {
		this.dateT4 = dateT4;
	}

	public Long getId_inscription() {
		return id_inscription;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

}
