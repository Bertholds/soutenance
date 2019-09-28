package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Etudiant;
import com.codetreatise.bean.Inscription;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.InscriptionRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.InscriptionServiceImpl;
import com.codetreatise.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
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
public class InscriptionController implements Initializable {

	@FXML
	private MenuItem handleRegisterClick;

	@FXML
	private TableView<Inscription> inscriptionTab;

	@FXML
	private TableColumn<Inscription, Long> id_inscription;

	@FXML
	private TableColumn<Inscription, String> id_students;

	@FXML
	private TableColumn<Inscription, String> nom;

	@FXML
	private TableColumn<Inscription, String> prenom;

	@FXML
	private TableColumn<Inscription, Double> tranche1c;

	@FXML
	private TableColumn<Inscription, Double> tranche2c;

	@FXML
	private TableColumn<Inscription, Double> tranche3c;

	@FXML
	private TableColumn<Inscription, Double> tranche4c;

	@FXML
	private TableColumn<Inscription, Double> totalMontant;

	@FXML
	private TableColumn<Inscription, String> classe;

	@FXML
	private TextField id;

	@FXML
	private Spinner<Integer> id_student;

	@FXML
	private TextField tranche1;

	@FXML
	private TextField tranche2;

	@FXML
	private TextField tranche3;

	@FXML
	private TextField tranche4;

	@FXML
	private Label totalTranche1;

	@FXML
	private Label totalMontant1;

	@FXML
	private Label totalTranche2;

	@FXML
	private Label totalMontant2;

	@FXML
	private Label totalMontant3;

	@FXML
	private Label totalTranche3;

	@FXML
	private Label totalMontant4;

	@FXML
	private Label totalTranche4;

	@FXML
	private TextField recherche;

	@FXML
	private Label total;

	@FXML
	private BarChart<String, Integer> BarChart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	@FXML
	private Button btnRegister;

	@FXML
	private MenuItem edit;

	Integer total1;
	Integer total2;
	Integer total3;
	Integer total4;

	boolean isEditClick = false;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private InscriptionRepository inscriptionRepository;

	@Autowired
	private InscriptionServiceImpl inscriptionServiceImpl;

	@Autowired
	private MethodUtilitaire methodUtilitaire;

