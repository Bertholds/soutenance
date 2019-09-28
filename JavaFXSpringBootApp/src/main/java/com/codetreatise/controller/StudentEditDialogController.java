package com.codetreatise.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Etudiant;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.impl.EtudiantServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

@Controller
public class StudentEditDialogController implements Initializable {

	@FXML
	private TextField nom;

	@FXML
	private TextField prenom;

	@FXML
	private RadioButton radioM;

	@FXML
	private ToggleGroup radioGroup;

	@FXML
	private RadioButton radioF;

	@FXML
	private TextField phone;

	@FXML
	private ComboBox<String> classe;

	@FXML
	private TextField matricule;

	@FXML
	private TextField parent;

	@FXML
	private DatePicker naissance;

	@FXML
	private TextField id;

	private Classe classroom;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private StudentController studentController;

	@Autowired
	private EtudiantServiceImpl etudiantServiceImpl;

	private String patern = "dd/MM/YY";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patern);

	private ObservableList<String> classeList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> list = classeRepository.loadAllClass();
		classeList.clear();
		classeList.addAll(list);

		classe.setItems(classeList);
	}

	private String aleatoireNumber() {

		Random random = new Random();
		String alphabet = "ABC8CVB05MQ2TGB9X6R3P14DGD7";
		int longueur = alphabet.length();
		String chaine = "GL";
		for (int i = 0; i < 20; i++) {
			int k = random.nextInt(longueur);
			chaine += alphabet.charAt(k);
		}
		System.out.println(chaine);
		// System.out.println( Math.round(random.nextFloat()*Math.pow(10, 12)));
		// System.out.println(10000000*random.nextDouble());
		// System.out.println((random.nextInt(1000000) + 1000000000) *
		// random.nextInt(900)+100);
		return chaine;
	}

	@FXML
	public Etudiant handleCreateStudentClick(ActionEvent event) {
		Etudiant newEtudiant = null;
		if (isInputValid()) {
			if (studentController.isEditButtonClick()) {
				Etudiant etudiant = studentRepository.findOne(getId());
				classroom = classeRepository.findByNom(getClasseRoom());
				etudiant.setClasse(classroom);
				etudiant.setclasseNom(classroom.getNom());
				etudiant.setMatricule(getMatricule());
				etudiant.setNaissance(getNaissance());
				etudiant.setNom(getNom());
				etudiant.setParente(getParent());
				etudiant.setPrenom(getPrenom());
				etudiant.setSexe(getSexe());
				etudiant.setTelephone(getPhone());
				newEtudiant = etudiantServiceImpl.update(etudiant);
				updateAlert(newEtudiant);
				clearFields();
				studentController.loadStudentDetailWhenCreate();
				studentController.setIsEditButtonClick(false);
				return newEtudiant;
			} else {
				classroom = classeRepository.findByNom(getClasseRoom());
				Etudiant etudiant = new Etudiant();
				etudiant.setMatricule(getMatricule());
				etudiant.setNom(getNom());
				etudiant.setNaissance(getNaissance());
				etudiant.setSexe(getSexe());
				etudiant.setParente(getParent());
				etudiant.setClasse(classroom);
				etudiant.setclasseNom(classroom.getNom());
				etudiant.setTelephone(getPhone());
				etudiant.setPrenom(getPrenom());
				newEtudiant = studentRepository.save(etudiant);
				saveAlert(newEtudiant);
				clearFields();
				return newEtudiant;
			}
		}
		return newEtudiant;
	}

	@FXML
	void handleCancelClick(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	void handleGenerateRegisterClick(ActionEvent event) {
		matricule.setText(aleatoireNumber());
	}

	@FXML
	void handleClearFieldClick(ActionEvent event) {
		clearFields();
	}

	private Long getId() {
		return Long.parseLong(id.getText());
	}

	private String getNom() {
		return nom.getText();
	}

	private String getPrenom() {
		return prenom.getText();
	}

	private String getPhone() {
		return phone.getText();
	}

	private String getClasseRoom() {
		return classe.getSelectionModel().getSelectedItem();
	}

	private String getMatricule() {
		return matricule.getText();
	}

	private String getParent() {
		return parent.getText();
	}

	private String getSexe() {
		return radioM.isSelected() ? radioM.getText() : radioF.getText();
	}

	private String getSexeTitle(String gender) {
		return (gender.equals("Male")) ? "his" : "her";
	}

	private String getNaissance() {
		return naissance.getEditor().getText();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getNom() == null || getNom().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (getPrenom() == null || getPrenom().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if (getPhone() == null || getPhone().length() == 0) {
			errorMessage += "No valid Phone number!\n";
		}

		if (getClasseRoom() == null || getClasseRoom().length() == 0) {
			errorMessage += "No valid class!\n";
		}

		if (getMatricule() == null || getMatricule().length() == 0) {
			errorMessage += "No valid register!\n";
		}

		if (getParent() == null || getParent().length() == 0) {
			errorMessage += "No valid relationship!\n";
		}

		if (getNaissance() == null || getNaissance().length() == 0) {
			errorMessage += "No valid birthday!\n";
		}

		if ( /* !DateUtil.validDate( birthdayField.getText() */ getSexe() == null || getSexe().length() == 0) {
			errorMessage += "No valid sex!";// Use the format dd.mm.yyyy!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			// alert.initOwner( dialogStage );
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	public Etudiant showPersonDetails(Etudiant etudiant) {
		if (etudiant != null) {
			// Fill the labels with info from the person object.
			id.setText(etudiant.getId().toString());
			nom.setText(etudiant.getNom());
			prenom.setText(etudiant.getPrenom());
			classe.setValue(etudiant.getClasse().getNom());
			phone.setText(etudiant.getTelephone());
			parent.setText(etudiant.getParente());
			naissance.getEditor().setText(etudiant.getNaissance());
			if (etudiant.getSexe().equals("Masculin"))
				radioM.setSelected(true);
			else
				radioF.setSelected(true);
			matricule.setText(etudiant.getMatricule());

		}
		return etudiant;
	}

	private void clearFields() {
		matricule.setText(null);
		nom.clear();
		prenom.clear();
		naissance.getEditor().clear();
		radioM.setSelected(false);
		radioF.setSelected(false);
		phone.clear();
		classe.getSelectionModel().clearSelection();
		parent.clear();
		studentController.setIsEditButtonClick(false);
	}

	private void saveAlert(Etudiant etudiant) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Student saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Student " + etudiant.getNom() + " " + etudiant.getPrenom()
				+ " has been created and \n" + getSexeTitle(etudiant.getSexe()) + " id is " + etudiant.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Etudiant etudiant) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Student updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Student " + etudiant.getNom() + " " + etudiant.getPrenom() + " has been update and \n"
				+ getSexeTitle(etudiant.getSexe()) + " id is " + etudiant.getId() + ".");
		alert.showAndWait();
	}
}
