package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

@Controller
public class ModuleController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private Button btnDashboard;

	@FXML
	private Button btnStudents;

	@FXML
	private Button btn_Timetable;

	@FXML
	private Button btnSettings;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnClasses;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	public void handleStudentClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.STUDENT);

	}

	@FXML
	void handleClassClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.CLASSE);
	}

	@FXML
	void handleStaffClick(ActionEvent event) {
        stageManager.switchSceneShowPreviousStage(FxmlView.STAFF);
	}

	@FXML
	void handleSchoolFeesClick(ActionEvent event) {
         stageManager.switchSceneShowPreviousStage(FxmlView.INSCRIPTION);
	}
	
	@FXML
	void handleSubjectClick(ActionEvent event) {
         stageManager.switchSceneShowPreviousStage(FxmlView.SUBJECT);
	}
}
