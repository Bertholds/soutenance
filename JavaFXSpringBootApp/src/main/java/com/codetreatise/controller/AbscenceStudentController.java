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

import com.codetreatise.bean.AbscenceStudent;
import com.codetreatise.bean.Etudiant;
import com.codetreatise.repository.AbscenceStudentRepository;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.AbscenceStudentServiceImpl;

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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
public class AbscenceStudentController implements Initializable {
	@FXML
	private TableView<AbscenceStudent> abscenceTab;
	@FXML
	private TableColumn<AbscenceStudent, Long> idAbscenceC;
	@FXML
	private TableColumn<AbscenceStudent, Date> dateC;
	@FXML
	private TableColumn<AbscenceStudent, Integer> quantiteC;
	@FXML
	private TableColumn<AbscenceStudent, Integer> justifierC;
	@FXML
	private TableColumn<AbscenceStudent, String> idEtudiantC;
	@FXML
	private TableColumn<AbscenceStudent, String> nomC;
	@FXML
	private TableColumn<AbscenceStudent, String> prenomC;
	@FXML
	private TableColumn<AbscenceStudent, String> classeC;
	@FXML
	private TextField idAbscence;
	@FXML
	private TextField recherche;
	@FXML
	private TextField recherche2;
	@FXML
	private TextField quantite;
	@FXML
	private DatePicker date;
	@FXML
	private DatePicker dateDepart;
	@FXML
	private DatePicker dateLimit;
	@FXML
	private Spinner<Integer> etudiants;
	@FXML
	private TextField justifier;
	@FXML
	private ComboBox<String> filterClasseTab1;
	@FXML
	private TableView<Etudiant> studentTab;
	@FXML
	private TableColumn<Etudiant, Integer> idStudent;
	@FXML
	private TableColumn<Etudiant, String> nameC;
	@FXML
	private TableColumn<Etudiant, String> surnameC;
	@FXML
	private TableColumn<Etudiant, String> classC;
	@FXML
	private ComboBox<String> filterClasseTab2;
	@FXML
	private Label idStudentLabel;
	@FXML
	private Label nomLabel;
	@FXML
	private Label prenomLabel;
	@FXML
	private Label classeLabel;

