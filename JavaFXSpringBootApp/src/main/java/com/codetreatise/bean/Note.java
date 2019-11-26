package com.codetreatise.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note implements Serializable {

	private static final long serialVersionUID = -7197399596230795009L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_note;
	private float normal;
	private float cc;
	private float note;
	private float moyenne;
	private int rang;
	private String appreciation;
	@OneToOne()
	private Etudiant etudiant;
	@OneToOne
	private Matiere matiere;
	@ManyToOne
	@JoinColumn(name = "id_classe")
	private Classe classe;

	public Note() {
		super();
	}

	public float getNormal() {
		return normal;
	}

	public void setNormal(float normal) {
		this.normal = normal;
	}

	public float getCc() {
		return cc;
	}

	public void setCc(float cc) {
		this.cc = cc;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	public float getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	public String getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Long getId_note() {
		return id_note;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

}
