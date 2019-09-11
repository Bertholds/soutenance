package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

@Controller
public class SettingController implements Initializable {
	@FXML
	private GridPane grid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 ColumnConstraints column = new ColumnConstraints(100);
		 grid.getColumnConstraints().add(column);
		
	}

}
