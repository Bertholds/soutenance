package com.codetreatise.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
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
@Table(name = "departement")
public class Departement implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id_departement", updatable = false)
	private Long id_departement;
	private String libele;
	private String chef;
	@Transient
	private int totalPoste;
	@ManyToMany(mappedBy="departements")
	private Collection<Personel>personels;
	@OneToMany(mappedBy="departement", fetch=FetchType.EAGER)
	private Collection<Poste>postes;

	public Departement() {
		super();
	}

	public Collection<Personel> getPersonels() {
		return personels;
	}

	public void setPersonels(Collection<Personel> personels) {
		this.personels = personels;
	}

	public Collection<Poste> getPostes() {
		return postes;
	}

	public void setPostes(Collection<Poste> postes) {
		this.postes = postes;
	}

	public String getLibele() {
		return libele;
	}

	public void setLibele(String libele) {
		this.libele = libele;
	}

	public Long getId_departement() {
		return id_departement;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}

	public int getTotalPoste() {
		return totalPoste;
	}
	
	public void setTotalPoste(int totalPoste) {
		this.totalPoste = totalPoste;
	}

	@Override
	public String toString() {
		return libele ;
	}
}
