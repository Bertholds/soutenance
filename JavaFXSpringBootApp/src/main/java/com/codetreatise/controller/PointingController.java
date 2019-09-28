package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Departement;
import com.codetreatise.bean.Personel;
import com.codetreatise.bean.Pointage;
import com.codetreatise.bean.Poste;
import com.codetreatise.bean.Tache;
import com.codetreatise.repository.PointageRepository;
import com.codetreatise.repository.StaffRepository;
import com.codetreatise.repository.TacheRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.PointageServiceImpl;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.Duration;
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
public class PointingController implements Initializable {
	@FXML
	private RadioButton become;
	@FXML
	private ToggleGroup status;
	@FXML
	private RadioButton leave;
	@FXML
	private DatePicker date;
	@FXML
	private ComboBox<String> personal;
	@FXML
	private TextField id;
	@FXML
	private TextField idTache;
	@FXML
	private TextField recherche;
	@FXML
	private ComboBox<String> task;
	@FXML
	private ComboBox<String> filtreMois;
	@FXML
	private ComboBox<Object> filtre;
	@FXML
	private TextField time;
	@FXML
	private TextField libeleTache;
	@FXML
	private TableView<Pointage> pointingTab;
	@FXML
	private TableView<Tache> tacheTab;
	@FXML
	private TableColumn<Pointage, Long> idTab;
	@FXML
	private TableColumn<Pointage, String> nomTab;
	@FXML
	private TableColumn<Pointage, String> prenomTab;
	@FXML
	private TableColumn<Pointage, String> dateTab;
	@FXML
	private TableColumn<Pointage, String> becomeTab;
	@FXML
	private TableColumn<Pointage, String> leaveTab;
	@FXML
	private TableColumn<Pointage, String> userIdTab;
	@FXML
	private TableColumn<Tache, Long> idTacheTab;
	@FXML
	private TableColumn<Tache, String> libeleTacheTab;
	@FXML
	private TableColumn<Tache, Boolean> editTacheTab;
	@FXML
	private TableColumn<Tache, Boolean> deleteTacheTab;
	@FXML
	private Label nomLabel;
	@FXML
	private Label prenomLabel;
	@FXML
	private Label functionLabel;
	@FXML
	private Label postLabel;
	@FXML
	private Label labelIdPointage;
	@FXML
	private Label departmentLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label becomeLabel;
	@FXML
	private Label leaveLabel;
	@FXML
	private Label hourOfDaytime;
	@FXML
	private Label hourOfMonth;

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private TacheRepository tacheRepository;
	@Autowired
	private PointageRepository pointageRepository;
	@Autowired
	private PointageServiceImpl pointageServiceImpl;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	ObservableList<String> personelList = FXCollections.observableArrayList();
	ObservableList<String> tacheList = FXCollections.observableArrayList();
	private ObservableList<String> monthNames = FXCollections.observableArrayList();
	ObservableList<Tache> tacheListTab = FXCollections.observableArrayList();
	ObservableList<Pointage> pointageListTab = FXCollections.observableArrayList();
	ObservableList<Object> setFiltre = FXCollections.observableArrayList("No limit", 5, 10, 15, 20, 25, 30, 50, 70);

	// Event Listener on Button.onAction
	@FXML
	public void handleValidateClick(ActionEvent event) throws IOException, Exception {
		if (isInputValid()) {
			Pointage newPointage = null;
			Pointage pointage = new Pointage();
			pointage.setUtilisateur(MethodUtilitaire.deserializationUser());
			pointage.setPersonel(getPersonnel());
			pointage.setDate(getCurrentDay());
			if (leave.isSelected()) {
				pointage.setHeurArriver(null);
				pointage.setHeurDepart(getTime());
			} else {
				pointage.setHeurArriver(getTime());
				pointage.setHeurDepart(null);
			}
			pointage.setTache(getTache());
			newPointage = pointageRepository.save(pointage);
			MethodUtilitaire.saveAlert(newPointage, "Pointing has saved successful",
					"Pointing number " + newPointage.getId_pointage() + " concerning "
							+ newPointage.getPersonel().getNom() + " " + newPointage.getPersonel().getPrenom()
							+ " has saved successful");
			handleClearFieldClick(event);
			loadDataOnTablePointage();
		}
	}

