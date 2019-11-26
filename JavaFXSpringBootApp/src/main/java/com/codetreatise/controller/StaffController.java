package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Departement;
import com.codetreatise.bean.Personel;
import com.codetreatise.bean.Poste;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.DepartementRepository;
import com.codetreatise.repository.PosteRepository;
import com.codetreatise.repository.StaffRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.DepartementServiceImpl;
import com.codetreatise.service.impl.PosteServiceImpl;
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

@Controller
public class StaffController implements Initializable {

	@FXML
	private TableView<Personel> staffTable;

	@FXML
	private TableColumn<Personel, String> firstname;

	@FXML
	private TableColumn<Personel, String> lastname;

	@FXML
	private ComboBox<Object> filtre;

	@FXML
	private TextField recherche;

	@FXML
	private Label id;

	@FXML
	private Label matricule;

	@FXML
	private Label nom;

	@FXML
	private Label prenom;

	@FXML
	private Label sex;

	@FXML
	private Label phone;

	@FXML
	private Label email;

	@FXML
	private Label poste;

	@FXML
	private Label departemnt;

	@FXML
	private Label fonction;

	@FXML
	private TableView<Poste> posteTab;

	@FXML
	private TableColumn<Poste, Long> id2;

	@FXML
	private TableColumn<Poste, String> libele_post;

	@FXML
	private TableColumn<Poste, Departement> departement;

	@FXML
	private TableColumn<Poste, Boolean> modifier;

	@FXML
	private ComboBox<Object> service;

	@FXML
	private TextField libele_poste;

	@FXML
	private TextField id_poste;

	@FXML
	private TableColumn<Departement, Long> id3c;

	@FXML
	private TableColumn<Departement, String> nom_departemen;

	@FXML
	private TableColumn<Departement, String> chef_departemen;

	@FXML
	private TableColumn<Departement, Integer> total_post;

	@FXML
	private TableColumn<Departement, Boolean> modified;

	@FXML
	private TableView<Departement> departementTab;

	@FXML
	private TextField nom_departement;

	@FXML
	private TextField id_departement;

	@FXML
	private TextField chef_departement;

	private boolean editbuttonclic = false;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private StaffEditDialogController dialog;

	@Autowired
	private DepartementRepository departementRepository;

	@Autowired
	private PosteRepository posteRepository;

	@Autowired
	private DepartementServiceImpl departementServiceImpl;

	@Autowired
	private PosteServiceImpl posteServiceImpl;

	@Autowired
	private MethodUtilitaire methodUtilitaire;
	
	private ObservableList<Personel> staffList = FXCollections.observableArrayList();
	private ObservableList<Object> filtrage = FXCollections.observableArrayList();
	private ObservableList<Object> filtrageDepartement = FXCollections.observableArrayList();
	private ObservableList<Departement> filtrageDepartementForTable = FXCollections.observableArrayList();
	private ObservableList<Poste> filtragePoste = FXCollections.observableArrayList();

	@FXML
	private void filterClick(ActionEvent event) {
		loadStaffDetail();
	}

