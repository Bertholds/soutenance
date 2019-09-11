package com.codetreatise.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Etudiant;
import com.codetreatise.bean.Preinscription;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.PreInscriptionRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.preInscriptionServiceImpl;
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
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

@Controller
public class PreinscriptionController implements Initializable {

	@FXML
	private TableView<Preinscription> preinscriptionTab;

	@FXML
	private TableColumn<Preinscription, Long> id_inscription;

	@FXML
	private TableColumn<Preinscription, String> id_students;

	@FXML
	private TableColumn<Preinscription, String> nom;

	@FXML
	private TableColumn<Preinscription, String> prenom;

	@FXML
	private TableColumn<Preinscription, Double> montant;

	@FXML
	private TableColumn<Preinscription, String> niveau;

	@FXML
	private TableColumn<Preinscription, String> classeC;

	@FXML
	private TableColumn<Preinscription, Date> date;

	@FXML
	private TextField id;

	@FXML
	private MenuItem edit;

	@FXML
	private Button btnRegister;

	@FXML
	private Spinner<Integer> id_student;

	@FXML
	private TextField montantPreinscription;

	@FXML
	private Label totalPreinscrit;

	@FXML
	private Label totalMontant;

	@FXML
	private TextField recherche;

	@FXML
	private PieChart pieChart;

	@Autowired
	@Lazy
	private StageManager stageManager;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PreInscriptionRepository preinscriptionRepository;

