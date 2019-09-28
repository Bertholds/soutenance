package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Coefficient;
import com.codetreatise.bean.Matiere;
import com.codetreatise.bean.Personel;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.CoefficientRepository;
import com.codetreatise.repository.MatiereRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.CoefficientServiceImpl;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

@Controller
public class MatiereEditDialogController implements Initializable {
	@FXML
	private RowConstraints rowCoefficient;
	@FXML
	private MenuButton classe;
	@FXML
	private MenuButton edit;
	@FXML
	private TextField nom;
	@FXML
	private TextField id;
	@FXML
	private MenuButton filtreCoefficient;
	@FXML
	private ComboBox<Object> supervisor;
	@FXML
	private Spinner<Integer> semester;
	@FXML
	private ListView<String> listClasse;
	@FXML
	private ListView<String> listCoefficient;

	@Autowired
	private MatiereRepository matiereRepository;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private MatiereController matiereController;

	@Autowired
	private MatiereServiceImpl matiereServiceImpl;

	@Autowired
	private CoefficientRepository coefficientRepository;

	@Autowired
	private CoefficientServiceImpl coefficientServiceImpl;

	String tabCoef[] = { "1", "2", "3", "4", "5", "6", "7", "8" };
	Map<String, Integer> memoire = new HashMap<String, Integer>();

	private ObservableList<CheckMenuItem> classeList = FXCollections.observableArrayList();
	private ObservableList<CheckMenuItem> coefficientList = FXCollections.observableArrayList();
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
		classe.getItems().addAll(classeList);

