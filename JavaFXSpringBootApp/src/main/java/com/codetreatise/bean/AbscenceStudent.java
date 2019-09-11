/**
 * 
 */
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

/**
 * @author Berthold
 *
 */
@Entity
@Table(name = "abscenceStudent")
public class AbscenceStudent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9132196313306886615L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_abscence;
	private int justifier;
	private int quantite;
	private int mois;
	@Temporal(TemporalType.DATE)
	private Date dates;
	@OneToOne
	private Etudiant etudiant;

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public AbscenceStudent() {
		super();
	}

	public int getJustifier() {
		return justifier;
	}

	public void setJustifier(int justifier) {
		this.justifier = justifier;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Date getDates() {
		return dates;
	}

	public void setDates(Date date) {
		this.dates = date;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Long getId_abscence() {
		return id_abscence;
	}
	
}