	@Autowired
	private preInscriptionServiceImpl preinscriptionServiceImpl;

	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0);
	ObservableList<Preinscription> preinscriptionList = FXCollections.observableArrayList();
	ObservableList<Data> data;

	private boolean isEditClick = false;

	@FXML
	void handleAboutClick(ActionEvent event) {

	}

	@FXML
	void handleCloseClick(ActionEvent event) {

	}

	@FXML
	void handleInscriptionClick(ActionEvent event) {
		stageManager.switchScene(FxmlView.INSCRIPTION);
	}

	@FXML
	void handleNewClick(ActionEvent event) {
		stageManager.switchScene(FxmlView.PREINSCRIPTION);
	}

	@FXML
	void handleRegisterClick(ActionEvent event) {

		if (isInputValid()) {
			Preinscription preinscription = new Preinscription();
			if (MethodUtilitaire.confirmationDialog(preinscription, "Create a new register", "Create a new register ?",
					"do you want to create this register ? ")) {
				try {
					preinscription.setEtudiant(getEtudiant());
//					preinscription.setId_etudiant(getEtudiant().getId());
//					preinscription.setNom(getEtudiant().getNom());
//					preinscription.setPrenom(getEtudiant().getPrenom());
//					preinscription.setNiveau(getEtudiant().getClasse().getNiveau());
					preinscription.setMontant(getM());
					preinscription.setDate(new Date());
					Preinscription newpreInscription = preinscriptionRepository.save(preinscription);
					clearField();
					MethodUtilitaire.saveAlert(newpreInscription, "Register save successfully",
							"The Register " + newpreInscription.getId_preinscription() + " concerning  "
									+ newpreInscription.getEtudiant().getNom() + " "
									+ newpreInscription.getEtudiant().getPrenom() + " has been created ");
					loadTotaux();
					loadDataOntable();
					pieChartOperation();
				} catch (Exception e) {
					MethodUtilitaire.deleteNoPersonSelectedAlert("Student is not exist", "Student is not exist",
							"This student is not exist!!");
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
	void handleRemoveClick(ActionEvent event) throws Exception {
		int selectedIndex = preinscriptionTab.getSelectionModel().getSelectedIndex();
		Preinscription preinscription = preinscriptionTab.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			if (MethodUtilitaire.confirmationDialog(preinscription, "Delete a register", "Delete a register ? ",
					"Delele register number " + preinscription.getId_preinscription() + " concerning "
							+ preinscription.getEtudiant().getNom() + " "
							+ preinscription.getEtudiant().getPrenom()) == true) {
				preinscriptionTab.getItems().remove(selectedIndex);
				preinscriptionRepository.delete(preinscription.getId_preinscription());
				loadTotaux();
				loadDataOntable();
				pieChartOperation();
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Any selection", "no register selected",
					"Please select a register in the table.");
		}
	}

	@FXML
	void handleEditClick(ActionEvent event) throws Exception {
		Preinscription preinscription = preinscriptionTab.getSelectionModel().getSelectedItem();
		if (preinscription != null) {
			if (isEditClick == false) {
				this.isEditClick = true;
				id.setText(preinscription.getId_preinscription().toString());
				id_student.getEditor().setText(preinscription.getEtudiant().getId().toString());
				montantPreinscription.setText(String.valueOf(preinscription.getMontant()));
				edit.setText("Commit this modification");
				id_student.setDisable(true);
				id.setDisable(true);
				btnRegister.setDisable(true);
			} else {

				if (MethodUtilitaire.confirmationDialog(preinscription, "Confirm to edit a register", "Edit a register",
						"do you want to edit register number " + preinscription.getId_preinscription()
								+ " concerning a student " + preinscription.getEtudiant().getNom() + " "
								+ preinscription.getEtudiant().getPrenom() + " ?")) {
					preinscription.setEtudiant(getEtudiant());
//					preinscription.setId_etudiant(getEtudiant().getId());
//					preinscription.setNom(getEtudiant().getNom());
//					preinscription.setPrenom(getEtudiant().getPrenom());
//					preinscription.setNiveau(getEtudiant().getClasse().getNiveau());
					preinscription.setMontant(getM());
					preinscription.setDate(new Date());
					Preinscription newpreInscription = preinscriptionServiceImpl.update(preinscription);
					MethodUtilitaire.saveAlert(newpreInscription, "Register update successfully",
							"The Register " + newpreInscription.getId_preinscription() + " concerning  "
									+ newpreInscription.getEtudiant().getNom() + " "
									+ newpreInscription.getEtudiant().getPrenom() + " has been updated ");
					edit.setText("Edit");
					id_student.setDisable(false);
					id.setDisable(false);
					btnRegister.setDisable(false);
					clearField();
					this.isEditClick = false;
					loadDataOntable();
					pieChartOperation();
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
	void handleRefreshClick(ActionEvent event) {
		loadDataOntable();
	}

	@FXML
	private void filteredTable(KeyEvent event) {

		FilteredList<Preinscription> filteredetudiants = new FilteredList<Preinscription>(preinscriptionList,
				e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredetudiants.setPredicate((Predicate<? super Preinscription>) inscription -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();

					if (inscription.getEtudiant().getClasse().getNiveau().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getId().toString().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (inscription.getEtudiant().getClasse().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Preinscription> sortedList = new SortedList<Preinscription>(filteredetudiants);
		sortedList.comparatorProperty().bind(preinscriptionTab.comparatorProperty());
		preinscriptionTab.setItems(sortedList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setColumProperties();
		id_student.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		id_student.setValueFactory(valueFactory);
		try {
			pieChartOperation();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		loadTotaux();
	}

	private void loadTotaux() {
		int total = preinscriptionRepository.getTotalPreinscrit();
		double montant = preinscriptionRepository.getTotalMontant();
		totalMontant.setText(String.valueOf(montant));
		totalPreinscrit.setText(String.valueOf(total));
	}

	private void loadDataOntable() {
		preinscriptionList.clear();
		preinscriptionList.addAll(preinscriptionRepository.findAll());
		preinscriptionTab.setItems(preinscriptionList);
	}

	private void setColumProperties() {
		id_inscription.setCellValueFactory(new PropertyValueFactory<>("id_preinscription"));
		id_students.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Preinscription, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Preinscription, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getId().toString());
					}
				});
		nom.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Preinscription, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Preinscription, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getNom());
					}
				});
		prenom.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Preinscription, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Preinscription, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getPrenom());
					}
				});
		niveau.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Preinscription, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Preinscription, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getClasse().getNiveau());
					}
				});
		classeC.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Preinscription,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Preinscription, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getClasse().getNom());
			}
		});
		montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
	}

	private void pieChartOperation() throws SQLException {
		data = FXCollections.observableArrayList();
		List<Object> list = preinscriptionServiceImpl.groupByNiveau();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
//			String niveau = list.get(i).getClasse().getNiveau();
//			double qte = list.get(i).getId();
//			data.add(new PieChart.Data(niveau, qte));
			System.out.println(list.size());
		}
		PieChart.Data slice1 = new PieChart.Data("Niveau 1", 213);
		PieChart.Data slice2 = new PieChart.Data("Niveau 2", 67);
		PieChart.Data slice3 = new PieChart.Data("Niveau 3", 36);

		pieChart.getData().add(slice1);
		pieChart.getData().add(slice2);
		pieChart.getData().add(slice3);
		// pieChart.setData(data);
	}

	private String getStudentId() {
		return id_student.getEditor().getText();
	}

	private String getMontant() {
		return montantPreinscription.getText();
	}

	private double getM() {
		return Double.parseDouble(getMontant());
	}

	private Etudiant getEtudiant() {
		Etudiant etudiant = studentRepository.findOne(Long.parseLong(getStudentId()));
		return etudiant;
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getMontant() == null || getMontant().length() == 0) {
			errorMessage += "No valid  field montant!\n";
			try {
				getM();
			} catch (Exception e) {
				parseLongException("montant");
			}
		}
		if (getStudentId() == null || getStudentId().length() == 0) {
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
		montantPreinscription.setText("0.00");
	}

	private void parseLongException(String tranche) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid data");
		alert.setContentText("the value of the " + tranche + " is not correct\n" + "Please check field and try agane");
		alert.showAndWait();
	}

}