		for (CheckMenuItem item : classeList) {
			item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
				if (newValue) {
					listClasse.getItems().add(item.getText());
					classe.getItems().remove(item);
				} else {
					listClasse.getItems().remove(item.getText());
				}
			});
		}
	}

	
	private void setCoefficientList() {
		coefficientList.clear();
		for (int i = 0; i < tabCoef.length; i++) {
			String coef = tabCoef[i];
			coefficientList.add(new CheckMenuItem(coef));
		}
		filtreCoefficient.getItems().addAll(coefficientList);
		for (CheckMenuItem item : coefficientList) {
			item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

				if (newValue) {
					if (listClasse.getSelectionModel().getSelectedItem() != null) {
						int index = listClasse.getSelectionModel().getSelectedIndex();
						{
							listCoefficient.getItems().add("0");
						}
						listCoefficient.getItems().remove(index);
						listCoefficient.getItems().add(index,
								item.getText() + " (" + listClasse.getItems().get(index) + ")");
						memoire.put(listClasse.getItems().get(index), Integer.parseInt(item.getText()));
						listCoefficient.getItems().add(listCoefficient.getItems().size(), "0");
						filtreCoefficient.getItems().clear();
						setCoefficientList();
					} else {
						MethodUtilitaire.deleteNoPersonSelectedAlert(null, "No class selected",
								"Select class firstly and try agane");
						filtreCoefficient.getItems().clear();
						setCoefficientList();
					}
				} else {
					listClasse.getItems().remove(item.getText());
				}
			});
		}
	}

	
	private void setCoefficientEdit( ) {
		coefficientList.clear();
		for (int i = 0; i < tabCoef.length; i++) {
			String coef = tabCoef[i];
			coefficientList.add(new CheckMenuItem(coef));
		}
		edit.getItems().addAll(coefficientList);
		for (CheckMenuItem item : coefficientList) {
			item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

				if (newValue) {
					if (listCoefficient.getSelectionModel().getSelectedItem() != null) {
						int index = listCoefficient.getSelectionModel().getSelectedIndex();
						{
							listCoefficient.getItems().add("0");
						}
						listCoefficient.getItems().remove(index);
						listCoefficient.getItems().add(index,
								item.getText() + " (" + listClasse.getItems().get(index) + ")");
						memoire.replace(listCoefficient.getItems().get(index), Integer.parseInt(item.getText()));
						listCoefficient.getItems().add(listCoefficient.getItems().size(), "0");
						edit.getItems().clear();
						setCoefficientEdit();
					} else {
						MethodUtilitaire.deleteNoPersonSelectedAlert(null, "No coefficient selected",
								"Select coefficient firstly and try agane");
						filtreCoefficient.getItems().clear();
						setCoefficientEdit();;
					}
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
			selectAllCoef();
			selectAllClasse();
			if (matiereController.isEditButtonClick()) {
				System.out.println("depart " + listCoefficient.getSelectionModel().getSelectedItems().size());
				System.out.println("arrivzer" + listClasse.getSelectionModel().getSelectedItems().size());
				for (int i = listCoefficient.getSelectionModel().getSelectedItems().size() - 1; i > listClasse
						.getItems().size() - 1; i--) {
					listCoefficient.getItems().remove(listCoefficient.getItems().get(i));
					System.out.println("remove " + i);
				}

				Matiere matiere = matiereRepository.findOne(getId());
				if (listClasse.getItems().size() == listCoefficient.getItems().size()) {
					matiere.setNom(getNom());
					matiere.setClasse(getClasse());
					matiere.setCoefficient(getCoeffifient());
					matiere.setSupervisor(getSupervisor());
					matiere.setSemestre(getSemester());
					// matiere.setClasses(getClasses());
					newMatiere = matiereServiceImpl.update(matiere);
					MethodUtilitaire.saveAlert(newMatiere, "subject updated successfully.",
							"The subject " + newMatiere.getNom() + " of " + newMatiere.getClasse()
									+ " has been updated  whith level " + newMatiere.getClasse() + " and supervisor "
									+ newMatiere.getSupervisor() + ".");
					matiereController.SetIsEditButtonClick(false);
					updateCoef();
					clearFields();
					return newMatiere;
				} else
					MethodUtilitaire.deleteNoPersonSelectedAlert(null,
							"Attention le nombre de classe doit etre egal au nombre de coefficient",
							listClasse.getItems().size() + " classe et " + listCoefficient.getItems().size()
									+ " coefficient");

			} else {
				try {
					for (int i = listCoefficient.getSelectionModel().getSelectedItems().size() - 1; i > listClasse
							.getItems().size() - 1; i--) {
						listCoefficient.getItems().remove(listCoefficient.getItems().get(i));
					}
				} catch (Exception e) {
					MethodUtilitaire.deleteNoPersonSelectedAlert("coefficient no set", "coefficient no set",
							"coefficient no set");
				}
				if (listClasse.getItems().size() == listCoefficient.getItems().size()) {
					Matiere matiere = new Matiere();
					matiere.setNom(getNom());
					matiere.setClasse(getClasse());
					matiere.setCoefficient(getCoeffifient());
					matiere.setSupervisor(getSupervisor());
					matiere.setSemestre(getSemester());
					newMatiere = matiereRepository.save(matiere);
					MethodUtilitaire.saveAlert(newMatiere, "subject created successfully.",
							"The subject " + newMatiere.getNom() + " of " + newMatiere.getClasse()
									+ " has been created  whith level " + newMatiere.getClasse() + " and supervisor "
									+ newMatiere.getSupervisor() + ".");
					createCoef();
					clearFields();
					matiereController.loadMatiereDetailWhenCreateUpdate();
					return newMatiere;

				} else
					MethodUtilitaire.deleteNoPersonSelectedAlert(null,
							"Attention le nombre de classe doit etre egal au nombre de coefficient",
							listClasse.getItems().size() + " classe et " + listCoefficient.getItems().size()
									+ " coefficient");
			}
		}
		return newMatiere;
	}

	private void createCoef() {
		int taille = listClasse.getItems().size();
		System.out.println(taille);
		for (int i = 0; i < taille; i++) {
			Coefficient coefficient = new Coefficient();
			System.out.println(memoire.size() + "memoire");
			coefficient.setCoefficient(memoire.get(listClasse.getItems().get(i)));
			// coefficient.setCoefficient(Integer.parseInt(listCoefficient.getItems().get(i).substring(0,
			// 2).trim()));
			coefficient.setMatiere(matiereRepository.findByNom(getNom()));
			System.out.println("----------------------matiere Ok");
			coefficient.setClasse(classeRepository.findByNom(listClasse.getItems().get(i)));
			System.out.println("----------------------classe Ok");
			coefficientRepository.save(coefficient);
			System.out.println("---------------------dave OK");

		}
	}

	private void updateCoef() {
		int taille = listClasse.getItems().size();
		System.out.println(taille);
		for (int i = 0; i < taille; i++) {
			Coefficient coefficient = coefficientRepository.findByCLasseAndMatiere(
					classeRepository.findByNom(listClasse.getItems().get(i)), matiereRepository.findByNom(getNom()));
			System.out.println(memoire.size() + "memoire");
			if (coefficient != null) {
				// coefficient.setCoefficient(memoire.get(listClasse.getItems().get(i)));
				coefficient.setCoefficient(Integer.parseInt(listCoefficient.getItems().get(i).substring(0, 2).trim()));
				coefficient.setMatiere(matiereRepository.findByNom(getNom()));
				System.out.println("----------------------matiere Ok");
				coefficient.setClasse(classeRepository.findByNom(listClasse.getItems().get(i)));
				System.out.println("----------------------classe Ok");
				coefficientServiceImpl.update(coefficient);
				System.out.println("---------------------update OK");

			} else {
				coefficient = new Coefficient();
				System.out.println(memoire.size() + "memoire");
				coefficient.setCoefficient(memoire.get(listClasse.getItems().get(i)));
				// coefficient.setCoefficient(Integer.parseInt(listCoefficient.getItems().get(i).substring(0,
				// 2).trim()));
				coefficient.setMatiere(matiereRepository.findByNom(getNom()));
				System.out.println("----------------------matiere Ok");
				coefficient.setClasse(classeRepository.findByNom(listClasse.getItems().get(i)));
				System.out.println("----------------------classe Ok");
				coefficientRepository.save(coefficient);
				System.out.println("---------------------dave OK");
			}
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void handleCancelClick(ActionEvent event) {
		matiereController.SetIsEditButtonClick(false);
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
			if(listCoefficient.getItems().size()!=0) {
			String clas = listClasse.getSelectionModel().getSelectedItem();
			classe.getItems().add(new MenuItem(clas));
			listCoefficient.getItems().remove(index);
			listClasse.getItems().remove(index);
			Coefficient coefficient = coefficientRepository.findByCLasseAndMatiere(classeRepository.findByNom(clas),
					matiereRepository.findByNom(getNom()));
			if(coefficient !=null)
				coefficientRepository.delete(coefficient);
			}else
				listClasse.getItems().remove(index);
		} catch (Exception e) {
			e.printStackTrace();
			MethodUtilitaire.deleteNoPersonSelectedAlert("Fail to remove item", "Fail to remove level",
					"Fail to remove level ! please select item firstly");
		}
	}

	@FXML
	private void handleRemoveCoefficientClick() {
		try {
			int index = listCoefficient.getSelectionModel().getSelectedIndex();
			if(listClasse.getItems().size()!=0) {
			Coefficient coefficient = coefficientRepository.findByCLasseAndMatiere(
					classeRepository.findByNom(listClasse.getItems().get(index)),
					matiereRepository.findByNom(getNom()));
			listClasse.getItems().remove(index);
			listCoefficient.getItems().remove(index);
			if(coefficient !=null)
				coefficientRepository.delete(coefficient);
			}else
				listCoefficient.getItems().remove(index);
		} catch (Exception e) {
			e.printStackTrace();
			MethodUtilitaire.deleteNoPersonSelectedAlert("Fail to remove item", "Fail to remove level",
					"Fail to remove level ! please select item firstly" + e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// permet de demarer la liste des coef des matiere avec 0 pour ne pas fausser la
		// methode setCoefficientList()
		// listCoefficient.getItems().add("0");
		setCoefficientList();
		setCoefficientEdit();
		setClasseList();
		setSupervisorList();
		semester.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		semester.setValueFactory(valueFactorysemester);
	}

	private Long getId() {
		return Long.parseLong(id.getText());
	}

	private String getNom() {
		return nom.getText();
	}

	private String getClasse() {
		selectAllClasse();
		int taille = listClasse.getSelectionModel().getSelectedItems().size();
		String niveau = "";
		for (int i = 0; i < taille; i++) {
			niveau += listClasse.getSelectionModel().getSelectedItems().get(i) + "  ";
		}
		return niveau;
	}

	private void selectAllCoef() {
		listCoefficient.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listCoefficient.getSelectionModel().selectAll();
	}

	private void selectAllClasse() {
		listClasse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listClasse.getSelectionModel().selectAll();
	}

	private String getCoeffifient() {
		selectAllCoef();
		int taille = listCoefficient.getSelectionModel().getSelectedItems().size();
		String coef = "";
		for (int i = 0; i < taille; i++) {
			coef += listCoefficient.getSelectionModel().getSelectedItems().get(i) + "  ";
		}
		return coef;
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

//	private List<Classe> getClasses() {
//		List<Classe> list = new ArrayList<>();
//		listClasse.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		listClasse.getSelectionModel().selectAll();
//		int taille = listClasse.getSelectionModel().getSelectedItems().size();
//		boolean mouchard = false;
//		for (int i = 0; i < taille; i++) {
//			if (listClasse.getSelectionModel().getSelectedItems().get(i) == "All class") {
//				list.clear();
//				list.addAll(classeRepository.findAll());
//				mouchard = true;
//			} else if (listClasse.getSelectionModel().getSelectedItems().get(i) != "All class" && mouchard == false) {
//				String str = (String) listClasse.getSelectionModel().getSelectedItems().get(i);
//				Classe classe = classeRepository.findByNom(str);
//				list.add(classe);
//				System.out.println(classe.getNom());
//			}
//		}
//
//		return list;
//	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getNom() == null || getNom().length() == 0) {
			errorMessage += "No valid field  name!\n";
		}
		if (getClasse() == null || getClasse().length() == 0) {
			errorMessage += "No valid field level!\n";
		}
//		if (getCoefficient() == 0) {
//			errorMessage += "No valid field coefficient!\n";
//		}

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
			for (CheckMenuItem c : classeList) {
				if (selectedMatiere.getClasse().contains(c.getText())) {
					listClasse.getItems().add(c.getText());
					classe.getItems().remove(c);
				}
				if (selectedMatiere.getCoefficient().contains(c.getText())) {
					Coefficient coef = coefficientRepository
							.findByCLasseAndMatiere(classeRepository.findByNom(c.getText()), selectedMatiere);
					listCoefficient.getItems().add(String.valueOf(coef.getCoefficient()) + " (" + c.getText() + ")");
				}
			}
//			for (String coef : tabCoef) {
//				if (selectedMatiere.getCoefficient().contains(coef))
//					listCoefficient.getItems().add(coef);
//			}
			supervisor.getSelectionModel().select(selectedMatiere.getSupervisor());
			semester.getEditor().setText(String.valueOf(selectedMatiere.getSemestre()));

		}
		return selectedMatiere;
	}

	private void clearFields() {
		nom.clear();
		listClasse.getItems().clear();
		listCoefficient.getItems().clear();
		// coef.getEditor().setText("0");
		supervisor.getSelectionModel().clearSelection();
		semester.getEditor().setText("0");
		id.clear();
	}
}
