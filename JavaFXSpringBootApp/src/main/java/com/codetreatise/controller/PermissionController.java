package com.codetreatise.controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Departement;
import com.codetreatise.bean.Etudiant;
import com.codetreatise.bean.Permission;
import com.codetreatise.bean.Personel;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.DepartementRepository;
import com.codetreatise.repository.PermissionRepository;
import com.codetreatise.repository.StaffRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.PermissionServiceImpl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

@Controller
public class PermissionController implements Initializable {

	@FXML
	private TableView<Permission> permissionTab;

	@FXML
	private TableColumn<?, ?> idAbscenceC;

	@FXML
	private TableColumn<?, ?> dateC;

	@FXML
	private TableColumn<?, ?> motifC;

	@FXML
	private TableColumn<?, ?> idTransmitterC;

	@FXML
	private TableColumn<Permission, String> nomC;

	@FXML
	private TableColumn<Permission, String> prenomC;

	@FXML
	private TextField idAbscence;

	@FXML
	private DatePicker date;

	@FXML
	private TextField motif;

	@FXML
	private TextField rechercheStudentTab;

	@FXML
	private TextField rechercheStaffTab;

	@FXML
	private TextField recherchePermissionTab;

	@FXML
	private TextField idTransmitter;

	@FXML
	private TextField duree;

	@FXML
	private RadioButton isStudent;

	@FXML
	private RadioButton group;

	@FXML
	private RadioButton individuel;

	@FXML
	private RadioButton isEmployee;

	@FXML
	private ComboBox<Object> filterPermission;

	@FXML
	private Label idAbscenceLabel;

	@FXML
	private Label dateLabel;

	@FXML
	private Label motifLabel;

	@FXML
	private Label idTransmitterLabel;

	@FXML
	private Label nomLabel;

	@FXML
	private Label prenomLabel;

	@FXML
	private Label dureeLabel;

	@FXML
	private Label totalPermissionLabel;

	@FXML
	private Label totalDureeLabel;

	@FXML
	private TableView<Personel> staffTab;

	@FXML
	private TableColumn<?, ?> idWorker;

	@FXML
	private TableColumn<?, ?> nom;

	@FXML
	private TableColumn<?, ?> prenom;

	@FXML
	private ComboBox<String> filterDepartment;

	@FXML
	private TableView<Etudiant> studentTab;

	@FXML
	private TableColumn<?, ?> idStudent;

	@FXML
	private TableColumn<?, ?> name;

	@FXML
	private TableColumn<?, ?> surname;

	@FXML
	private ComboBox<String> filterClasse;

	@Autowired
	private DepartementRepository departementRepository;

	@Autowired
	private ClasseRepository classeRepository;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PermissionServiceImpl PermissionServiceImpl;

	@Autowired
	private PermissionRepository permissionRepository;

	boolean isEditButton = false;
	boolean isPrintButton = false;
	int compteur = 0;

	ObservableList<Object> setFiltre = FXCollections.observableArrayList("No limit", 5, 10, 15, 20, 25, 30, 50, 70);
	ObservableList<String> departementList = FXCollections.observableArrayList();
	ObservableList<Permission> permissionList = FXCollections.observableArrayList();
	ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
	ObservableList<Personel> personelList = FXCollections.observableArrayList();
	ObservableList<String> classeList = FXCollections.observableArrayList();

	@FXML
	void handleClearField(ActionEvent event) {
		clearField();
	}

	@FXML
	void handleDeleteClick(ActionEvent event) {
		Permission selectedpermission = permissionTab.getSelectionModel().getSelectedItem();
		if (selectedpermission != null) {
			if (MethodUtilitaire.confirmationDialog(selectedpermission, "confirm to delete abscence permission",
					"confirm to delete abscence permission", "delete this abscence permission ?")) {
				permissionTab.getItems().remove(selectedpermission);
				permissionRepository.delete(selectedpermission);
				MethodUtilitaire.saveAlert(selectedpermission, "Delete successful", "Delete successful");
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No class Selected",
					"Please select a person in the table.");
		}
	}

