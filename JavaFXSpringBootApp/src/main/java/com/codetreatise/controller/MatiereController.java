package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Matiere;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.MatiereRepository;
import com.codetreatise.service.MethodUtilitaire;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

@Controller
public class MatiereController implements Initializable {
	@FXML
	private TableView<Matiere> subjectTable;
	@FXML
	private TableColumn<?, ?> subjectNameColumn;
	@FXML
	private TableColumn<?, ?> subjectLevelColumn;
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
	private Label coefficient;
	@FXML
	private Label supervisor;
	@FXML
	private Label semester;
	@FXML
	private Label id;
	
	private boolean editbuttonclic = false;
	
	@Autowired
	@Lazy
	private StageManager stageManager;
	
	@Autowired
	private MatiereRepository matiereRepository;
	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private MatiereEditDialogController dialog;

	private ObservableList<Matiere> matiereList = FXCollections.observableArrayList();
	ObservableList<Matiere> ol = FXCollections.observableArrayList();
	private ObservableList<String> filtrage = FXCollections.observableArrayList();
	
	public boolean isEditButtonClick() {
		return editbuttonclic;
	}

	public void SetIsEditButtonClick(boolean value) {
		this.editbuttonclic = value;
	}
	
	@FXML
	public void handleFiltreClick(ActionEvent event) {
		loadMatiereDetail();
	}
	// Event Listener on TextField[#recherche].onKeyReleased
	@FXML
	public void filteredTable(KeyEvent event) {
		FilteredList<Matiere> filteredmatiere = new FilteredList<Matiere>(matiereList, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredmatiere.setPredicate((Predicate<? super Matiere>) matiere -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (matiere.getId_matiere().toString().contains(newValueFilter)) {
						return true;
					} else if (matiere.getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Matiere> sortedList = new SortedList<Matiere>(filteredmatiere);
		sortedList.comparatorProperty().bind(subjectTable.comparatorProperty());
		subjectTable.setItems(sortedList);
	}
	
	private void showClasseDetails(Matiere matiere) {
		if (matiere != null) {
			nom.setText(matiere.getNom());
			id.setText(matiere.getId_matiere().toString());
			niveau.setText(matiere.getClasse());
			coefficient.setText(matiere.getCoefficient());
			supervisor.setText(matiere.getSupervisor());
			semester.setText(String.valueOf(matiere.getSemestre()));
		} else {
			// Person is null, remove all the text.
			nom.setText("");
			id.setText("");
			niveau.setText("");
			coefficient.setText("");
			supervisor.setText("");
			semester.setText("");
		}
	}
	
	public void loadMatiereDetailWhenCreateUpdate() {
		Long id = matiereRepository.findLastId();
		matiereList.clear();
		matiereList.addAll(matiereRepository.findByLastId(id));
		subjectTable.setItems(matiereList);
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleNewsubject(ActionEvent event) {
		stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.MATIEREEDITDIALOG);
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleEditsubject(ActionEvent event) {
		Matiere selectedMatiere = subjectTable.getSelectionModel().getSelectedItem();
		if (selectedMatiere != null) {
			stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.MATIEREEDITDIALOG);
			dialog.showMatiereDetails(selectedMatiere);
			editbuttonclic = true;
		}

		else {
			// Nothing selected.
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No class Selected",
					"Please select a person in the table.");
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleDeletesubject(ActionEvent event) {
		Matiere selectedMatiere = subjectTable.getSelectionModel().getSelectedItem();
		if (selectedMatiere != null) {
			subjectTable.getItems().remove(selectedMatiere);
			matiereRepository.delete(selectedMatiere);
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No class Selected",
					"Please select a person in the table.");
		}
	}
	
	public void loadMatiereDetail() {
		matiereList.clear();
		if (getFiltre() == "All class") {
			matiereList.addAll(matiereRepository.findAll());
			subjectTable.setItems(matiereList);
		} else {
			for(Matiere m: ol) {
				if(m.getClasse().contains(getFiltre()))
				matiereList.add(m);
			}
			subjectTable.setItems(matiereList);
		}
	}

	private String getFiltre() {
		return filtre.getSelectionModel().getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFiltre();
		setColumProperties();

		subjectTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Matiere>() {

			@Override
			public void changed(ObservableValue<? extends Matiere> observable, Matiere oldValue, Matiere newValue) {
				showClasseDetails(newValue);
			}
		});
		
	}
	
	private void setFiltre() {
		ol.addAll(matiereRepository.findAll());
		ArrayList<String> list2 = classeRepository.loadAllClasse();
		filtrage.clear();
		filtrage.addAll(list2);
		filtrage.add("All class");
		filtre.setItems(filtrage);
		filtre.getSelectionModel().selectLast();
	}

	private void setColumProperties() {
		subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		subjectLevelColumn.setCellValueFactory(new PropertyValueFactory<>("classe"));
	}
}
