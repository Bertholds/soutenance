package com.codetreatise.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Utilisateur;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.UserRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.EtudiantServiceImpl;
import com.codetreatise.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
	
	@Autowired
	private MethodUtilitaire methodUtilitaire;
	
	
	//private static final Logger logger = LogManager.getLogger(LoginController.class);

	File file;
	Utilisateur utilisateur;
	
	private void serializeUser(Utilisateur utilisateur) throws IOException {
		file = new File("C:/wamp/license.txt");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(utilisateur);
		oos.close();
		fos.close();
	}
	
	@FXML
	private void login(ActionEvent event) throws IOException {
		if (etudiantServiceImpl.isInputValid(getUsername(), getPassword())) {
			 utilisateur = userRepository.authentification(getUsername(), getPassword());
			try {
				if (utilisateur.getPass().equals(getPassword()))
					stageManager.switchScene(FxmlView.MODULE);
				serializeUser(utilisateur);
				methodUtilitaire.LogFile("Operation de connection", "Pas de cible", utilisateur);
				//logger.trace("Connection"+utilisateur.getIdutilisateur());
			} catch (Exception e) {
				e.printStackTrace();
				MethodUtilitaire.errorMessageAlert("Invalid Data", "Invalid Data", "Wrong username or password!");
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
	
	public String getAcces() {
		
		return utilisateur.getAcces();
	}

}