	@FXML
	private void filteredTable(KeyEvent event) {

		FilteredList<Personel> filteredetudiants = new FilteredList<Personel>(staffList, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredetudiants.setPredicate((Predicate<? super Personel>) personel -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();

					if (personel.getMatricule().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (personel.getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (personel.getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Personel> sortedList = new SortedList<Personel>(filteredetudiants);
		sortedList.comparatorProperty().bind(staffTable.comparatorProperty());
		staffTable.setItems(sortedList);
	}

	private void showPersonDetails(Personel personel) {
		if (personel != null) {
			List<Poste> postes = personel.getPostes();
			String str = "";
			for (int i = 0; i < postes.size(); i++) {

				str += postes.get(i).toString() + " -- ";
			}
			List<Departement> departements = personel.getDepartements();
			String str1 = "";
			for (int i = 0; i < departements.size(); i++) {
				str1 += departements.get(i).toString() + " -- ";
			}
			nom.setText(personel.getNom());
			prenom.setText(personel.getPrenom());
			matricule.setText(personel.getMatricule());
			phone.setText(personel.getTelephone());
			fonction.setText(personel.getFonction());
			id.setText(personel.getId().toString());
			poste.setText(str);
			departemnt.setText(str1);
			sex.setText(personel.getSexe());
			matricule.setText(personel.getMatricule());
			email.setText(personel.getEmail());
		} else {
			// Person is null, remove all the text.
			nom.setText("");
			prenom.setText("");
			matricule.setText("");
			phone.setText("");
			fonction.setText("");
			id.setText("");
			poste.setText("");
			departemnt.setText("");
			sex.setText("");
			matricule.setText("");
			email.setText("");
		}
	}

	@FXML
	private void handleCreateDepartmentClick(ActionEvent event) {
		Departement newDepartement = null;
		if (isInputValidDepartement()) {
			Departement departement = new Departement();
			departement.setLibele(getNomDepartement());
			departement.setChef(getChefDepartement());
			departement.setTotalPoste(0);
			newDepartement = departementRepository.save(departement);
			alertCreateDepartement(newDepartement);
			clearFieldDepartement();
			loadDepartementDetail();
		}
	}

	private void updateDepartementTable(Object departement) {
		((Departement) departement).setTotalPoste(((Departement) departement).getPostes().size());
	}

	@FXML
	private void handleCreatePostClick(ActionEvent event) {
		Poste newPoste = null;
		if (isInputValidPoste()) {
			Poste poste = new Poste();
			poste.setLibele(getLibelePoste());
			poste.setDepartement(getDepartement());
			newPoste = posteRepository.save(poste);
			alertCreatePoste(newPoste);
			clearFieldPoste();
			loadPosteDetail();
		}

	}

	@FXML
	void handleCreateStaffClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.STAFFEDITDIALOG);
	}

	@FXML
	void handleDeleteDepartmentClick(ActionEvent event) {
		Departement selectedIndex = departementTab.getSelectionModel().getSelectedItem();
		if (selectedIndex != null) {
			departementTab.getItems().remove(selectedIndex);
			departementRepository.delete(selectedIndex);
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No department Selected", "Please select a department in the table.");
		}
	}

	@FXML
	void handleDeletePostClick(ActionEvent event) {
		Poste selectedIndex = posteTab.getSelectionModel().getSelectedItem();
		if (selectedIndex != null) {
			posteTab.getItems().remove(selectedIndex);
			posteRepository.delete(selectedIndex);
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No post Selected", "Please select a post in the table.");
		}
	}

	@FXML
	void handleDeleteStaffClick(ActionEvent event) throws IOException, Exception {
		Personel selectedIndex = staffTable.getSelectionModel().getSelectedItem();
		if (selectedIndex != null) {
			staffTable.getItems().remove(selectedIndex);
			staffRepository.delete(selectedIndex);
			methodUtilitaire.LogFile("Soppression d'un Personnel", selectedIndex.getId()+"-"+selectedIndex.getNom()+" "+selectedIndex.getPrenom(), MethodUtilitaire.deserializationUser());

		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No staff Selected", "Please select a staff in the table.");
		}
	}

	private void updateDepartement(Departement departement) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Updating department");
		alert.setHeaderText("Department has been update successful");
		alert.setContentText("Department number " + departement.getId_departement() + " has been update successful!");
		alert.showAndWait();
	}

	private void updatePoste(Poste poste) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Updating post");
		alert.setHeaderText("Post has been update successful");
		alert.setContentText("Post number " + poste.getId_poste() + " has been update successful!");
		alert.showAndWait();
	}

	@FXML
	private void handleEditDepartmentClick(ActionEvent event) {
		if (isInputValidDepartement()) {
			Departement newDepartement = null;
			Departement departement = departementTab.getSelectionModel().getSelectedItem();
			try {
				departement.setLibele(getNomDepartement());
				departement.setChef(getChefDepartement());
				departement.setTotalPoste(departement.getPostes().size());
				newDepartement = departementServiceImpl.update(departement);
				updateDepartement(newDepartement);
				clearFieldDepartement();
				loadDepartementDetail();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Updating department failed");
				alert.setHeaderText("failed to update department");
				alert.setContentText("Any row selected! please select department only");
				alert.showAndWait();
			}
		}
	}

	@FXML
	private void handleEditPostClick(ActionEvent event) {
		if (isInputValidPoste()) {
			Poste newPoste = null;
			Poste poste = posteTab.getSelectionModel().getSelectedItem();
			try {
				poste.setLibele(getLibelePoste());
				poste.setDepartement(getDepartement());
				newPoste = posteServiceImpl.update(poste);
				updatePoste(newPoste);
				clearFieldPoste();
				loadPosteDetail();
			} catch (Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Updating post failed");
				alert.setHeaderText("failed to update post");
				alert.setContentText("Any row selected! please select department firstly");
				alert.showAndWait();
			}
		}
	}

	@FXML
	void handleEditStaffClick(ActionEvent event) {

		Personel selectedPerson = staffTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.STAFFEDITDIALOG);
			editbuttonclic = true;
			dialog.showPersonDetails(selectedPerson);
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

	public boolean isEditButtonClick() {
		return editbuttonclic;
	}

	public void setIsEditButtonClick(boolean value) {
		this.editbuttonclic = value;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFiltre();
		setFiltreDepartement();
		setColumProperties();
		setColumDepartementProperties();
		setColumPosteProperties();

		staffTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Personel>() {
			@Override
			public void changed(ObservableValue<? extends Personel> observable, Personel oldValue, Personel newValue) {
				showPersonDetails(newValue);
			}
		});
	}

