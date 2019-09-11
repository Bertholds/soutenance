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
@Table(name = "preinscription")
public class Preinscription implements Serializable {

	private static final long serialVersionUID = 7949443794235623858L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_preinscription;
	private double montant;
	@Temporal(TemporalType.DATE)
	private Date date;

	@OneToOne
	private Etudiant etudiant;

	public Preinscription() {
		super();
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Long getId_preinscription() {
		return id_preinscription;
	}
	
	

}
