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
	public void handleClassClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.CLASSE);
	}

	@FXML
	public void handleStaffClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.STAFF);
	}

	@FXML
	public void handleSchoolFeesClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.INSCRIPTION);
	}

	@FXML
	public void handleSubjectClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.SUBJECT);
	}

	@FXML
	public void handlePointingClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.POINTING);
	}

	@FXML
	private void handleSettingClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.SETTING);
	}
	
	@FXML
	private void handleStudentAbscenceClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.ABSCENCE);
	}
	
	@FXML
	private void handlePermissionClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.PERMISSION);
	}
	
	@FXML
	private void handleCourierClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.COURIER);
	}
	
	@FXML
	private void handleUserClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.USER);
	}

}
