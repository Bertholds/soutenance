package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Personel;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.service.impl.ClasseServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class ClasseEditDialogController implements Initializable {

	@FXML
	private TextField level;

	@FXML
	private TextField chef;

	@FXML
	private TextField delegue2;

	@FXML
	private TextField nom;

	@FXML
	private ComboBox<Object> leader;

	@FXML
	private TextField id;

	@FXML
	private TextField adjoint;

	@FXML
	private TextField delegue1;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private ClasseController classeController;

	@Autowired
	private ClasseServiceImpl classeServiceImpl;

	private ObservableList<Object> classeList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Personel> list = classeRepository.loadLeader();
		classeList.clear();
		for(int i=0; i<list.size(); i++) {
			Personel personel = list.get(i);
			classeList.add(personel.toString());
			 leader.setItems(classeList);
		}
	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {

			// show the dialog
			// d.show();
		}
	};

	@FXML
	public Classe handleCreateSClassClick(ActionEvent event) {
		Classe newClasse = null;
		if (isInputValid()) {
			if (classeController.isEditButtonClick()) {
				Classe classe = classeRepository.findOne(getId());
				// classroom = classeRepository.findByNom(getClasseRoom());
				classe.setNiveau(getNiveau());
				classe.setChef(getChef());
				classe.setSous_chef(getAdjoint());
				classe.setDelegue1(getDelegue1());
				classe.setDelegue2(getDelegue2());
				classe.setTitulaire(getLeader());
				classe.setNom(getNom());
				newClasse = classeServiceImpl.update(classe);
				updateAlert(newClasse);
				clearFields();
				classeController.loadClasseDetailWhenCreateUpdate();
				classeController.SetIsEditButtonClick(false);
				return newClasse;
			} else {
				// classroom = classeRepository.findByNom(getClasseRoom());
				Classe classe = new Classe();
				classe.setNiveau(getNiveau());
				classe.setChef(getChef());
				classe.setSous_chef(getAdjoint());
				classe.setDelegue1(getDelegue1());
				classe.setDelegue2(getDelegue2());
				classe.setTitulaire(getLeader());
				classe.setNom(getNom());
				newClasse = classeRepository.save(classe);
				saveAlert(newClasse);
				clearFields();
				classeController.loadClasseDetailWhenCreateUpdate();
				return newClasse;
			}
		}
		return newClasse;
	}

	@FXML
	void handleCancelClick(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
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

	private String getNiveau() {
		return level.getText();
	}

	private String getChef() {
		return chef.getText();
	}

	private String getAdjoint() {
		return adjoint.getText();
	}

	private String getDelegue1() {
		return delegue1.getText();
	}

	private String getDelegue2() {
		return delegue2.getText();
	}

	private String getLeader() {
		return  leader.getSelectionModel().getSelectedItem().toString();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getNom() == null || getNom().length() == 0) {
			errorMessage += "No valid  name!\n";
		}
		if (getNiveau() == null || getNiveau().length() == 0) {
			errorMessage += "No valid level!\n";
		}
		if (getChef() == null || getChef().length() == 0) {
			errorMessage += "No valid chief!\n";
		}

		if (getAdjoint() == null || getAdjoint().length() == 0) {
			errorMessage += "No valid adjunct chief!\n";
		}

		if (getDelegue1() == null || getDelegue1().length() == 0) {
			errorMessage += "No valid delegate n1!\n";
		}

		if (getDelegue2() == null || getDelegue2().length() == 0) {
			errorMessage += "No valid delegate n2!\n";
		}

		if ( /* !DateUtil.validDate( birthdayField.getText() */ getLeader() == null || getLeader().length() == 0) {
			errorMessage += "No valid leader!";// Use the format dd.mm.yyyy!\n";
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

	public Classe showClasseDetails(Classe selectedClasse) {
		if (selectedClasse != null) {
			id.setText(selectedClasse.getId_classe().toString());
			nom.setText(selectedClasse.getNom());
			level.setText(selectedClasse.getNiveau());
			chef.setText(selectedClasse.getChef());
			adjoint.setText(selectedClasse.getSous_chef());
			delegue1.setText(selectedClasse.getDelegue1());
			delegue2.setText(selectedClasse.getDelegue2());
			leader.setValue(selectedClasse.getTitulaire());

		} else {

		}
		return selectedClasse;
	}

	private void clearFields() {
		id.setText(null);
		nom.clear();
		level.clear();
		chef.clear();
		adjoint.clear();
		delegue1.clear();
		delegue2.clear();
		leader.getSelectionModel().clearSelection();
	}

	private void saveAlert(Classe classe) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Class saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Class " + classe.getNom() + " of " + classe.getNiveau()
				+ " has been created and whith leader \n" + classe.getTitulaire() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Classe classe) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Class updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Class " + classe.getNom() + " of " + classe.getNiveau()
				+ " has been created and whith leader \n" + classe.getTitulaire() + ".");
		alert.showAndWait();
	}
}