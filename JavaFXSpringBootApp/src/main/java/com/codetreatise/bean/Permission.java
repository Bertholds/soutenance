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
@Table(name="permission")
public class Permission implements Serializable {

	private static final long serialVersionUID = 6008122734737307156L;

	/**
	 * 
	 */
	public Permission() {
		super();
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_permission;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String motif;
	private int duree;
	private boolean status;
	@OneToOne
	private Etudiant etudiant;
	@OneToOne
	private Personel personel;

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Personel getPersonel() {
		return personel;
	}
	public void setPersonel(Personel personel) {
		this.personel = personel;
	}
	public Long getId_permission() {
		return id_permission;
	}
	
	
}
