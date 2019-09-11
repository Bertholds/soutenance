package com.codetreatise.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tache")
public class Tache implements Serializable {

	private static final long serialVersionUID = 4737438014746121541L;

	@GeneratedValue(strategy=GenerationType.IDENTITY) @Id
	private Long id_tache;
	private String libele;

	public Tache() {
		super();
	}

	public String getLibele() {
		return libele;
	}

	public void setLibele(String libele) {
		this.libele = libele;
	}

	public Long getId_tache() {
		return id_tache;
	}

	@Override
	public String toString() {
		return libele ;
	}

}