	@FXML
	public void handleLoadDataOnTablePointage(ActionEvent event) {
		pointageListTab.clear();
		List<Pointage> list;
		if (filtre.getSelectionModel().getSelectedItem() == "No limit") {
			list = pointageRepository.findAll();
		} else
			list = pointageServiceImpl
					.findAllLimitBy(Integer.parseInt((String) filtre.getSelectionModel().getSelectedItem()));
		pointageListTab.addAll(list);
		pointingTab.setItems(pointageListTab);
	}

	@FXML
	public void handleLeaveTime(ActionEvent event) {
		Pointage pointage = pointingTab.getSelectionModel().getSelectedItem();
		if (pointage != null) {
			if (MethodUtilitaire.confirmationDialog(pointage, "Confirm to apply leave time",
					"Confirm to apply leave time", "apply leave time of " + pointage.getPersonel().getNom() + " "
							+ pointage.getPersonel().getPrenom())) {
				pointage.setHeurDepart(getTime());
				pointage.setHeureTravail(getHourOfDaytime(pointage));
				pointageServiceImpl.update(pointage);
				loadDataOnTablePointage();
			}
		}
	}

	@FXML
	public void handleFilerOntablePointage(KeyEvent keyEvent) {
		FilteredList<Pointage> filteredPointage = new FilteredList<Pointage>(pointageListTab, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredPointage.setPredicate((Predicate<? super Pointage>) pointage -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (pointage.getId_pointage().toString().contains(newValueFilter)) {
						return true;
					} else if (pointage.getDate().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (pointage.getHeurArriver().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (pointage.getHeurArriver().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (pointage.getPersonel().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (pointage.getPersonel().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Pointage> sortedList = new SortedList<Pointage>(filteredPointage);
		sortedList.comparatorProperty().bind(pointingTab.comparatorProperty());
		pointingTab.setItems(sortedList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClearFieldClick(ActionEvent event) {
		personal.getSelectionModel().clearSelection();
		leave.setSelected(false);
		become.setSelected(true);
		task.getSelectionModel().clearSelection();
	}

	@FXML
	public void handleAjouterTacheClick(ActionEvent event) {
		if (isInputValidTache()) {
			Tache newTache = null;
			Tache tache = new Tache();
			tache.setLibele(getLibeleTache());
			newTache = tacheRepository.save(tache);
			MethodUtilitaire.saveAlert(newTache, "Save successful", "tache number " + newTache.getId_tache()
					+ " whith label: \n" + newTache.getLibele() + " has been save successful");
			clearFieldTache();
			loadDataOntableTache();
		}
	}

	@FXML
	public void handleModifierTacheClick(ActionEvent event) {
		if (isInputValidTache()) {
			Tache tache = tacheTab.getSelectionModel().getSelectedItem();
			try {
				tache.setLibele(getLibeleTache());
				tacheRepository.updateTask(tache.getLibele(), tache.getId_tache());
				MethodUtilitaire.saveAlert(null, "Update Task successful", "Update Task successful");
				clearFieldTache();
				loadDataOntableTache();
			} catch (Exception e) {
				e.printStackTrace();
				MethodUtilitaire.deleteNoPersonSelectedAlert("Updating task failed", "failed to update post",
						"Any row selected! please select department firstly");
			}
		}
	}

	@FXML
	private void handleLoadDataOnTableTache(ActionEvent event) {
		loadDataOntableTache();
	}

	@FXML
	private void handleDeleteClick(ActionEvent event) {
		ObservableList<Pointage> selectedItems = pointingTab.getSelectionModel().getSelectedItems();
		ArrayList<Pointage> selectedIDs = new ArrayList<Pointage>();
		for (Pointage row : selectedItems) {
			selectedIDs.add(row);
		}
		if (selectedIDs.size() != 0) {
			pointingTab.getItems().removeAll(selectedIDs);
			for (Pointage p : selectedIDs) {
				pointageRepository.delete(p);
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No pointing Selected",
					"Please select a pointing in the table.");
		}
	}

	@FXML
	private void handleDeleteAllClick(ActionEvent event) {
		pointingTab.getSelectionModel().selectAll();
		List<Pointage> pointage = pointingTab.getSelectionModel().getSelectedItems();
		if (pointage.size() != 0) {
			pointingTab.getItems().removeAll(pointage);
			pointageRepository.deleteAll();
			System.out.println(pointage.size());
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Empty table", "The table is empty",
					"No items to delete in table.");
		}
	}

	@FXML
	private void handlePrintClick(ActionEvent event) {
		int i = filtreMois.getSelectionModel().getSelectedIndex();
		if(i==-1) {
			MethodUtilitaire.deleteNoPersonSelectedAlert(null, "Le mois est incorrect", "Veuillez selectioner un mois de l'annee puis reesayé");
		}
		else {
			String[] tableauDeMois = {"-01-", "-02-", "-03-", "-04-", "-05-", "-06-", "-07-", "-08-", "-09-", "-10-","-11-", "-12-"};
			try {
				System.setProperty("java.awt.headless", "false");
				JasperDesign jasperDesign = JRXmlLoader.load("C:\\wamp\\listPointage.jrxml");
				String sql = "select max(p.id_pointage) as id_pointage, sum(p.heure_travail) as heure_travail, n.nom, n.prenom, n.fonction\r\n" + 
						"from pointage p \r\n" + 
						"join personnel n on p.personel_id=n.id\r\n" + 
						"where p.date like '"+"%" + tableauDeMois[i] + "%"+ "'\r\n" + 
						"group by `personel_id`\r\n" + 
						"order by`id_pointage`";
				JRDesignQuery designQuery = new JRDesignQuery();
				designQuery.setText(sql);
				jasperDesign.setQuery(designQuery);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				JasperPrint print = JasperFillManager.fillReport(jasperReport, null, MethodUtilitaire.dbConnect());
				JasperViewer jrviewer = new JasperViewer(print, false);
				// JasperViewer.viewReport(print);
				jrviewer.setVisible(true);
				jrviewer.toFront();
			} catch (SQLException | JRException | ClassNotFoundException e) {
				e.printStackTrace();
				MethodUtilitaire.errorMessageAlert("Failed to print", "Failed to print !", e.getMessage());
			}
		}
		
	}

	private void setDataOnCurrentTime() {
		time.setEditable(false);
		/*
		 * Thread clock = new Thread() { public void run() { for (;;) { Calendar cal =
		 * Calendar.getInstance();
		 * 
		 * int second = cal.get(Calendar.SECOND); int minute = cal.get(Calendar.MINUTE);
		 * int hour = cal.get(Calendar.HOUR); //System.out.println(hour + ":" + (minute)
		 * + ":" + second); time.setText(hour + ":" + (minute) + ":" + second);
		 * 
		 * try { sleep(1000); } catch (InterruptedException ex) { //... } } } };
		 * clock.start();
		 */
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String t = formatter.format(LocalTime.now());
			time.setText(t);
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

	private void setCurrentDay() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String dat = format.format(new Date());
			date.getEditor().setText(dat);
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

	private void setPersonal() {
		personelList.clear();
		List<Personel> list = staffRepository.findAll();
		for (Personel personel : list) {
			personelList.add(personel.toString());
		}
		personal.setItems(personelList);
	}

	private void setfiltreMois() {
		filtreMois.setPromptText("Chose month");
		monthNames.clear();
		String[] month = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		monthNames.addAll(Arrays.asList(month));
		filtreMois.setItems(monthNames);
	}

	private void setTask() {
		tacheList.clear();
		List<Tache> list = tacheRepository.findAll();
		for (Tache tache : list) {
			tacheList.add(tache.toString());
		}
		task.setItems(tacheList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pointingTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		filtre.setItems(setFiltre);
		become.setSelected(true);
		filtre.getSelectionModel().selectFirst();
		setPersonal();
		setTask();
		setfiltreMois();
		setDataOnCurrentTime();
		setCurrentDay();
		setColumTacheProperties();
		setColumPointageProperties();
		pointingTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pointage>() {

			@Override
			public void changed(ObservableValue<? extends Pointage> observable, Pointage oldValue, Pointage newValue) {
				showPointageDetails(newValue);

			}
		});
	}

	private String getPersonal() {
		System.out.println(personal.getSelectionModel().getSelectedItem());
		return personal.getSelectionModel().getSelectedItem();
	}

	private String getStatus() {
		String status = null;
		if (become.isSelected())
			status = become.getText();
		if (leave.isSelected())
			status = leave.getText();
		return status;
	}

	private String getTime() {
		return time.getText();
	}

	private String getTask() {
		return task.getSelectionModel().getSelectedItem();
	}

	private Tache getTache() {
		Tache tache = tacheRepository.findByLibele(task.getSelectionModel().getSelectedItem());
		return tache;
	}

	private Personel getPersonnel() {
		Personel p = null;
		List<Personel> list = staffRepository.findAll();
		for (Personel personel : list) {
			if (personel.toString().equals(getPersonal()))
				p = personel;
		}
		return p;
	}

	private String getCurrentDay() {
		return date.getEditor().getText();
	}

	private String getLibeleTache() {
		return libeleTache.getText();
	}

	private void setColumTacheProperties() {
		idTacheTab.setCellValueFactory(new PropertyValueFactory<>("id_tache"));
		libeleTacheTab.setCellValueFactory(new PropertyValueFactory<>("libele"));
		editTacheTab.setCellFactory(cellFactory);
		deleteTacheTab.setCellFactory(cellFactory2);
	}

	private void setColumPointageProperties() {
		idTab.setCellValueFactory(new PropertyValueFactory<>("id_pointage"));
		dateTab.setCellValueFactory(new PropertyValueFactory<>("date"));
		becomeTab.setCellValueFactory(new PropertyValueFactory<>("heurArriver"));
		leaveTab.setCellValueFactory(new PropertyValueFactory<>("heurDepart"));
		userIdTab.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Pointage, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Pointage, String> param) {
						return new SimpleStringProperty(
								param.getValue().getUtilisateur().getId().toString());
					}
				});
		nomTab.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Pointage, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Pointage, String> param) {
						return new SimpleStringProperty(param.getValue().getPersonel().getNom());
					}
				});
		prenomTab.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Pointage, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Pointage, String> param) {
						return new SimpleStringProperty(param.getValue().getPersonel().getPrenom());
					}
				});
	}