	@FXML
	private Label totalAnnuelLabel;
	@FXML
	private Label idAbscenceLabel;
	@FXML
	private Label totalAnnuelJustifier ;
	@FXML
	private Label abscenceLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label totalMois;
	@FXML
	private Label justifierLabel;
	@FXML
	private Label totalJustifierMoisLabel;

	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private AbscenceStudentRepository abscenceStudentRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private AbscenceStudentServiceImpl abscenceStudentServiceImpl;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	ObservableList<String> classeTab1List = FXCollections.observableArrayList();
	ObservableList<AbscenceStudent> abscenceTabList = FXCollections.observableArrayList();
	ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0);

	private void setFilterClasseTab1() {
		classeTab1List.clear();
		List<String> list = classeRepository.loadAllClasse();
		classeTab1List.addAll(list);
		classeTab1List.add("All class");
		filterClasseTab1.setItems(classeTab1List);
		filterClasseTab2.setItems(classeTab1List);
		filterClasseTab1.getSelectionModel().selectLast();
		filterClasseTab2.getSelectionModel().selectLast();
	}
	
	private void convertFormatDateAllPicker() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
	
		dateDepart.setConverter(new StringConverter<LocalDate>() {
			
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

dateLimit.setConverter(new StringConverter<LocalDate>() {
	
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

	// Event Listener on Button.onAction
	@FXML
	public void handleClearFieldClick(ActionEvent event) {
		cleanField();
	}

	// Event Listener on Button.onAction
	@SuppressWarnings("deprecation")
	@FXML
	public void handleValidateClick(ActionEvent event) throws ParseException {
		if (isInputValid()) {
			if (MethodUtilitaire.confirmationDialog(null, "Confirm to save this record", "Confirm to save this record",
					"Confirm to save record concerning " + getEtudiantById().getNom() + " "
							+ getEtudiantById().getPrenom() + " whith " + getQuantite() + " abscence hour ?")) {
				AbscenceStudent abscenceStudent = new AbscenceStudent();

				Etudiant etudiant = getEtudiantById();
				abscenceStudent.setEtudiant(etudiant);
				abscenceStudent.setDates(getDateAbscence());
				abscenceStudent.setQuantite(getQuantite());
				abscenceStudent.setJustifier(0);
				abscenceStudent.setMois(getDateAbscence().getMonth());
				AbscenceStudent newAbscenceStudent = abscenceStudentRepository.save(abscenceStudent);
				MethodUtilitaire.saveAlert(newAbscenceStudent, "Record save successfull",
						"Record concerning " + newAbscenceStudent.getEtudiant().getNom() + " "
								+ newAbscenceStudent.getEtudiant().getPrenom() + " save successfull");

				cleanField();
				loadDataOnTable1();
				clearLabel();
			}
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleJustifierClick(ActionEvent event) {
		if (isInputValid()) {
			if (justifier.getText() != null && justifier.getText().length() != 0) {
				try {
					if (MethodUtilitaire.confirmationDialog(null, "Confirm to update this record",
							"Confirm to update this record",
							"Confirm to update record concerning " + getEtudiantById().getNom() + " "
									+ getEtudiantById().getPrenom() + " whith " + getQuantite() + " abscence hour ?")) {

						AbscenceStudent abscenceStudent = handleJustificationClick(event);
						abscenceStudent.setJustifier(Integer.parseInt(justifier.getText()));
						abscenceStudentServiceImpl.update(abscenceStudent);
						MethodUtilitaire.saveAlert(null, "Record updated successfull",
								"Record concerning " + abscenceStudent.getEtudiant().getNom() + " "
										+ abscenceStudent.getEtudiant().getPrenom() + " updated successfull");
						cleanField();
						loadDataOnTable1();
						setEditable();
					}
				} catch (Exception e) {
					e.printStackTrace();
					MethodUtilitaire.deleteNoPersonSelectedAlert("Invalid field", "Invalid field",
							"please correct field justifier");
				}
			} else
				MethodUtilitaire.deleteNoPersonSelectedAlert("Invalid field", "Empty field",
						"please insert data in field justifier");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshClickTab1(ActionEvent event) {
		loadDataOnTable1();
	}

	// Event Listener on TextField.onKeyReleased
	@FXML
	public void handleFilterTab1(KeyEvent event) {
		FilteredList<AbscenceStudent> filteredAbscenceStudent = new FilteredList<AbscenceStudent>(abscenceTabList,
				e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredAbscenceStudent.setPredicate((Predicate<? super AbscenceStudent>) abscence -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();

					if (abscence.getEtudiant().getClasse().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (abscence.getEtudiant().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (abscence.getEtudiant().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (abscence.getEtudiant().getId().toString().contains(newValueFilter)) {
						return true;
					} else if (abscence.getDates().toString().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<AbscenceStudent> sortedList = new SortedList<AbscenceStudent>(filteredAbscenceStudent);
		sortedList.comparatorProperty().bind(abscenceTab.comparatorProperty());
		abscenceTab.setItems(sortedList);
	}

	// Event Listener on Button.onAction
	@FXML
	public AbscenceStudent handleJustificationClick(ActionEvent event) {
		AbscenceStudent selectedAbscence = abscenceTab.getSelectionModel().getSelectedItem();
		if (selectedAbscence != null) {
			etudiants.getEditor().setDisable(true);
			date.getEditor().setEditable(false);
			quantite.setEditable(false);

			idAbscence.setText(selectedAbscence.getId_abscence().toString());
			etudiants.getEditor().setText(selectedAbscence.getEtudiant().getId().toString());
			date.getEditor().setText(selectedAbscence.getDates().toString());
			quantite.setText(String.valueOf(selectedAbscence.getQuantite()));
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No record selected", "No record selected",
					"Please select firstly one record");
		return selectedAbscence;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshClickTab2(ActionEvent event) {
		etudiantList.clear();
		if (filterClasseTab2.getSelectionModel().getSelectedItem() == "All class") {
			etudiantList.addAll(studentRepository.findAll());
			studentTab.setItems(etudiantList);
		} else {
			etudiantList.addAll(studentRepository.findClasse(filterClasseTab2.getSelectionModel().getSelectedItem()));
			studentTab.setItems(etudiantList);
		}
	}

	@FXML
	public void handlePrintClick(ActionEvent event) throws ParseException {
		try {
			String d1 = dateDepart.getEditor().getText();
			String d2 = dateLimit.getEditor().getText();
			if((d1==null || d1.length()==0) || (d2==null || d2.length()==0))
				throw new Exception("Please enter valid date before to print");
			try {
				System.setProperty("java.awt.headless", "false");
				JasperDesign jasperDesign = JRXmlLoader.load("C:\\wamp\\listAbscence.jrxml");
				String sql = "SELECT MAX( a.id_abscence ) as id_abscence , SUM( a.quantite ) as quantite , SUM( a.justifier ) as justifier , a.etudiant_id,\r\n"
						+ "e.nom, e.prenom, c.name\r\n" + "FROM abscence_student a\r\n"
						+ "join etudiant e on a.etudiant_id=e.id\r\n" + "join classe c on e.id_classe=c.id_classe\r\n"
						+ "WHERE a.dates\r\n" + "BETWEEN  '" + d1.toString() + "'\r\n" + "AND  '" + d2.toString() + "' and c.name='" + filterClasseTab1.getSelectionModel().getSelectedItem() + "' \r\n"
						+ "group by `etudiant_id`\r\n" + "";
				JRDesignQuery designQuery = new JRDesignQuery();
				designQuery.setText(sql);
				jasperDesign.setQuery(designQuery);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint print = JasperFillManager.fillReport(jasperReport, null, MethodUtilitaire.dbConnect());
				JasperViewer jrviewer = new JasperViewer(print, false);
				//JasperViewer.viewReport(print);
				jrviewer.setVisible(true);
				jrviewer.toFront();
			} catch (SQLException | JRException | ClassNotFoundException e) {
				e.printStackTrace();
				MethodUtilitaire.errorMessageAlert("Failed to print", "Failed to print !", e.getMessage());
			}
		} catch (Exception e) {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Date format error", "Date format error",
					"Please enter valid date before to print");
		}
	}

	// Event Listener on TextField.onKeyReleased
	@FXML
	public void handleFilterTab2(KeyEvent event) {
		FilteredList<Etudiant> filteredetudiants = new FilteredList<Etudiant>(etudiantList, e -> true);
		recherche2.setOnKeyReleased(e -> {
			recherche2.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredetudiants.setPredicate((Predicate<? super Etudiant>) etudiant -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (etudiant.getId().toString().contains(newValueFilter)) {
						return true;
					} else if (etudiant.getMatricule().toLowerCase().contains(newValueFilter)) {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		etudiants.setValueFactory(valueFactory);
		etudiants.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		setFilterClasseTab1();
		setColumPropertiesTab1();
		setColumPropertiesTab2();
		addListener();
		convertFormatDateAllPicker();
	}

	private void setColumPropertiesTab1() {
		idAbscenceC.setCellValueFactory(new PropertyValueFactory<>("id_abscence"));
		dateC.setCellValueFactory(new PropertyValueFactory<>("dates"));
		quantiteC.setCellValueFactory(new PropertyValueFactory<>("quantite"));
		justifierC.setCellValueFactory(new PropertyValueFactory<>("justifier"));
		idEtudiantC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AbscenceStudent, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AbscenceStudent, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getId().toString());
					}
				});
		nomC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AbscenceStudent, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AbscenceStudent, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getNom());
					}
				});
		prenomC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AbscenceStudent, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AbscenceStudent, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getPrenom());
					}
				});
		classeC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AbscenceStudent, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<AbscenceStudent, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getClasse().getNom());
					}
				});
	}

	private void setColumPropertiesTab2() {
		idStudent.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameC.setCellValueFactory(new PropertyValueFactory<>("nom"));
		surnameC.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		classC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Etudiant, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Etudiant, String> param) {
						return new SimpleStringProperty(param.getValue().getClasse().getNom());
					}
				});
	}

	private void addListener() {
		abscenceTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AbscenceStudent>() {

			@Override
			public void changed(ObservableValue<? extends AbscenceStudent> observable, AbscenceStudent oldValue,
					AbscenceStudent newValue) {
				showAbscenceDetail(newValue);
			}
		});
	}

	private void loadDataOnTable1() {
		abscenceTabList.clear();
		if (filterClasseTab1.getSelectionModel().getSelectedItem() == "All class") {
			List<AbscenceStudent> list = abscenceStudentRepository.findAll();
			abscenceTabList.addAll(list);
		} else {
			List<AbscenceStudent> list = abscenceStudentRepository
					.findByClasse(filterClasseTab1.getSelectionModel().getSelectedItem());
			abscenceTabList.addAll(list);
		}
		abscenceTab.setItems(abscenceTabList);
	}

	private String getDate() {
		return date.getEditor().getText();
	}

	private Date getDateAbscence() throws ParseException {
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy
		// hh:mm:ss");
		Date date = format.parse(getDate());
		return date;
	}

	private int getQuantite() {
		int qte = 0;
		try {
			qte = Integer.parseInt(quantite.getText());
		} catch (Exception e) {

		}
		return qte;
	}

	private int getEtudiant() {
		int id = 0;
		try {
			id = Integer.parseInt(etudiants.getEditor().getText());
		} catch (Exception e) {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Etudiant non existant", "Etudiant non existant",
					"L'etudiant identifié par " + etudiants.getEditor().getText() + " est inexistant");
		}
		return id;
	}

	private Etudiant getEtudiantById() {
		Etudiant etudiant = null;
		try {
			etudiant = studentRepository.findOne((long) getEtudiant());
		} catch (Exception e) {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Etudiant non existant", "Etudiant non existant",
					"L'etudiant portant l'identifiant " + getEtudiant() + " n'existe pas");
		}
		return etudiant;
	}

	private void cleanField() {
		idAbscence.clear();
		etudiants.getEditor().setText("0");
		date.getEditor().clear();
		quantite.clear();
		justifier.clear();

		setEditable();
	}

	private void setEditable() {
		etudiants.getEditor().setDisable(false);
		date.getEditor().setEditable(true);
		quantite.setEditable(true);
	}

	@SuppressWarnings("deprecation")
	private void showAbscenceDetail(AbscenceStudent abscenceStudent) {
		if (abscenceStudent != null) {
			int total = abscenceStudentRepository.getTotalSum(abscenceStudent.getEtudiant().getId());
			int totalAJ = abscenceStudentRepository.getTotalJustifierAnnuelsSum(abscenceStudent.getEtudiant().getId());
			int totalM = abscenceStudentRepository.getTotalMoisSum(abscenceStudent.getEtudiant().getId(), abscenceStudent.getDates().getMonth());
			int totalJ = abscenceStudentRepository.getTotalJustifierMoisSum(abscenceStudent.getEtudiant().getId(),
					abscenceStudent.getDates().getMonth());
			idStudentLabel.setText(abscenceStudent.getEtudiant().getId().toString());
			nomLabel.setText(abscenceStudent.getEtudiant().getNom());
			prenomLabel.setText(abscenceStudent.getEtudiant().getPrenom());
			classeLabel.setText(abscenceStudent.getEtudiant().getClasse().getNom());
			idAbscenceLabel.setText(abscenceStudent.getId_abscence().toString());
			justifierLabel.setText(String.valueOf(abscenceStudent.getJustifier()));
			abscenceLabel.setText(String.valueOf(abscenceStudent.getQuantite()));
			totalAnnuelLabel.setText(String.valueOf(total));
			totalJustifierMoisLabel.setText(String.valueOf(totalJ));
			dateLabel.setText(abscenceStudent.getDates().toString());
			totalMois.setText(String.valueOf(totalM));
			totalAnnuelJustifier.setText(String.valueOf(totalAJ));
			
		} else
			clearLabel();
	}

	private void clearLabel() {
		idStudentLabel.setText(null);
		nomLabel.setText(null);
		prenomLabel.setText(null);
		classeLabel.setText(null);
		idAbscenceLabel.setText(null);
		justifierLabel.setText(null);
		abscenceLabel.setText(null);
		totalAnnuelLabel.setText(null);
		totalJustifierMoisLabel.setText(null);
		dateLabel.setText(null);
		totalMois.setText(null);
		totalMois.setText(null);
		totalAnnuelJustifier.setText(null);
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getDate() == null || getDate().length() == 0) {
			errorMessage += "No valid field date!\n";
		}
		if (getEtudiant() == 0) {
			errorMessage += "No valid field etudiant id!\n";
		}
		if (getQuantite() == 0) {
			errorMessage += "No valid field quantite!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}
}
