package com.codetreatise.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "poste")
public class Poste implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poste", updatable = false, nullable = false)
	private Long id_poste;
	private String libele;
	
	@ManyToMany(mappedBy="postes", fetch=FetchType.EAGER)
	private Collection<Personel>personels;
	@ManyToOne
	@JoinColumn(name="id_departement")
	private Departement departement;

	public Collection<Personel> getPersonels() {
		return personels;
	}

	public void setPersonels(Collection<Personel> personels) {
		this.personels = personels;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public Poste() {
		super();
	}

	public String getLibele() {
		return libele;
	}

	public void setLibele(String libele) {
		this.libele = libele;
	}

	public Long getId_poste() {
		return id_poste;
	}

	@Override
	public String toString() {
		return  libele ;
	}
	
	

}

