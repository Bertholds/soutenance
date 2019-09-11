package com.codetreatise.controller;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

@Controller
public class PermissionController {

	@FXML
	private TableView<?> permissionTab;

	@FXML
	private TableColumn<?, ?> idAbscenceC;

	@FXML
	private TableColumn<?, ?> dateC;

	@FXML
	private TableColumn<?, ?> motifC;

	@FXML
	private TableColumn<?, ?> idTransmitterC;

	@FXML
	private TableColumn<?, ?> nomC;

	@FXML
	private TableColumn<?, ?> prenomC;

	@FXML
	private TextField idAbscence;

	@FXML
	private TextField date;

	@FXML
	private TextField motif;

	@FXML
	private TextField idTransmitter;

	@FXML
	private TextField duree;

	@FXML
	private RadioButton isStudent;

	@FXML
	private RadioButton isEmployee;

	@FXML
	private ComboBox<?> filterPermission;

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
	private TableView<?> staffTab;

	@FXML
	private TableColumn<?, ?> idWorker;

	@FXML
	private TableColumn<?, ?> nom;

	@FXML
	private TableColumn<?, ?> prenom;

	@FXML
	private ComboBox<?> filterDepartment;

	@FXML
	private TableView<?> studentTab;

	@FXML
	private TableColumn<?, ?> idStudent;

	@FXML
	private TableColumn<?, ?> name;

	@FXML
	private TableColumn<?, ?> surname;

	@FXML
	private ComboBox<?> filterClasse;

	@FXML
	void handleClearField(ActionEvent event) {

	}

	@FXML
	void handleDeleteClick(ActionEvent event) {

	}

	@FXML
	void handleFilteredOnTablePermission(KeyEvent event) {

	}

	@FXML
	void handleFilteredOnTableStaff(KeyEvent event) {

	}

	@FXML
	void handleFilteredOnTableStudent(KeyEvent event) {

	}

	@FXML
	void handleModifyClick(ActionEvent event) {

	}

	@FXML
	void handleRefreshOnPermission(ActionEvent event) {

	}

	@FXML
	void handleRefreshOnTabStaff(ActionEvent event) {

	}

	@FXML
	void handleRefreshOnTabStudent(ActionEvent event) {

	}

	@FXML
	void handleValidateClick(ActionEvent event) {

	}

}