	private void setFiltre() {
		ArrayList<Departement> list = departementRepository.loadAllDepartement();
		filtrage.clear();
		filtrage.add("All staff");
		filtrage.addAll(list);
		// setFiltreDepartement();
		filtre.setItems(filtrage);
	}

	private void setFiltreDepartement() {
		ArrayList<Departement> list = departementRepository.loadAllDepartement();
		filtrageDepartement.clear();
		filtrageDepartement.addAll(list);
		service.setItems(filtrageDepartement);
	}

	private void setColumProperties() {
		firstname.setCellValueFactory(new PropertyValueFactory<>("nom"));
		lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	}

	private void setColumDepartementProperties() {
		nom_departemen.setCellValueFactory(new PropertyValueFactory<>("libele"));
		chef_departemen.setCellValueFactory(new PropertyValueFactory<>("chef"));
		id3c.setCellValueFactory(new PropertyValueFactory<>("id_departement"));
		total_post.setCellValueFactory(new PropertyValueFactory<>("totalPoste"));
		modified.setCellFactory(cellFactory);
	}

	private void setColumPosteProperties() {
		libele_post.setCellValueFactory(new PropertyValueFactory<>("libele"));
		departement.setCellValueFactory(new PropertyValueFactory<>("departement"));
		id2.setCellValueFactory(new PropertyValueFactory<>("id_poste"));
		modifier.setCellFactory(cellFactory2);
	}

	private Departement getFiltreDepartement() {
		String str = (String) filtre.getSelectionModel().getSelectedItem();
		Departement departement = departementRepository.findByLibele(str);
		return departement;
	}

	public void loadStaffDetail() {
		staffList.clear();
		System.out.println(filtre.getSelectionModel().getSelectedItem());
		if (filtre.getSelectionModel().getSelectedItem() == "All staff") {
			staffList.addAll(staffRepository.findAll());
			staffTable.setItems(staffList);
		} else {
			staffList.addAll(staffRepository.findOther(getFiltreDepartement()));
			staffTable.setItems(staffList);
		}
	}

	@FXML
	public void loadDepartementDetail() {
		filtrageDepartementForTable.clear();
		filtrageDepartementForTable.addAll(departementRepository.findAll());
		for (int i = 0; i < filtrageDepartement.size(); i++) {
			updateDepartementTable(filtrageDepartement.get(i));
			departementTab.setItems(filtrageDepartementForTable);
		}
	}

