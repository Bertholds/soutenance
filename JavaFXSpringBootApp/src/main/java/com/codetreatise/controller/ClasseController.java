package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.ClasseRepository;
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
import javafx.scene.input.KeyEvent;

@Controller
public class ClasseController implements Initializable {
	@FXML
	private TableView<Classe> classeTable;

	@FXML
	private TableColumn<Classe, String> ClassNameColumn;

	@FXML
	private TableColumn<Classe, String> LeaderNameColumn;

	@FXML
	private ComboBox<String> filtre;

	@FXML
	private Button ok;

	@FXML
	private TextField recherche;

	@FXML
	private Label nom;

	@FXML
	private Label niveau;

	@FXML
	private Label chef;

	@FXML
	private Label sous_chef;

	@FXML
	private Label delegue1;

	@FXML
	private Label delegue2;

	@FXML
	private Label proffesseur;

	@FXML
	private Label id;

	@FXML
	private Label total;

	private boolean editbuttonclic = false;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private ClasseEditDialogController dialog;

	private ObservableList<Classe> classeList = FXCollections.observableArrayList();
	private ObservableList<String> filtrage = FXCollections.observableArrayList();

	@FXML
	private void handleDeleteClasse(ActionEvent event) {

		int selectedIndex = classeTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			classeTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner( );
			alert.setTitle("No Selection");
			alert.setHeaderText("No class Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleEditClasse(ActionEvent event) {

		Classe selectedClasse = classeTable.getSelectionModel().getSelectedItem();
		if (selectedClasse != null) {
			stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.CLASSEEDITDIALOGCONTROLLER);
			dialog.showClasseDetails(selectedClasse);
			editbuttonclic = true;
		}

		else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			// alert.initOwner( mainApp.getPrimaryStage() );
			alert.setTitle("No Selection");
			alert.setHeaderText("No Class Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleFiltreClick(ActionEvent event) {
		loadClasseDetail();
	}

	@FXML
	private void filteredTableWhenPressed(KeyEvent event) {

	}

	@FXML
	public void handleNewClasse(ActionEvent event) {
		stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.CLASSEEDITDIALOGCONTROLLER);
	}

	public boolean isEditButtonClick() {
		return editbuttonclic;
	}

	public void SetIsEditButtonClick(boolean value) {
		this.editbuttonclic = value;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFiltre();
		setColumProperties();

		classeTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Classe>() {

			@Override
			public void changed(ObservableValue<? extends Classe> observable, Classe oldValue, Classe newValue) {
				showClasseDetails(newValue);
			}
		});

	}

	private void setFiltre() {
		ArrayList<String> list2 = classeRepository.loadAllNiveau();
		filtrage.clear();
		filtrage.addAll(list2);
		filtrage.add("All class");
		filtre.setItems(filtrage);
	}

	private void setColumProperties() {
		ClassNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		LeaderNameColumn.setCellValueFactory(new PropertyValueFactory<>("titulaire"));
	}

	public void loadClasseDetail() {
		classeList.clear();
		if (getFiltre() == "All class") {
			classeList.addAll(classeRepository.findAll());
			classeTable.setItems(classeList);
		} else {
			classeList.addAll(classeRepository.findClasse(getFiltre()));
		}

		classeTable.setItems(classeList);
	}

	private String getFiltre() {
		return filtre.getSelectionModel().getSelectedItem();
	}

	@FXML
	private void filteredTable(KeyEvent event) {
		FilteredList<Classe> filteredclasse = new FilteredList<Classe>(classeList, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredclasse.setPredicate((Predicate<? super Classe>) classe -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (classe.getId_classe().toString().contains(newValueFilter)) {
						return true;
					} else if (classe.getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Classe> sortedList = new SortedList<Classe>(filteredclasse);
		sortedList.comparatorProperty().bind(classeTable.comparatorProperty());
		classeTable.setItems(sortedList);
	}

	private void showClasseDetails(Classe classe) {
		if (classe != null) {
			classe.setTotal((long) classe.getEtudiant().size());
			nom.setText(classe.getNom());
			chef.setText(classe.getChef());
			delegue1.setText(classe.getDelegue1());
			delegue2.setText(classe.getDelegue2());
			id.setText(classe.getId_classe().toString());
			sous_chef.setText(classe.getSous_chef());
			niveau.setText(classe.getNiveau());
			proffesseur.setText(classe.getTitulaire());
			total.setText(classe.getTotal().toString());
		} else {
			// Person is null, remove all the text.
			nom.setText("");
			chef.setText("");
			delegue1.setText("");  
			delegue2.setText("");
			id.setText("");
			sous_chef.setText("");
			niveau.setText("");
			proffesseur.setText("");
			total.setText("");
		}
	}

	public void loadClasseDetailWhenCreateUpdate() {
		Long id = classeRepository.findLastId();
		classeList.clear();
		classeList.addAll(classeRepository.findByLastId(id));
		classeTable.setItems(classeList);
	}

}
