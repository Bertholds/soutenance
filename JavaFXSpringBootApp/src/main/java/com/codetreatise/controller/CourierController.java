package com.codetreatise.controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Courier;
import com.codetreatise.repository.CourierRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.CourierServiceImpl;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class CourierController implements Initializable {
	@FXML
	private TableView<Courier> courierTab;

	@FXML
	private TableColumn<Courier, Long> idC;

	@FXML
	private TableColumn<Courier, String> emetterC;

	@FXML
	private TableColumn<Courier, Date> dateC;

	@FXML
	private TableColumn<Courier, Boolean> editC;

	@FXML
	private TableColumn<Courier, String> destinataireC;

	@FXML
	private TableColumn<Courier, String> statusC;

	@FXML
	private TableColumn<Courier, String> objectC;

	@FXML
	private ComboBox<String> filter;

	@FXML
	private DatePicker date;

	@FXML
	private DatePicker dateDepart;

	@FXML
	private DatePicker dateLimit;

	@FXML
	private TextField id_courier;

	@FXML
	private TextField emetter;

	@FXML
	private TextField recherche;

	@FXML
	private TextField destinataire;

	@FXML
	private RadioButton entrant;

	@FXML
	private ToggleGroup status;

	@FXML
	private RadioButton sortant;

	@FXML
	private TextArea object;

	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private CourierServiceImpl courierServiceImpl;

	ObservableList<String> setFiltre = FXCollections.observableArrayList("No limit", "5", "10", "15", "20", "25", "30",
			"50", "70");
	ObservableList<Courier> courierList = FXCollections.observableArrayList();

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

	@FXML
	void handleClearFieldClick(ActionEvent event) {
		clearField();
	}

	@FXML
	void handleFilterPressed(KeyEvent event) {
		FilteredList<Courier> filteredCourier = new FilteredList<Courier>(courierList, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredCourier.setPredicate((Predicate<? super Courier>) courier -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (courier.getId_courier().toString().contains(newValueFilter)) {
						return true;
					} else if (courier.getDate().toString().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (courier.getEmetteur().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (courier.getDestinataire().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Courier> sortedList = new SortedList<Courier>(filteredCourier);
		sortedList.comparatorProperty().bind(courierTab.comparatorProperty());
		courierTab.setItems(sortedList);
	}

	@FXML
	void handlePrintClick(ActionEvent event) {
		try {
			String d1 = dateDepart.getEditor().getText();
			String d2 = dateLimit.getEditor().getText();
			if((d1==null || d1.length()==0) || (d2==null || d2.length()==0))
				throw new Exception("Please enter valid date before to print");
			try {
				System.setProperty("java.awt.headless", "false");
				JasperDesign jasperDesign = JRXmlLoader.load("C:\\wamp\\listCourier.jrxml");
				String sql = "select * from courier where date between  '" + d1.toString() + "' AND  '" + d2.toString() + "'";
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

	@FXML
	void handleRefreshClick(ActionEvent event) {
		courierList.clear();
		List<Courier> list;
		if (filter.getSelectionModel().getSelectedItem() == "No limit") {
			list = courierRepository.findAll();
		} else
			list = courierServiceImpl.findAllLimitBy(Integer.parseInt(filter.getSelectionModel().getSelectedItem()));
		courierList.addAll(list);
		courierTab.setItems(courierList);
	}

	@FXML
	void handleValidateClick(ActionEvent event) throws ParseException {
		if (isInputValid()) {
			Courier courier = new Courier();
			Courier newcourier = null;
			courier.setDate(getDate());
			courier.setDestinataire(getDestinataire());
			courier.setEmetteur(getEmetteur());
			courier.setObject(getObject());
			courier.setStatus(getStatusIsInputValid());
			if (MethodUtilitaire.confirmationDialog(courier, "Confirm to save correspondence",
					"Confirm to save correspondence", "Save correspondence of " + courier.getEmetteur()))
				newcourier = courierRepository.save(courier);
			MethodUtilitaire.saveAlert(newcourier, "Correspondence save successful", "Correspondence save successful");
			loadDataOnTable();
			clearField();
		}
	}

	@FXML
	void handleDeleteClick(ActionEvent event) {
		ObservableList<Courier> selectedItems = courierTab.getSelectionModel().getSelectedItems();
		ArrayList<Courier> selectedIDs = new ArrayList<Courier>();
		for (Courier row : selectedItems) {
			selectedIDs.add(row);
		}
		if (selectedIDs.size() != 0) {
			courierTab.getItems().removeAll(selectedIDs);
			for (Courier c : selectedIDs) {
				courierRepository.delete(c);
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No correspondence Selected",
					"Please select a correspondence in the table.");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filter.setItems(setFiltre);
		filter.getSelectionModel().selectFirst();
		convertFormatDateAllPicker();
		setColumProperties();

	}

	private void setColumProperties() {
		idC.setCellValueFactory(new PropertyValueFactory<>("id_courier"));
		emetterC.setCellValueFactory(new PropertyValueFactory<>("emetteur"));
		destinataireC.setCellValueFactory(new PropertyValueFactory<>("destinataire"));
		statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
		objectC.setCellValueFactory(new PropertyValueFactory<>("object"));
		dateC.setCellValueFactory(new PropertyValueFactory<>("date"));
		editC.setCellFactory(cellFactory);

	}

	private void loadDataOnTable() {
		courierList.clear();
		List<Courier> list = courierRepository.findAll();
		courierList.addAll(list);
		courierTab.setItems(courierList);
	}

	private void clearField() {
		id_courier.clear();
		object.clear();
		emetter.clear();
		destinataire.clear();
		entrant.setSelected(false);
		sortant.setSelected(false);
		date.getEditor().clear();
	}

	private String getObject() {
		return object.getText();
	}

	private String getEmetteur() {
		return emetter.getText();
	}

	private String getDestinataire() {
		return destinataire.getText();
	}

	private Date getDate() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = format.parse(date.getEditor().getText());
		return d;
	}

	private String getStatusIsInputValid() {
		String status = null;
		if (entrant.isSelected()) {
			status = entrant.getText();
		} else if (sortant.isSelected()) {
			status = sortant.getText();
		}
		return status;
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getEmetteur() == null || getEmetteur().length() == 0) {
			errorMessage += "No valid field emetteur!\n";
		}

		if (getObject() == null || getObject().length() == 0) {
			errorMessage += "No valid field objet !\n";
		}

		if (getDestinataire() == null || getDestinataire().length() == 0) {
			errorMessage += "No valid field Destinataire!\n";
		}

		if (getStatusIsInputValid() == null || getStatusIsInputValid().length() == 0) {
			errorMessage += "Entrant/Sortant!\n";
		}

		if (date.getEditor().getText() == null || date.getEditor().getText().length() == 0) {
			errorMessage += "Please check the current date!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

	Callback<TableColumn<Courier, Boolean>, TableCell<Courier, Boolean>> cellFactory = new Callback<TableColumn<Courier, Boolean>, TableCell<Courier, Boolean>>() {
		@Override
		public TableCell<Courier, Boolean> call(final TableColumn<Courier, Boolean> param) {
			final TableCell<Courier, Boolean> cell = new TableCell<Courier, Boolean>() {
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
							Courier courier = getTableView().getItems().get(getIndex());
							updateUser(courier);
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

				private void updateUser(Courier courier) {
					id_courier.setText(courier.getId_courier().toString());
					emetter.setText(courier.getEmetteur());
					destinataire.setText(courier.getDestinataire());
					if (courier.getStatus() == "Entrant")
						entrant.setSelected(true);
					else
						sortant.setSelected(true);
					date.getEditor().setText(courier.getDate().toString());

				}
			};
			return cell;
		}
	};
}
