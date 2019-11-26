package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Departement;
import com.codetreatise.bean.Personel;
import com.codetreatise.bean.Poste;
import com.codetreatise.bean.Utilisateur;
import com.codetreatise.repository.DepartementRepository;
import com.codetreatise.repository.PosteRepository;
import com.codetreatise.repository.StaffRepository;
import com.codetreatise.repository.UserRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.StaffServiceImpl;
import com.codetreatise.service.impl.UserServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

@Controller
public class StaffEditDialogController implements Initializable {

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
	private TextField matricule;

	@FXML
	private TextField fonction;

	@FXML
	private TextField id;

	@FXML
	private TextField email;

	@FXML
	private MenuButton poste;

	@FXML
	private MenuButton departement;

	@FXML
	private ListView<Object> listDepartement;

	@FXML
	private ListView<Object> listPoste;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private StaffController staffController;

	@Autowired
	private PosteRepository posteRepository;

	@Autowired
	private DepartementRepository departementRepository;
	
	@Autowired
	private MethodUtilitaire methodUtilitaire;

	@Autowired
	private StaffServiceImpl staffServiceImpl;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceImpl;

	// private ObservableList<Object> posteList =
	// FXCollections.observableArrayList();
	private ObservableList<CheckMenuItem> departementList = FXCollections.observableArrayList();
	private ObservableList<CheckMenuItem> posteList = FXCollections.observableArrayList();

//	private void setPosteList() {
//		ArrayList<Poste> list = posteRepository.loadAllPoste();
//		posteList.clear();
//		for (int i = 0; i < list.size(); i++) {
//			Poste postes = list.get(i);
//
//			posteList.add(postes.toString());
//			poste.setItems(posteList);
//		}
//	}

	private void setDepartementList() {
		ArrayList<Departement> list = departementRepository.loadAllDepartement();
		departementList.clear();
		for (int i = 0; i < list.size(); i++) {
			Departement departement = list.get(i);

			departementList.add(new CheckMenuItem(departement.toString()));
		}
		departement.getItems().addAll(departementList);

		for (CheckMenuItem item : departementList) {
			item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
				if (newValue) {
					listDepartement.getItems().add(item.getText());
				} else {
					listDepartement.getItems().remove(item.getText());
				}
			});
		}
	}

	private void setPosteList() {
		ArrayList<Poste> list = posteRepository.loadAllPoste();
		posteList.clear();
		for (int i = 0; i < list.size(); i++) {
			Poste poste = list.get(i);
			System.out.println(poste);
			System.out.println(poste.getId_poste());
			posteList.add(new CheckMenuItem(poste.toString()));
		}
		poste.getItems().addAll(posteList);

		for (final CheckMenuItem item : posteList) {
			item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
				if (newValue) {
					listPoste.getItems().add(item.getText());
				} else {
					listPoste.getItems().remove(item.getText());
				}
			});
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPosteList();
		setDepartementList();
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
//		System.out.println(chaine);
//		System.out.println(Math.round(random.nextFloat() * Math.pow(10, 12)));
//		System.out.println(10000000 * random.nextDouble());
//		System.out.println((random.nextInt(1000000) + 1000000000) * random.nextInt(900) + 100);
		return chaine;
	}

	@FXML
	private void handleRemoveDepartementClick(ActionEvent event) {
		try {
			int index = listDepartement.getSelectionModel().getSelectedIndex();
			listDepartement.getItems().remove(index);
		} catch (Exception e) {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Fail to remove item", "Fail to remove department",
					"Fail to remove item ! please select item firstly");
		}
	}

	@FXML
	private void handleRemovePosteClick(ActionEvent event) {
		try {
			 int index = listPoste.getSelectionModel().getSelectedIndex();
		        listPoste.getItems().remove(index);
		} catch (Exception e) {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Fail to remove item", "Fail to remove post", "Fail to remove item !! please select item firstly");
		}
	}

	@FXML
	public Personel handleCreateStaffClick(ActionEvent event) throws IOException, Exception {

		Personel newPersonel = null;
		if (isInputValid()) {
			if (staffController.isEditButtonClick()) {
				Personel personel = staffRepository.findOne(getId());
				personel.setEmail(getEmail());
				personel.setMatricule(getMatricule());
				personel.setFonction(getFonction());
				personel.setNom(getNom());
				personel.setPrenom(getPrenom());
				personel.setSexe(getSexe());
				personel.setTelephone(getPhone());
				personel.addPostes(getPoste());
				personel.addDepartements(getDepartements());
				newPersonel = staffServiceImpl.update(personel);
				updateAlert(newPersonel);
				clearFields();
				staffController.setIsEditButtonClick(false);
				methodUtilitaire.LogFile("Modification des données d'un personnel", newPersonel.getId()+"-"+newPersonel.getNom()+" "+newPersonel.getPrenom(), MethodUtilitaire.deserializationUser());
				Utilisateur utilisateur = userRepository.findOne(personel.getId()); 
				if(utilisateur !=null) {
					utilisateur.setNom(newPersonel.getNom());
					utilisateur.setPrenom(newPersonel.getPrenom());
					 userServiceImpl.update(utilisateur);
				}
				return newPersonel;
			} else {
				Personel personel = new Personel();
				personel.setEmail(getEmail());
				personel.setMatricule(getMatricule());
				personel.setFonction(getFonction());
				personel.setNom(getNom());
				personel.setPrenom(getPrenom());
				personel.setSexe(getSexe());
				personel.setTelephone(getPhone());
				personel.addDepartements(getDepartements());
				personel.addPostes(getPoste());
				newPersonel = staffRepository.save(personel);
				saveAlert(newPersonel);
				staffController.loadStaffDetailWhenCreate();
				clearFields();
				methodUtilitaire.LogFile("Enregistrement d'un nouveau personnel", newPersonel.getId()+"-"+newPersonel.getNom()+" "+newPersonel.getPrenom(), MethodUtilitaire.deserializationUser());
				return newPersonel;
			}
		}
		return newPersonel;

	}

	@FXML
	void handleCancelClick(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		staffController.setIsEditButtonClick(false);
	}

	@FXML
	void handleClearFieldClick(ActionEvent event) {
		clearFields();
	}

	@FXML
	void handleGenerateRegisterClick(ActionEvent event) {
		matricule.setText(aleatoireNumber());
	}

	@FXML
	void handleRemoveDepartement(ActionEvent event) {

	}

	@FXML
	void handleRemovePoste(ActionEvent event) {

	}

	public String getNom() {
		return nom.getText();
	}

	public String getPrenom() {
		return prenom.getText();
	}

	public String getRadioM() {
		return radioM.getText();
	}

	public String getRadioF() {
		return radioF.getText();
	}

	public String getPhone() {
		return phone.getText();
	}

	public String getMatricule() {
		return matricule.getText();
	}

	public String getFonction() {
		return fonction.getText();
	}

	public Long getId() {
		return Long.parseLong(id.getText());
	}

	public String getEmail() {
		return email.getText();
	}