	@FXML
	void handleFilteredOnTablePermission(KeyEvent event) {
		FilteredList<Permission> filteredPermission = new FilteredList<Permission>(permissionList, e -> true);
		recherchePermissionTab.setOnKeyReleased(e -> {
			recherchePermissionTab.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredPermission.setPredicate((Predicate<? super Permission>) permission -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (permission.getId_permission().toString().contains(newValueFilter)) {
						return true;
					} else if (permission.getDate().toString().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (permission.getMotif().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (permission.isStatus() == true
							&& permission.getPersonel().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (permission.isStatus() == false
							&& permission.getEtudiant().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (permission.isStatus() == true
							&& permission.getPersonel().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (permission.isStatus() == false
							&& permission.getEtudiant().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Permission> sortedList = new SortedList<Permission>(filteredPermission);
		sortedList.comparatorProperty().bind(permissionTab.comparatorProperty());
		permissionTab.setItems(sortedList);
	}

	@FXML
	void handleFilteredOnTableStaff(KeyEvent event) {
		FilteredList<Personel> filteredepersonel = new FilteredList<Personel>(personelList, e -> true);
		rechercheStaffTab.setOnKeyReleased(e -> {
			rechercheStaffTab.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredepersonel.setPredicate((Predicate<? super Personel>) personel -> {
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

		SortedList<Personel> sortedList = new SortedList<Personel>(filteredepersonel);
		sortedList.comparatorProperty().bind(staffTab.comparatorProperty());
		staffTab.setItems(sortedList);
	}

	@FXML
	void handleFilteredOnTableStudent(KeyEvent event) {
		FilteredList<Etudiant> filteredetudiants = new FilteredList<Etudiant>(etudiantList, e -> true);
		rechercheStudentTab.setOnKeyReleased(e -> {
			rechercheStudentTab.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredetudiants.setPredicate((Predicate<? super Etudiant>) etudiant -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (etudiant.getId().toString().contains(newValueFilter)) {
						return true;
					} else if (etudiant.getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (etudiant.getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Etudiant> sortedList = new SortedList<Etudiant>(filteredetudiants);
		sortedList.comparatorProperty().bind(studentTab.comparatorProperty());
		studentTab.setItems(sortedList);
	}

	@FXML
	void handleModifyClick(ActionEvent event) {
		Permission selectedPermission = permissionTab.getSelectionModel().getSelectedItem();
		if (selectedPermission != null) {
			idAbscence.setText(selectedPermission.getId_permission().toString());
			date.getEditor().setText(selectedPermission.getDate().toString());
			motif.setText(selectedPermission.getMotif());
			idTransmitter
					.setText(selectedPermission.isStatus() == true ? selectedPermission.getPersonel().getId().toString()
							: selectedPermission.getEtudiant().getId().toString());
			duree.setText(String.valueOf(selectedPermission.getDuree()));
			isStudent.setSelected(selectedPermission.isStatus() == true ? false : true);
			isEmployee.setSelected(selectedPermission.isStatus() == true ? true : false);
			isEditButton = true;
		}

		else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No row Selected",
					"Please select a row in the table.");
		}
	}

	@FXML
	void handleRefreshOnPermission(ActionEvent event) {
		loadDataOntablepermissionTab();
	}

	@FXML
	void handleRefreshOnTabStaff(ActionEvent event) {
		loadDataOntablestaffTab();
	}

	@FXML
	void handleRefreshOnTabStudent(ActionEvent event) {
		loadDataOntablestudentTab();
	}

	@FXML
	void handlePrintClick(ActionEvent event) {
		group.setVisible(true);
		individuel.setVisible(true);
		isPrintButton = true;
		compteur += 1;
		if (isPrintButton && (individuel.isSelected()) || group.isSelected()) {
			try {
				System.setProperty("java.awt.headless", "false");

				JasperDesign jasperDesign;
				JRDesignQuery designQuery = new JRDesignQuery();
				if (individuel.isSelected()) {
					jasperDesign = JRXmlLoader.load("C:\\wamp\\listPermission.jrxml");
					Personel personel = permissionTab.getSelectionModel().getSelectedItem().getPersonel();
					String sql2 = "SELECT  p.date, p.duree, p.motif, p.personel_id, n.nom, n.prenom\r\n"
							+ "FROM permission p\r\n" + "JOIN personnel n ON p.personel_id = n.id\r\n"
							+ "WHERE status= true and personel_id='" + personel.getId() + "'\r\n"
							+ "ORDER BY personel_id";
					designQuery.setText(sql2);
				} else {
					jasperDesign = JRXmlLoader.load("C:\\wamp\\listPermission2.jrxml");
					String sql1 = "SELECT count(p.id_permission) as total_permission, sum(p.duree) as duree, p.motif ,p.personel_id, n.nom, n.prenom\r\n"
							+ "from permission p\r\n" + "join personnel n on p.personel_id=n.id\r\n"
							+ "where status=true\r\n" + "group by personel_id\r\n" + "order by personel_id";
					designQuery.setText(sql1);
				}

				jasperDesign.setQuery(designQuery);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint print = JasperFillManager.fillReport(jasperReport, null, MethodUtilitaire.dbConnect());
				JasperViewer jrviewer = new JasperViewer(print, false);
				// JasperViewer.viewReport(print);
				jrviewer.setVisible(true);
				jrviewer.toFront();
				isPrintButton = false;
				group.setVisible(false);
				individuel.setVisible(false);
				individuel.setSelected(false);
				group.setSelected(false);
				compteur = 0;
			} catch (SQLException | JRException | ClassNotFoundException | NullPointerException e) {
				if (e.getClass().getName().equals("java.lang.NullPointerException"))
					MethodUtilitaire.deleteNoPersonSelectedAlert("No personal selected", "No personal selected",
							"Please select one row in table concerning personal permission and try to print agane");
				else
					MethodUtilitaire.errorMessageAlert("Failed to print", "Failed to print !", e.getMessage());
				System.out.println(e.getClass().getName());
			}
		} else {
			if (compteur == 2) {
				compteur = 0;
				MethodUtilitaire.deleteNoPersonSelectedAlert("Select print mode", "Select print mode",
						"Check group or individual");
			}
		}
	}

	@FXML
	void handleValidateClick(ActionEvent event) throws Exception {
		if (isInputValid()) {
			if (isEditButton == true) {
				if (MethodUtilitaire.confirmationDialog(null, "Confirm to update abscence permission",
						"Confirm to update abscence permission", "update this abscence permission")) {
					Permission newPermission = null;
					Permission permission = permissionRepository.findOne(getId());
					permission.setDate(getDatePermission());
					permission.setMotif(getMotif());
					permission.setDuree(getDuree());
					permission.setStatus(isStudent.isSelected() ? false : true);
					permission.setEtudiant(isStudent.isSelected() ? getEtudiant() : null);
					permission.setPersonel(isStudent.isSelected() ? null : getPersonel());
					newPermission = PermissionServiceImpl.update(permission);
					MethodUtilitaire.saveAlert(newPermission, "permission has update successful",
							"permission number " + newPermission.getId_permission() + " has update successful");
					clearField();
					loadDataOntablepermissionTab();
				}
			} else {
				if (MethodUtilitaire.confirmationDialog(null, "Confirm to save abscence permission",
						"Confirm to save abscence permission", "Save this abscence permission")) {
					try {
						Permission newPermission = null;
						Permission permission = new Permission();
						permission.setDate(getDatePermission());
						permission.setMotif(getMotif());
						permission.setDuree(getDuree());
						permission.setStatus(isStudent.isSelected() ? false : true);
						permission.setEtudiant(isStudent.isSelected() ? getEtudiant() : null);
						permission.setPersonel(isStudent.isSelected() ? null : getPersonel());
						newPermission = permissionRepository.save(permission);
						MethodUtilitaire.saveAlert(newPermission, "permission has saved successful",
								"permission number " + newPermission.getId_permission() + " has saved successful");
						clearField();
						loadDataOntablepermissionTab();
					} catch (Exception e) {
						MethodUtilitaire.deleteNoPersonSelectedAlert("Student/Personal is not exist",
								"Student/Personal is not exist", "This Student/Personal is not exist!!");
					}
				}
			}

		}
	}

	private void setFiltreDepartement() {
		departementList.clear();
		List<Departement> list = departementRepository.findAll();
		for (Departement d : list) {
			departementList.add(d.toString());
		}
		departementList.add("All department");
		filterDepartment.setItems(departementList);
		filterDepartment.getSelectionModel().selectLast();
	}

	private void setFiltreClasse() {
		classeList.clear();
		List<Classe> list = classeRepository.findAll();
		for (Classe c : list) {
			classeList.add(c.getNom());
		}
		classeList.add("All class");
		filterClasse.setItems(classeList);
		filterClasse.getSelectionModel().selectLast();
	}

	private void convertFormatDateAllPicker() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		date.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filterPermission.setItems(setFiltre);
		filterPermission.getSelectionModel().selectFirst();
		convertFormatDateAllPicker();
		setFiltreDepartement();
		setFiltreClasse();
		setPermissionTbaPropertiesValue();
		setstaffTabPropertiesValue();
		setstudentTabPropertiesValue();
		permissionTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Permission>() {

			@Override
			public void changed(ObservableValue<? extends Permission> observable, Permission oldValue,
					Permission newValue) {
				showPermissionDetail(newValue);
			}
		});
	}

	private void setPermissionTbaPropertiesValue() {
		idAbscenceC.setCellValueFactory(new PropertyValueFactory<>("id_permission"));
		dateC.setCellValueFactory(new PropertyValueFactory<>("date"));
		motifC.setCellValueFactory(new PropertyValueFactory<>("motif"));
		nomC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Permission, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Permission, String> param) {
						if (param.getValue().isStatus() == true) {
							return new SimpleStringProperty(param.getValue().getPersonel().getNom());
						} else
							return new SimpleStringProperty(param.getValue().getEtudiant().getNom());
					}
				});
		prenomC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Permission, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Permission, String> param) {
						if (param.getValue().isStatus() == true) {
							return new SimpleStringProperty(param.getValue().getPersonel().getPrenom());
						} else
							return new SimpleStringProperty(param.getValue().getEtudiant().getPrenom());
					}
				});
	}

	private void setstudentTabPropertiesValue() {
		idStudent.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("nom"));
		surname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	}

	private void setstaffTabPropertiesValue() {
		idWorker.setCellValueFactory(new PropertyValueFactory<>("id"));
		nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	}

	private Long getId() {
		return Long.parseLong(idAbscence.getText());
	}

	private String getDate() {
		return date.getEditor().getText();
	}

	private Date getDatePermission() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(getDate());
		return date;
	}

	private String getMotif() {
		return motif.getText();
	}

	private Long getIdTransmitter() {
		Long t = null;
		try {
			t = Long.parseLong(idTransmitter.getText());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	private Etudiant getEtudiant() throws Exception {
		Etudiant etudiant = studentRepository.findOne(getIdTransmitter());
		if (etudiant == null)
			throw new Exception("Etudiant inexistant");
		return etudiant;
	}

	private Personel getPersonel() throws Exception {
		Personel personel = staffRepository.findOne(getIdTransmitter());
		if (personel == null)
			throw new Exception("Personel inexistant");
		return personel;
	}

	private int getDuree() {
		int t = 0;
		try {
			t = Integer.parseInt(duree.getText());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	private boolean getStatus() {
		boolean status = false;
		if (isStudent.isSelected())
			status = true;
		if (isEmployee.isSelected())
			status = true;
		return status;
	}

	private void loadDataOntablepermissionTab() {
		permissionList.clear();
		if (filterPermission.getSelectionModel().getSelectedItem() == "No limit") {
			permissionList.addAll(permissionRepository.findAll());
			permissionTab.setItems(permissionList);
		} else {
			permissionList.addAll(PermissionServiceImpl
					.findAllLimitBy(Integer.parseInt((String) filterPermission.getSelectionModel().getSelectedItem())));
			permissionTab.setItems(permissionList);
		}
	}

	private void loadDataOntablestudentTab() {
		etudiantList.clear();
		if (filterClasse.getSelectionModel().getSelectedItem() == "All class") {
			etudiantList.addAll(studentRepository.findAll());
			studentTab.setItems(etudiantList);
		} else {
			etudiantList.addAll(studentRepository.findByClasse(filterClasse.getSelectionModel().getSelectedItem()));
			studentTab.setItems(etudiantList);
		}
	}

	private void loadDataOntablestaffTab() {
		personelList.clear();
		if (filterDepartment.getSelectionModel().getSelectedItem() == "All department") {
			personelList.addAll(staffRepository.findAll());
			staffTab.setItems(personelList);
		} else {
			// personelList.addAll(
			// staffRepository.findByDepartementName(filterDepartment.getSelectionModel().getSelectedItem()));
			staffTab.setItems(personelList);
		}
	}

	private void clearField() {
		idAbscence.clear();
		date.getEditor().clear();
		motif.clear();
		idTransmitter.clear();
		duree.clear();
		isStudent.setSelected(false);
		isEmployee.setSelected(false);
		isEditButton = false;
	}

	private void showPermissionDetail(Permission newValue) {
		if (newValue != null) {
			idAbscenceLabel.setText(newValue.getId_permission().toString());
			dateLabel.setText(newValue.getDate().toString());
			motifLabel.setText(newValue.getMotif());
			boolean mouchard = newValue.isStatus();
			if (mouchard == true) {
				idTransmitterLabel.setText(newValue.getPersonel().getId().toString());
				nomLabel.setText(newValue.getPersonel().getNom());
				prenomLabel.setText(newValue.getPersonel().getPrenom());
			} else {
				idTransmitterLabel.setText(newValue.getEtudiant().getId().toString());
				nomLabel.setText(newValue.getEtudiant().getNom());
				prenomLabel.setText(newValue.getEtudiant().getPrenom());
			}
			dureeLabel.setText(String.valueOf(newValue.getDuree()));
			int totalDuree = 0;
			int totalPermission = 0;
			if (mouchard == true) {
				totalDuree = permissionRepository.getTotalDureePersonel(newValue.getPersonel().getId());
				totalPermission = permissionRepository.getTotalPermissionPersonnel(newValue.getPersonel().getId());
				totalPermissionLabel.setText(String.valueOf(totalPermission));
				totalDureeLabel.setText(String.valueOf(totalDuree));
			} else {
				totalDuree = permissionRepository.getTotalDureeEtudiant(newValue.getEtudiant().getId());
				totalPermission = permissionRepository.getTotalPermissionEtudiant(newValue.getEtudiant().getId());
				totalPermissionLabel.setText(String.valueOf(totalPermission));
				totalDureeLabel.setText(String.valueOf(totalDuree));
			}
		} else {
			idAbscenceLabel.setText(null);
			dateLabel.setText(null);
			motifLabel.setText(null);
			idTransmitterLabel.setText(null);
			nomLabel.setText(null);
			prenomLabel.setText(null);
			dureeLabel.setText(null);
			totalPermissionLabel.setText(null);
			totalDureeLabel.setText(null);
		}

	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getDate() == null || getDate().length() == 0) {
			errorMessage += "No valid field date!\n";
		}

		if (getStatus() == false) {
			errorMessage += "No valid field Is student/Is employee!\n";
		}

		if (getMotif() == null || getMotif().length() == 0) {
			errorMessage += "No valid field motif!\n";
		}

		if (getIdTransmitter() == null || getIdTransmitter() == 0) {
			errorMessage += "No valid field Id transmitter!\n";
		}

		if (getDuree() == 0) {
			errorMessage += "No valid field Id duree!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

}
