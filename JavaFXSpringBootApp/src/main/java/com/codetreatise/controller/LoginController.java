package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Utilisateur;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.UserRepository;
import com.codetreatise.service.impl.EtudiantServiceImpl;
import com.codetreatise.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LoginController implements Initializable {

	@FXML
	private Button btnLogin;

	@FXML
	private PasswordField password;

	@FXML
	private TextField username;

	@FXML
	private Label lblLogin;

	@Autowired
	private EtudiantServiceImpl etudiantServiceImpl;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private UserRepository userRepository;

	@FXML
	private void login(ActionEvent event) throws IOException {
		if (etudiantServiceImpl.isInputValid(getUsername(), getPassword())) {
			Utilisateur utilisateur = userRepository.authentification(getUsername(), getPassword());
			try {
				if (utilisateur.getPass().equals(getPassword()))
					stageManager.switchScene(FxmlView.MODULE);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Data");
				alert.setHeaderText("Please enter correct data");
				alert.setContentText("Wrong username or password!");

				alert.showAndWait();
			}
		}
	}

	public String getPassword() {
		return password.getText();
	}

	public String getUsername() {
		return username.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
