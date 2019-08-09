package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Etudiant;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.view.FxmlView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class StudentController implements Initializable {

	@FXML
	private TableView<Etudiant> studentTable;

	@FXML
	private TableColumn<Etudiant, String> firstNameColumn;

	@FXML
	private TableColumn<Etudiant, String> lastNameColumn;

	@FXML
	private ComboBox<String> filtre;

	@FXML
	private Button ok;

	@FXML
	private TextField recherche;

	@FXML
	private Label matricule;

	@FXML
	private Label nom;

	@FXML
	private Label prenom;

	@FXML
	private Label sexe;

	@FXML
	private Label naissance;

	@FXML
	private Label phone;

	@FXML
	private Label classe;

	@FXML
	private Label parent;

	@FXML
	private Label id;

	private  boolean editbuttonclic = false;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private StudentEditDialogController dialog;

	private ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
	private ObservableList<String> filtrage = FXCollections.observableArrayList();

	@FXML
	private void handleDeletePerson(ActionEvent event) {

		int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			studentTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner( );
			alert.setTitle("No Selection");
			alert.setHeaderText("No Student Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleEditPerson(ActionEvent event) {

		Etudiant selectedPerson = studentTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.STUDENTEDITDIALOG);
			dialog.showPersonDetails(selectedPerson);
			editbuttonclic = true;
		}

		else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner( mainApp.getPrimaryStage() );
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleFiltreClick(ActionEvent event) {
		loadStudentDetail();
	}

	@FXML
	public void handleNewPerson(ActionEvent event) {
		stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.STUDENTEDITDIALOG);
	}

	public boolean isEditButtonClick() {
		return editbuttonclic;
	}
	public void setIsEditButtonClick(boolean value) {
		 this.editbuttonclic = value;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFiltre();
		setColumProperties();
		// showPersonDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		studentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Etudiant>() {
			@Override
			public void changed(ObservableValue<? extends Etudiant> observable, Etudiant oldValue, Etudiant newValue) {
				showPersonDetails(newValue);
			}
		});
		
	}

	private void setFiltre() {
		ArrayList<String> list = classeRepository.loadAllClass();
		ArrayList<String> list2 = classeRepository.loadAllNiveau();
		filtrage.clear();
		filtrage.addAll(list);
		filtrage.addAll(list2);
		filtrage.add("All student");
		filtre.setItems(filtrage);
	}

	private void setColumProperties() {
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	}

	private String getFiltre() {
		return filtre.getSelectionModel().getSelectedItem();
	}
	
	public void loadStudentDetail() {
		etudiantList.clear();
		if (getFiltre() == "All student") {
			etudiantList.addAll(studentRepository.findAll());
			studentTable.setItems(etudiantList);
		} else {
			etudiantList.addAll(studentRepository.findClasse(getFiltre()));
			studentTable.setItems(etudiantList);
		}
	}
	
	public void loadStudentDetailWhenCreate() {
		Long id = studentRepository.findLastId();
		etudiantList.clear();
			etudiantList.addAll(studentRepository.findByLastId(id));
			studentTable.setItems(etudiantList);
	}
	@FXML
	private void filteredTable() {
		FilteredList<Etudiant>filteredetudiants =new FilteredList<Etudiant>(etudiantList, e->true);
		recherche.setOnKeyReleased(e->{
			recherche.textProperty().addListener((observableValue, oldValue, newValue)->{
				filteredetudiants.setPredicate((Predicate<? super Etudiant>) etudiant->{
					if(newValue==null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if(etudiant.getId().toString().contains(newValueFilter)) {
						return true;
					}
					else if (etudiant.getMatricule().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					else if (etudiant.getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					else if (etudiant.getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});
		
		SortedList<Etudiant>sortedList = new SortedList<Etudiant>(filteredetudiants);
		sortedList.comparatorProperty().bind(studentTable.comparatorProperty());
		studentTable.setItems(sortedList);
	}
	

	private void showPersonDetails(Etudiant etudiant) {
		if (etudiant != null) {
			// Fill the labels with info from the person object.
			nom.setText(etudiant.getNom());
			prenom.setText(etudiant.getPrenom());
			classe.setText(etudiant.getClasse().getNom());
			phone.setText(etudiant.getTelephone());
			parent.setText(etudiant.getParente());
			id.setText(etudiant.getId().toString());
			naissance.setText(etudiant.getNaissance().toString());
			sexe.setText(etudiant.getSexe());
			matricule.setText(etudiant.getMatricule());
		} else {
			// Person is null, remove all the text.
			nom.setText("");
			prenom.setText("");
			classe.setText("");
			phone.setText("");
			parent.setText("");
			id.setText("");
			naissance.setText("");
			sexe.setText("");
			matricule.setText("");
		}
	}

}