//	private Long getLibelePoste() {
//		List<Poste> list = posteRepository.findAll();
//		Poste p = null;
//		Long id = null;
//		for (int i = 0; i < list.size(); i++) {
//			p = list.get(i);
//			boolean obj = poste.getSelectionModel().getSelectedItem().equals(p.toString());
//			if (obj) {
//				id = p.getId_poste();
//				break;
//			}
//		}
//
//		return id;
//	}

//	public Poste getPoste() {
//		Poste poste = posteRepository.findOne(getLibelePoste());
//		System.out.println(poste);
//		return poste;
//	}

	private List<Poste> getPoste() {

		List<Poste> list = new ArrayList<Poste>();
		listPoste.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listPoste.getSelectionModel().selectAll();
		int taille = listPoste.getSelectionModel().getSelectedItems().size();
		for (int i = 0; i < taille; i++) {
			String str = (String) listPoste.getSelectionModel().getSelectedItems().get(i);
			Poste poste = posteRepository.findByLibele(str);
			list.add(poste);
		}
		return list;
	}

	private List<Departement> getDepartements() {

		List<Departement> list = new ArrayList<Departement>();
		listDepartement.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listDepartement.getSelectionModel().selectAll();
		int taille = listDepartement.getSelectionModel().getSelectedItems().size();
		for (int i = 0; i < taille; i++) {
			String str = (String) listDepartement.getSelectionModel().getSelectedItems().get(i);
			Departement departement = departementRepository.findByLibele(str);
			list.add(departement);
		}
		return list;
	}

	private String getSexe() {
		return radioM.isSelected() ? radioM.getText() : radioF.getText();
	}

	private String getSexeTitle(String gender) {
		return (gender.equals("Male")) ? "his" : "her";
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

		if (getPoste() == null) {
			errorMessage += "No valid post!\n";
		}

		if (getMatricule() == null || getMatricule().length() == 0) {
			errorMessage += "No valid register!\n";
		}

		if (getEmail() == null || getEmail().length() == 0) {
			errorMessage += "No valid email!\n";
		}

		if (getFonction() == null || getFonction().length() == 0) {
			errorMessage += "No valid function!\n";
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

	public Personel showPersonDetails(Personel personel) {
		if (personel != null) {
			// Fill the labels with info from the person object.
			id.setText(personel.getId().toString());
			nom.setText(personel.getNom());
			prenom.setText(personel.getPrenom());
			fonction.setText(personel.getFonction());
			phone.setText(personel.getTelephone());
			for (int i = 0; i < poste.getItems().size(); i++) {
				for (int j = 0; j < personel.getPostes().size(); j++) {
					if (poste.getItems().get(i).getText().equals(personel.getPostes().get(j).toString())) {
						listPoste.getItems().add(poste.getItems().get(i).getText());
						//poste.getItems().get(i).setDisable(true);
					}
				}
			}

			for (int i = 0; i < departement.getItems().size(); i++) {
				for (int j = 0; j < personel.getDepartements().size(); j++) {
					if (departement.getItems().get(i).getText().equals(personel.getDepartements().get(j).getLibele())) {
						listDepartement.getItems().add(departement.getItems().get(i).getText());
						//departement.getItems().get(i).setDisable(true);
					}
				}
			}
			email.setText(personel.getEmail());
			if (personel.getSexe().equals("Masculin"))
				radioM.setSelected(true);
			else
				radioF.setSelected(true);
			matricule.setText(personel.getMatricule());

		}
		return personel;
	}

	private void clearFields() {
		matricule.clear();
		nom.clear();
		prenom.clear();
		fonction.clear();
		radioM.setSelected(false);
		radioF.setSelected(false);
		phone.clear();
		email.clear();
		listPoste.getItems().clear();
		listDepartement.getItems().clear();
	}

	private void saveAlert(Personel personel) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Staff saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Staff " + personel.getNom() + " " + personel.getPrenom() + " has been created and \n"
				+ getSexeTitle(personel.getSexe()) + " id is " + personel.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Personel personel) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Staff updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Staff " + personel.getNom() + " " + personel.getPrenom() + " has been update and \n"
				+ getSexeTitle(personel.getSexe()) + " id is " + personel.getId() + ".");
		alert.showAndWait();
	}

}
