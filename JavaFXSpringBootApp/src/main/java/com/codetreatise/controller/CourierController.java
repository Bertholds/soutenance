package com.codetreatise.controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;

import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.RadioButton;

import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

@Controller
public class CourierController {
	@FXML
	private TableView<?> courierTab;

	@FXML
	private TableColumn<?, ?> idC;

	@FXML
	private TableColumn<?, ?> emetterC;

	@FXML
	private TableColumn<?, ?> destinataireC;

	@FXML
	private TableColumn<?, ?> statusC;

	@FXML
	private TableColumn<?, ?> objectC;

	@FXML
	private ComboBox<?> filter;

	@FXML
	private TextField id_courier;

	@FXML
	private TextField emetter;

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

	@FXML
	void handleClearFieldClick(ActionEvent event) {

	}

	@FXML
	void handleFilterPressed(KeyEvent event) {

	}

	@FXML
	void handlePrintClick(ActionEvent event) {

	}

	@FXML
	void handleRefreshClick(ActionEvent event) {

	}

	@FXML
	void handleValidateClick(ActionEvent event) {

	}
}
