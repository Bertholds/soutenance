package com.codetreatise.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@Controller
public class ModuleController implements Initializable {


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (Button b : getAllButton()) {
			b.setDisable(true);
		}
		
		try {
			checkAcces();
			setSchoolName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	private Button btnCourier;

	@FXML
	private Button btnClasses;

	@FXML
	private Button btnStaff;

	@FXML
	private Button btnUser;

	@FXML
	private Button btnSchoolFess;

	@FXML
	private Button btnPointing;

	@FXML
	private Button btnPermission;

	@FXML
	private Button btnStudentAbscence;

	@FXML
	private Button btnInfirmary;

	@FXML
	private Button btnSubject;

	@FXML
	private Button btnLibrary;

	@FXML
	private Button btnRest;
	
	@FXML
	private Label schoolName;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private LoginController LoginController;

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
	
	@FXML
	private void handleDashbordClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.DASHBORD);
	}

	@FXML
	private void handleLibrairyClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.Bibliotheque);
	}

	@FXML
	private void handleLogOutClick(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	@FXML
	private void handleBackupClic(ActionEvent event) {
		stageManager.switchSceneShowPreviousStage(FxmlView.BACKUP);
	}

	public List<Button> getAllButton() {

		List<Button> buttons = new ArrayList<Button>();
		buttons.add(btnClasses);
		buttons.add(btnCourier);
		buttons.add(btnPermission);
		buttons.add(btnRest);
		buttons.add(btnSchoolFess);
		buttons.add(btnSettings);
		buttons.add(btnStudentAbscence);
		buttons.add(btnStudents);
		buttons.add(btnSubject);
		buttons.add(btnUser);
		buttons.add(btn_Timetable);
		buttons.add(btnDashboard);
		buttons.add(btnInfirmary);
		buttons.add(btnLibrary);
		buttons.add(btnPointing);
		buttons.add(btnStaff);

		return buttons;
	}

	private void checkAcces() throws Exception {
		String acces = LoginController.getAcces();

		if (acces.equals("Administrateur")) {
			helpCheckAccess(new File("C:/wamp/listAdmin.txt"));
		} else if (acces.equals("Super administrateur")) {
			helpCheckAccess(new File("C:/wamp/listSuperAdmin.txt"));
		}else if (acces.equals("Pointeur")) {
			helpCheckAccess(new File("C:/wamp/listPointeur.txt"));
		}else if (acces.equals("Creancier")) {
			helpCheckAccess(new File("C:/wamp/listCreance.txt"));
		}else if (acces.equals("Discipline")) {
			helpCheckAccess(new File("C:/wamp/listDiscipline.txt"));
		}else if (acces.equals("Bibliotheque")) {
			helpCheckAccess(new File("C:/wamp/listBibliotheque.txt"));
		}else if (acces.equals("Infirmerie")) {
			helpCheckAccess(new File("C:/wamp/listInfirmerie.txt"));
		}else if (acces.equals("Enseignant")) {
			helpCheckAccess(new File("C:/wamp/listEnseignant.txt"));
		}
	}
	
	private void helpCheckAccess( File fichier) throws Exception {
		File file = fichier;
		FileReader	fileReader = new FileReader(file);
		BufferedReader	bufferedReader = new BufferedReader(fileReader);
		String s;
		String[] t = null;
			while ((s = bufferedReader.readLine()) != null) {
			t = s.split(";");
			}
			for (Button b : getAllButton()) {
				for (int i = 0; i < t.length; i++) {
					if (t[i].contentEquals(b.getText()))
						b.setDisable(false);
				}
			}
			fileReader.close();
			bufferedReader.close();
	}
	
	public String setSchoolName() throws  IOException {
		File file = new File("C:/wamp/writeLabel.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String s=bufferedReader.readLine();
		System.out.println("********************  "+s+"  ******************************");
        schoolName.setText(s);
		schoolName.setOnMouseEntered(new EventHandler<Event>() { 

			@Override
			public void handle(Event event) {
				//schoolName.setFill(Color.BROWN);
			}
		});
		fileReader.close();
		bufferedReader.close();
		System.out.println("Bingoooooooooooooooooooooo!");
		return s;
	}

}