	private void loadDataOntableTache() {
		tacheListTab.clear();
		List<Tache> list = tacheRepository.findAll();
		tacheListTab.addAll(list);
		tacheTab.setItems(tacheListTab);
	}

	private void loadDataOnTablePointage() {
		pointageListTab.clear();
		List<Pointage> list = pointageRepository.findAll();
		pointageListTab.addAll(list);
		pointingTab.setItems(pointageListTab);
	}

	private float getHourOfDaytime(Pointage pointage) {
		String arriver = pointage.getHeurArriver();
		String depart = pointage.getHeurDepart();
		LocalTime TimeBecome = LocalTime.parse(arriver);
		LocalTime TimeLeave = LocalTime.parse(depart, formatter);
		LocalTime val = TimeLeave.minusHours(TimeBecome.getHour());
		val = val.minusMinutes(TimeBecome.getMinute());
		val = val.minusSeconds(TimeBecome.getSecond());
		int h = val.getHour();
		int min = val.getMinute();
		String time = h + "." + min;
		return Float.parseFloat(time);
	}

	private float getHourOfMonth(Pointage pointage) {
		// DateFormat dateFormatter = new SimpleDateFormat("yyyy-MMM-dd");
		String date = pointage.getDate();
		String val = "0";
		 float heur = 0;
		LocalDate localDate = null;
		DateTimeFormatter f = new DateTimeFormatterBuilder().parseCaseInsensitive()
				.append(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toFormatter();
		try {
			localDate = LocalDate.parse(date.subSequence(0, 10), f);
			System.out.println(localDate.toString()); // 2019-12-22
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			// Exception handling message/mechanism/logging as per company standard
		}
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		if (month < 10) {
			val = val.concat(String.valueOf(month)) + "-" + year;
			System.out.println(val);
		} else {
			val = String.valueOf(month) + "-" + year;
			System.out.println(val);
		}

		List<Float> heurTravail = pointageRepository
				.loadAllHeurTravailFilterByMonthForOnePerson(pointage.getPersonel().getId(), "%" + val + "%");
		System.out.println(heurTravail.size());
		for (Float ht : heurTravail) {
             heur = heur + ht.floatValue(); 
			
//			totalTime = totalTime.plusHours(time.getHour());
//			totalTime = totalTime.plusMinutes(time.getMinute());
//			totalTime = totalTime.plusSeconds(time.getSecond());
		}

		return heur;
	}

	private void clearFieldTache() {
		idTache.clear();
		libeleTache.clear();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getPersonal() == null || getPersonal().length() == 0) {
			errorMessage += "No valid field Personal to tick!\n";
		}

		if (getStatus() == null || getStatus().length() == 0) {
			errorMessage += "No valid field become/leave!\n";
		}

		if (getTask() == null || getTask().length() == 0) {
			errorMessage += "Please check Job/Work/task!\n";
		}

		if (getCurrentDay() == null || getCurrentDay().length() == 0) {
			errorMessage += "Please check the current day!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

	private boolean isInputValidTache() {
		String errorMessage = "";

		if (getLibeleTache() == null || getLibeleTache().length() == 0) {
			errorMessage += "No valid field libele!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

	private void showPointageDetails(Pointage pointage) {
		if (pointage != null) {
			List<Poste> postes = pointage.getPersonel().getPostes();
			String str = "";
			for (int i = 0; i < postes.size(); i++) {

				str += postes.get(i).toString() + " -- ";
			}
			List<Departement> departements = pointage.getPersonel().getDepartements();
			String str1 = "";
			for (int i = 0; i < departements.size(); i++) {
				str1 += departements.get(i).toString() + " -- ";
			}
			nomLabel.setText(pointage.getPersonel().getNom());
			prenomLabel.setText(pointage.getPersonel().getPrenom());
			functionLabel.setText(pointage.getPersonel().getFonction());
			postLabel.setText(str);
			departmentLabel.setText(str1);
			idLabel.setText(pointage.getPersonel().getId().toString());
			dateLabel.setText(pointage.getDate());
			becomeLabel.setText(pointage.getHeurArriver());
			leaveLabel.setText(pointage.getHeurDepart());
			labelIdPointage.setText(pointage.getId_pointage().toString());
			hourOfDaytime.setText(String.valueOf(pointage.getHeureTravail()));
			hourOfMonth.setText(String.valueOf(getHourOfMonth(pointage)));

		} else

		{
			nomLabel.setText(null);
			prenomLabel.setText(null);
			functionLabel.setText(null);
			postLabel.setText(null);
			departmentLabel.setText(null);
			idLabel.setText(null);
			dateLabel.setText(null);
			becomeLabel.setText(null);
			leaveLabel.setText(null);
			hourOfDaytime.setText(null);
			labelIdPointage.setText(null);
			hourOfMonth.setText(null);

		}
	}

	Callback<TableColumn<Tache, Boolean>, TableCell<Tache, Boolean>> cellFactory = new Callback<TableColumn<Tache, Boolean>, TableCell<Tache, Boolean>>() {
		@Override
		public TableCell<Tache, Boolean> call(final TableColumn<Tache, Boolean> param) {
			final TableCell<Tache, Boolean> cell = new TableCell<Tache, Boolean>() {
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
							Tache tache = getTableView().getItems().get(getIndex());
							updateUser(tache);
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

				private void updateUser(Tache tache) {
					idTache.setText(tache.getId_tache().toString());
					libeleTache.setText(tache.getLibele());
				}
			};
			return cell;
		}
	};

	Callback<TableColumn<Tache, Boolean>, TableCell<Tache, Boolean>> cellFactory2 = new Callback<TableColumn<Tache, Boolean>, TableCell<Tache, Boolean>>() {
		@Override
		public TableCell<Tache, Boolean> call(final TableColumn<Tache, Boolean> param) {
			final TableCell<Tache, Boolean> cell = new TableCell<Tache, Boolean>() {
				Image imgDelete = new Image(getClass().getResourceAsStream("/images/delete.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Tache tache = getTableView().getItems().get(getIndex());
							deleteUser(tache);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgDelete);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void deleteUser(Tache tache) {
					if (MethodUtilitaire.confirmationDialog(tache, "Deleting task", "Delete this task ?",
							"Delete task number " + tache.getId_tache() + " ?")) {
						tacheTab.getItems().remove(tache);
						tacheRepository.delete(tache);
					}
				}
			};
			return cell;
		}
	};

	Callback<TableColumn.CellDataFeatures<Pointage, String>, ObservableValue<String>> callName = new Callback<TableColumn.CellDataFeatures<Pointage, String>, ObservableValue<String>>() {

		@Override
		public ObservableValue<String> call(CellDataFeatures<Pointage, String> param) {
			return new SimpleStringProperty(param.getValue().getPersonel().getNom());
		}
	};
}