	// Value factory.
	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0);
	private ObservableList<Inscription> inscriptionList = FXCollections.observableArrayList();
	private ObservableList<String> TrancheName = FXCollections.observableArrayList();
	private String[] tranche = { "T1", "T2", "T3", "T4" };

	@FXML
	void handleAboutClick(ActionEvent event) {

	}

	@FXML
	void handleCloseClick(ActionEvent event) {
		if (MethodUtilitaire.confirmationDialog(null, "Close a current windows", "Close windows",
				"do you want to close this windows ?")) {
			System.out.println(",,,,,,,,,,,,,,");
			new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
//					Node node = (Node) event.getSource();
//					Stage stage = (Stage) node.getScene().getWindow();
//					stage.close();
					System.out.println(",,,,,,,,,,,,,,");

				}
			};
		}
	}

	@FXML
	void handleEditClick(ActionEvent event) throws IOException, Exception {
		Inscription inscription = inscriptionTab.getSelectionModel().getSelectedItem();
		if (inscription != null) {
			if (isEditClick == false) {
				this.isEditClick = true;
				id.setText(inscription.getId_inscription().toString());
				id_student.getEditor().setText(inscription.getEtudiant().getId().toString());
				tranche1.setText(String.valueOf(inscription.getTranche1()));
				tranche2.setText(String.valueOf(inscription.getTranche2()));
				tranche3.setText(String.valueOf(inscription.getTranche3()));
				tranche4.setText(String.valueOf(inscription.getTranche4()));
				edit.setText("Commit this modification");
				id_student.setDisable(true);
				id.setDisable(true);
				btnRegister.setDisable(true);
			} else {

				if (MethodUtilitaire.confirmationDialog(inscription, "Confirm to edit a register", "Edit a register",
						"do you want to edit register number " + inscription.getId_inscription()
								+ " concerning a student " + inscription.getEtudiant().getNom() + " " + inscription.getEtudiant().getPrenom()
								+ " ?")) {
					inscription.setEtudiant(getEtudiant());
//					inscription.setId_etudiant(getEtudiant().getId());
//					inscription.setNom(getEtudiant().getNom());
//					inscription.setPrenom(getEtudiant().getPrenom());
//					inscription.setClasse(getEtudiant().getClasse().getNom());
					inscription.setTranche1(getT1());
					inscription.setTranche2(getT2());
					inscription.setTranche3(getT3());
					inscription.setTranche4(getT4());
					inscription.setTotal(getT1() + getT2() + getT3() + getT4());
					if (getT1() == 0 || getT1() == 0.0 || getT1() == 0.00) {
						inscription.setDateT1(null);
					} else
						inscription.setDateT1(new Date());
					if (getT2() == 0 || getT2() == 0.0 || getT2() == 0.00) {
						inscription.setDateT2(null);
					} else
						inscription.setDateT2(new Date());
					if (getT3() == 0 || getT3() == 0.0 || getT3() == 0.00) {
						inscription.setDateT3(null);
					} else
						inscription.setDateT3(new Date());
					if (getT4() == 0 || getT4() == 0.0 || getT4() == 0.00) {
						inscription.setDateT4(null);
					} else
						inscription.setDateT4(new Date());
					Inscription newInscription = inscriptionServiceImpl.update(inscription);
					MethodUtilitaire.saveAlert(newInscription, "Register update successfully",
							"The Register " + inscription.getId_inscription() + " concerning  "
									+ inscription.getEtudiant().getNom() + " " + inscription.getEtudiant().getPrenom()
									+ " has been updated ");
					edit.setText("Edit");
					id_student.setDisable(false);
					id.setDisable(false);
					btnRegister.setDisable(false);
					clearField();
					this.isEditClick = false;
					loadDataOntable();
					setTotalTranche();
					setTotalMontant();
					barcharOperation();
					methodUtilitaire.LogFile(
							"Operation de modification de l'inscription", "id: " + inscription.getId_inscription() + " "
									+ inscription.getEtudiant().getNom() + " " + inscription.getEtudiant().getPrenom(),
							MethodUtilitaire.deserializationUser());
				} else {
					isEditClick = false;
					edit.setText("Edit");
					clearField();
					btnRegister.setDisable(false);
					id.setDisable(false);
					id_student.setDisable(false);
					new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Node node = (Node) event.getSource();
							Stage stage = (Stage) node.getScene().getWindow();
							stage.close();
						}
					};
				}
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No Register Selected",
					"Please select a Register in the table.");
		}
	}

	@FXML
	private void handleNewFileClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.INSCRIPTION);
	}

	@FXML
	private void handlePreinscriptionClick(ActionEvent event) {
		stageManager.switchScene(FxmlView.PREINSCRIPTION);
	}

	@FXML
	private void handleRegisterClick(ActionEvent event) {
		if (isInputValid()) {
			Inscription inscription = new Inscription();
			if (MethodUtilitaire.confirmationDialog(inscription, "Create a new register", "Create a new register ?",
					"do you want to create this register ? ")) {
				try {
					inscription.setEtudiant(getEtudiant());
//					inscription.setId_etudiant(getEtudiant().getId());
//					inscription.setNom(getEtudiant().getNom());
//					inscription.setPrenom(getEtudiant().getPrenom());
//					inscription.setClasse(getEtudiant().getClasse().getNom());
					inscription.setTranche1(getT1());
					inscription.setTranche2(getT2());
					inscription.setTranche3(getT3());
					inscription.setTranche4(getT4());
					inscription.setTotal(getT1() + getT2() + getT3() + getT4());
					if (getT1() == 0 || getT1() == 0.0 || getT1() == 0.00) {
						inscription.setDateT1(null);
					} else
						inscription.setDateT1(new Date());
					if (getT2() == 0 || getT2() == 0.0 || getT2() == 0.00) {
						inscription.setDateT2(null);
					} else
						inscription.setDateT2(new Date());
					if (getT3() == 0 || getT3() == 0.0 || getT3() == 0.00) {
						inscription.setDateT3(null);
					} else
						inscription.setDateT3(new Date());
					if (getT4() == 0 || getT4() == 0.0 || getT4() == 0.00) {
						inscription.setDateT4(null);
					} else
						inscription.setDateT4(new Date());
					Inscription newInscription = inscriptionRepository.save(inscription);
					clearField();
					MethodUtilitaire.saveAlert(newInscription, "Register save successfully",
							"The Register " + inscription.getId_inscription() + " concerning  "
									+ inscription.getEtudiant().getNom() + " " + inscription.getEtudiant().getPrenom()
									+ " has been created ");
					loadDataOntable();
					setTotalTranche();
					setTotalMontant();
					barcharOperation();
				} catch (Exception e) {
					MethodUtilitaire.deleteNoPersonSelectedAlert("Student is not exist", "Student is not exist", "This student is not exist!!");
				}
			} else {
				new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Node node = (Node) event.getSource();
						Stage stage = (Stage) node.getScene().getWindow();
						stage.close();
					}
				};
			}
		}
	}

	@FXML
	void handleRemoveClick(ActionEvent event) {
		int selectedIndex = inscriptionTab.getSelectionModel().getSelectedIndex();
		Inscription inscription = inscriptionTab.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			if (MethodUtilitaire.confirmationDialog(null, "Delete a register", "Delete a register ? ",
					"Delele register number " + inscription.getId_inscription() + " concerning " + inscription.getEtudiant().getNom()
							+ " " + inscription.getEtudiant().getPrenom()) == true) {
				inscriptionTab.getItems().remove(selectedIndex);
				inscriptionRepository.delete(inscription.getId_inscription());
				loadDataOntable();
				setTotalTranche();
				setTotalMontant();
				barcharOperation();
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Any selection", "no register selected",
					"Please select a register in the table.");
		}
	}

	@FXML
	void handleRefreshClick(ActionEvent event) {
		loadDataOntable();
	}

	@FXML
	void handlePrintListClick(ActionEvent event)   {
		try {
			System.setProperty("java.awt.headless", "false");
			JasperDesign jasperDesign = JRXmlLoader.load("C:\\wamp\\listInscrit.jrxml");
			String sql = "SELECT i.id_inscription, i.tranche1, i.tranche2, i.tranche3, i.tranche4, e.nom, e.prenom, c.name, i.total\r\n" + 
					"FROM etudiant e, inscription i, classe c where e.id=i.etudiant_id and e.id_classe=c.id_classe and c.name='"+recherche.getText()+"'";
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
	}

	@SuppressWarnings("unlikely-arg-type")
	@FXML
	private void filteredTable(KeyEvent event) {

		FilteredList<Inscription> filteredetudiants = new FilteredList<Inscription>(inscriptionList, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredetudiants.setPredicate((Predicate<? super Inscription>) inscription -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();

					if (inscription.getEtudiant().getClasse().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getId().toString().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Inscription> sortedList = new SortedList<Inscription>(filteredetudiants);
		sortedList.comparatorProperty().bind(inscriptionTab.comparatorProperty());
		inscriptionTab.setItems(sortedList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setColumProperties();
		id_student.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		id_student.setValueFactory(valueFactory);
		setTotalMontant();
		setTotalTranche();
		barcharOperation();
	}

	private void setTotalTranche() {
		total1 = inscriptionRepository.getTotalTranche1();
		totalTranche1.setText(total1.toString());

		total2 = inscriptionRepository.getTotalTranche2();
		totalTranche2.setText(total2.toString());

		total3 = inscriptionRepository.getTotalTranche3();
		totalTranche3.setText(total3.toString());

		total4 = inscriptionRepository.getTotalTranche4();
		totalTranche4.setText(total4.toString());
	}

	private void setTotalMontant() {
		Double montan = inscriptionRepository.getTotalMontant1();
		Double montant2 = inscriptionRepository.getTotalMontant2();
		Double montant3 = inscriptionRepository.getTotalMontant3();
		Double montant4 = inscriptionRepository.getTotalMontant4();
		Double totalT = inscriptionRepository.getTotal();
		if (montan == null && montant2 == null && montant3 == null && montant4 == null) {
			totalMontant1.setText("0");
			totalMontant2.setText("0");
			totalMontant3.setText("0");
			totalMontant4.setText("0");
			total.setText("0");
		} else {
			totalMontant1.setText(montan.toString());
			totalMontant2.setText(montant2.toString());
			totalMontant3.setText(montant3.toString());
			totalMontant4.setText(montant4.toString());
			total.setText(totalT.toString());
		}
	}

	private void loadDataOntable() {
		inscriptionList.clear();
		inscriptionList.addAll(inscriptionRepository.findAll());
		inscriptionTab.setItems(inscriptionList);
	}

	private void setColumProperties() {
		id_inscription.setCellValueFactory(new PropertyValueFactory<>("id_inscription"));
		id_students.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Inscription, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getId().toString());
			}
		});
		nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Inscription, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getNom());
			}
		});
		prenom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Inscription, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getPrenom());
			}
		});
		classe.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inscription,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Inscription, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getClasse().getNom());
			}
		});
		tranche1c.setCellValueFactory(new PropertyValueFactory<>("tranche1"));
		tranche2c.setCellValueFactory(new PropertyValueFactory<>("tranche2"));
		tranche3c.setCellValueFactory(new PropertyValueFactory<>("tranche3"));
		tranche4c.setCellValueFactory(new PropertyValueFactory<>("tranche4"));
		totalMontant.setCellValueFactory(new PropertyValueFactory<>("total"));
	}

	private void barcharOperation() {
		TrancheName.clear();
		TrancheName.addAll(tranche);
		// Assign the month names as categories for the horizontal axis.
		xAxis.setCategories(TrancheName);
		xAxis.setLabel("Les tranches de la pension");
		yAxis.setLabel(" nombre de d'etudiant ");
		// Set show Tick Marks
		xAxis.setTickMarkVisible(true);

		// Rotate the label of Tick Marks 90 degrees
		xAxis.setTickLabelRotation(90);

		// Set color for lable of Tick marks
		xAxis.setTickLabelFill(Color.RED);

		// Set Font
		xAxis.setTickLabelFont(new Font("Arial", 15));

		// Set legend position.
		BarChart.setLegendSide(Side.LEFT);
		BarChart.setAnimated(true);

		int[] trancheCounter = new int[4];
		int[] totaux = { total1, total2, total3, total4 };
		for (int i = 0; i < trancheCounter.length; i++) {
			int value = totaux[i];
			trancheCounter[i] = value;
		}

		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("Tranches");

		// Create a XYChart.Data object for each tranche. Add it to the series.
		for (int i = 0; i < trancheCounter.length; i++) {
			series.getData().add(new XYChart.Data<String, Integer>(TrancheName.get(i), trancheCounter[i]));
		}

		BarChart.getData().add(series);

	}

	private String getTranche1() {
		return tranche1.getText();
	}

	private String getTranche2() {
		return tranche2.getText();
	}

	private String getTranche3() {
		return tranche3.getText();
	}

	private String getTranche4() {
		return tranche4.getText();
	}

	private String getStudent() {
		return id_student.getEditor().getText();
	}

	private double getT1() {
		return Double.parseDouble(getTranche1());
	}

	private double getT2() {
		return Double.parseDouble(getTranche2());
	}

	private double getT3() {
		return Double.parseDouble(getTranche3());
	}

	private double getT4() {
		return Double.parseDouble(getTranche4());
	}

	private Etudiant getEtudiant() {
		Etudiant etudiant = studentRepository.findOne(Long.parseLong(getStudent()));
		return etudiant;
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getTranche1() == null || getTranche1().length() == 0) {
			errorMessage += "No valid  field tanche 1!\n";
			try {
				getT1();
			} catch (Exception e) {
				parseLongException("tranche 1");
			}
		}
		if (getTranche2() == null || getTranche2().length() == 0) {
			errorMessage += "No valid field tranche 2!\n";
			try {
				getT2();
			} catch (Exception e) {
				parseLongException("tranche 2");
			}
		}
		if (getTranche3() == null || getTranche3().length() == 0) {
			errorMessage += "No valid field tranche 3!\n";
			try {
				getT3();
			} catch (Exception e) {
				parseLongException("tranche 3");
			}
		}
		if (getTranche4() == null || getTranche4().length() == 0) {
			errorMessage += "No valid field tranche 4!\n";
			try {
				getT4();
			} catch (Exception e) {
				parseLongException("tranche 4");
			}
		}
		if (getStudent() == null || getStudent().length() == 0) {
			errorMessage += "No valid field id student!\n";
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

	private void clearField() {
		id.clear();
		id_student.getEditor().setText("0");
		tranche1.setText("0.00");
		tranche2.setText("0.00");
		tranche3.setText("0.00");
		tranche4.setText("0.00");
	}

	private void parseLongException(String tranche) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid data");
		alert.setContentText("the value of the " + tranche + " is not correct\n" + "Please check field and try agane");
		alert.showAndWait();
	}
}