	@FXML
	private void loadPosteDetail() {
		filtragePoste.clear();
		filtragePoste.addAll(posteRepository.findAll());
		posteTab.setItems(filtragePoste);
	}

	public void loadStaffDetailWhenCreate() {
		Long id = staffRepository.findLastId();
		staffList.clear();
		staffList.addAll(staffRepository.findByLastId(id));
		staffTable.setItems(staffList);
	}

	private String getNomDepartement() {
		return nom_departement.getText();
	}

	private String getChefDepartement() {
		return chef_departement.getText();
	}

	private String getLibelePoste() {
		return libele_poste.getText();
	}

	private Object setValueDepartement(Poste poste) {
		return poste.getDepartement();
	}

	private String getValueDepartement() {
		return (String) service.getSelectionModel().getSelectedItem();
	}

	private Departement getDepartement() {
		String d = (String) service.getValue();
		Departement d1 = departementRepository.findByLibele(d);
		return d1;
	}

	private boolean isInputValidDepartement() {
		String errorMessage = "";

		if (getNomDepartement() == null || getNomDepartement().length() == 0) {
			errorMessage += "No valid  name department!\n";
		}
		if (getChefDepartement() == null || getChefDepartement().length() == 0) {
			errorMessage += "No valid chief department!\n";
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

	private boolean isInputValidPoste() {
		String errorMessage = "";

		if (getLibelePoste() == null || getLibelePoste().length() == 0) {
			errorMessage += "No valid  name of post !\n";
		}
		if (getValueDepartement() == null || getValueDepartement().length() == 0) {
			errorMessage += "No valid name of department!\n";
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

	private void alertCreateDepartement(Departement departement) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Department has been create successful");
		alert.setHeaderText("Creation of department successful");
		alert.setContentText("Department number " + departement.getId_departement() + " whith name "
				+ departement.getLibele() + " has been create successful!");
		alert.showAndWait();
	}

	private void alertCreatePoste(Poste poste) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Poste has been create successful");
		alert.setHeaderText("Creation of Poste successful");
		alert.setContentText("Poste number " + poste.getId_poste() + " whith name " + poste.getLibele()
				+ " has been create successful!");
		alert.showAndWait();
	}

	@FXML
	private void clearFieldPoste() {
		id_poste.clear();
		libele_poste.clear();
		service.setValue(null);
	}

	@FXML
	private void clearFieldDepartement() {
		id_departement.clear();
		nom_departement.clear();
		chef_departement.clear();
	}

	Callback<TableColumn<Departement, Boolean>, TableCell<Departement, Boolean>> cellFactory = new Callback<TableColumn<Departement, Boolean>, TableCell<Departement, Boolean>>() {
		@Override
		public TableCell<Departement, Boolean> call(final TableColumn<Departement, Boolean> param) {
			final TableCell<Departement, Boolean> cell = new TableCell<Departement, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Departement departement = getTableView().getItems().get(getIndex());
							updateUser(departement);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateUser(Departement departement) {
					nom_departement.setText(departement.getLibele());
					chef_departement.setText(departement.getChef());
					id_departement.setText(departement.getId_departement().toString());
				}
			};
			return cell;
		}
	};

	Callback<TableColumn<Poste, Boolean>, TableCell<Poste, Boolean>> cellFactory2 = new Callback<TableColumn<Poste, Boolean>, TableCell<Poste, Boolean>>() {
		@Override
		public TableCell<Poste, Boolean> call(final TableColumn<Poste, Boolean> param) {
			final TableCell<Poste, Boolean> cell = new TableCell<Poste, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Poste poste = getTableView().getItems().get(getIndex());
							updateUser(poste);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateUser(Poste poste) {
					libele_poste.setText(poste.getLibele());
					service.setValue(setValueDepartement(poste));
					service.getSelectionModel().selectedItemProperty();
					service.getSelectionModel().select(setValueDepartement(poste));
					id_poste.setText(poste.getId_poste().toString());
				}
			};
			return cell;
		}
	};
}
