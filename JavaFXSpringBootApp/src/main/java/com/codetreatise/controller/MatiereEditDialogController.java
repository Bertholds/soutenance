package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Matiere;
import com.codetreatise.bean.Personel;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.MatiereRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.MatiereServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class MatiereEditDialogController implements Initializable {
	@FXML
	private MenuButton classe;
	@FXML
	private Spinner<Integer> coef;
	@FXML
	private TextField nom;
	@FXML
	private TextField id;
	@FXML
	private ComboBox<Object> supervisor;
	@FXML
	private Spinner<Integer> semester;
	@FXML
	private ListView<String> listClasse;

	@Autowired
	private MatiereRepository matiereRepository;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private MatiereController matiereController;

	@Autowired
	private MatiereServiceImpl matiereServiceImpl;

	private ObservableList<CheckMenuItem> classeList = FXCollections.observableArrayList();
	private ObservableList<Object> supervisorList = FXCollections.observableArrayList();
	SpinnerValueFactory<Integer> valueFactorycoef = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
	SpinnerValueFactory<Integer> valueFactorysemester = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

	private void setClasseList() {
		ArrayList<String> list = classeRepository.loadAllClasse();
		classeList.clear();
		for (int i = 0; i < list.size(); i++) {
			String classe = list.get(i);
			classeList.add(new CheckMenuItem(classe));
			System.out.println(classeList.size());
		}
		classeList.add(new CheckMenuItem("All class"));
		classe.getItems().addAll(classeList);

		for (CheckMenuItem item : classeList) {
			item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
				if (newValue) {
					listClasse.getItems().add(item.getText());
				} else {
					listClasse.getItems().remove(item.getText());
				}
			});
		}
	}

	private void setSupervisorList() {
		ArrayList<Personel> list = classeRepository.loadLeader();
		supervisorList.clear();
		for (int i = 0; i < list.size(); i++) {
			Personel personel = list.get(i);
			supervisorList.add(personel.toString());
			supervisor.setItems(supervisorList);
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public Matiere handleCreateSubjectClick(ActionEvent event) {
		Matiere newMatiere = null;
		if (isInputValid()) {
			if (matiereController.isEditButtonClick()) {
				Matiere matiere = matiereRepository.findOne(getId());
				matiere.setNom(getNom());
				matiere.setNiveau(getLevel());
				matiere.setCoefficient(getCoefficient());
				matiere.setSupervisor(getSupervisor());
				matiere.setSemestre(getSemester());
				matiere.setClasses(getClasses());
				newMatiere = matiereServiceImpl.update(matiere);
				MethodUtilitaire.saveAlert(newMatiere, "subject updated successfully.",
						"The subject " + newMatiere.getNom() + " of " + newMatiere.getNiveau()
								+ " has been updated  whith level " + newMatiere.getNiveau() + " and supervisor "
								+ newMatiere.getSupervisor() + ".");
				clearFields();
				matiereController.SetIsEditButtonClick(false);
				return newMatiere;
			} else {
				// classroom = classeRepository.findByNom(getClasseRoom());
				Matiere matiere = new Matiere();
				matiere.setNom(getNom());
				matiere.setNiveau(getLevel());
				matiere.setCoefficient(getCoefficient());
				matiere.setSupervisor(getSupervisor());
				matiere.setSemestre(getSemester());
				matiere.setClasses(getClasses());
				newMatiere = matiereRepository.save(matiere);
				MethodUtilitaire.saveAlert(newMatiere, "subject created successfully.",
						"The subject " + newMatiere.getNom() + " of " + newMatiere.getNiveau()
								+ " has been created  whith level " + newMatiere.getNiveau() + " and supervisor "
								+ newMatiere.getSupervisor() + ".");
				clearFields();
				matiereController.loadMatiereDetailWhenCreateUpdate();
				return newMatiere;
			}
		}
		return newMatiere;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleCancelClick(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClearFieldClick(ActionEvent event) {
		clearFields();
	}

	@FXML
	private void handleRemoveLevelClick() {
		try {
			int index = listClasse.getSelectionModel().getSelectedIndex();
			listClasse.getItems().remove(index);
		} catch (Exception e) {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Fail to remove item", "Fail to remove level",
					"Fail to remove level ! please select item firstly");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setClasseList();
		setSupervisorList();
		coef.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		semester.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		coef.setValueFactory(valueFactorycoef);
		semester.setValueFactory(valueFactorysemester);
	}

	private Long getId() {
		return Long.parseLong(id.getText());
	}

	private String getNom() {
		return nom.getText();
	}

	private String getLevel() {
		listClasse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listClasse.getSelectionModel().selectAll();
		int taille = listClasse.getSelectionModel().getSelectedItems().size();
		String niveau = "";
		for (int i = 0; i < taille; i++) {
			niveau += listClasse.getSelectionModel().getSelectedItems().get(i) + "--";
		}
		return niveau;
	}

	private int getCoefficient() {
		return Integer.parseInt(coef.getEditor().getText());
	}

	private String getSupervisor() {
		String string = null;
		try {
			string = supervisor.getSelectionModel().getSelectedItem().toString();
		} catch (Exception e) {

		}
		return string;
	}

	private int getSemester() {
		return Integer.parseInt(semester.getEditor().getText());
	}

	private List<Classe> getClasses() {
		List<Classe> list = new ArrayList<>();
		listClasse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listClasse.getSelectionModel().selectAll();
		int taille = listClasse.getSelectionModel().getSelectedItems().size();
		boolean mouchard = false;
		for(int i=0; i<taille; i++){
			if (listClasse.getSelectionModel().getSelectedItems().get(i) == "All class") {
				list.clear();
				list.addAll(classeRepository.findAll());
				mouchard = true;
			}
			else if(listClasse.getSelectionModel().getSelectedItems().get(i) != "All class" && mouchard==false) {
				String str = (String) listClasse.getSelectionModel().getSelectedItems().get(i);
				Classe classe = classeRepository.findByNom(str);
				list.add(classe);
				System.out.println(classe.getNom());
			}
		}
			
		return list;
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getNom() == null || getNom().length() == 0) {
			errorMessage += "No valid field  name!\n";
		}
		if (getLevel() == null || getLevel().length() == 0) {
			errorMessage += "No valid field level!\n";
		}
		if (getCoefficient() == 0) {
			errorMessage += "No valid field coefficient!\n";
		}

		if (getSupervisor() == null || getSupervisor().length() == 0) {
			errorMessage += "No valid field supervisor!\n";
		}

		if (getSemester() == 0) {
			errorMessage += "No valid field semester!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

	public Matiere showMatiereDetails(Matiere selectedMatiere) {
		if (selectedMatiere != null) {
			nom.setText(selectedMatiere.getNom());
			id.setText(selectedMatiere.getId_matiere().toString());
			listClasse.getItems().add(selectedMatiere.getNiveau());
			coef.getEditor().setText(String.valueOf(selectedMatiere.getCoefficient()));
			supervisor.getSelectionModel().select(selectedMatiere.getSupervisor());
			semester.getEditor().setText(String.valueOf(selectedMatiere.getSemestre()));

		}
		return selectedMatiere;
	}

	private void clearFields() {
		nom.clear();
		listClasse.getItems().clear();
		coef.getEditor().setText("0");
		supervisor.getSelectionModel().clearSelection();
		semester.getEditor().setText("0");
		id.clear();
	}
}
