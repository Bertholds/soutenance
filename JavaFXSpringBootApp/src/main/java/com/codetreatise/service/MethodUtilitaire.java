package com.codetreatise.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Operation;
import com.codetreatise.bean.Utilisateur;
import com.codetreatise.repository.OperationRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

@Controller
public class MethodUtilitaire {

	@Autowired
	private  OperationRepository operationRepository;
	
	public static void saveAlert(Object object, String title, String content) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void deleteNoPersonSelectedAlert(String title, String header, String content) {
		// Nothing selected.
					Alert alert = new Alert(AlertType.WARNING);
					// alert.initOwner( );
					alert.setTitle(title);
					alert.setHeaderText(header);
					alert.setContentText(content);

					alert.showAndWait();
	}
	
	public static boolean confirmationDialog(Object object, String title, String header, String content) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		ButtonType cancel = new ButtonType("Cancel");
		ButtonType yes = new ButtonType("Yes");
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(cancel, yes);
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == yes) {
			return true;
		} else
			return false;
	}
	
	public static void errorMessageAlert(String title, String header, String content) {
		// Nothing selected.
					Alert alert = new Alert(AlertType.ERROR);
					// alert.initOwner( );
					alert.setTitle(title);
					alert.setHeaderText(header);
					alert.setContentText(content);

					alert.showAndWait();
	}
	
	public  static Utilisateur deserializationUser() throws IOException, Exception {
		File file = new File("C:/wamp/license.txt");
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Utilisateur utilisateur = (Utilisateur) objectInputStream.readObject();
		System.out.println(utilisateur);
		objectInputStream.close();
		return utilisateur;
	}
	
	public   void LogFile(String name, String cible, Utilisateur utilisateur) throws IOException, Exception   {
		Operation operation = new Operation();
		String ip = InetAddress.getLocalHost().toString();
		System.out.println(ip);
		operation.setName(name);
		operation.setHeure(new Time(System.currentTimeMillis()));
		operation.setUtilisateur(utilisateur);
		operation.setAddress(ip);
		operation.setCible(cible);
		operationRepository.save(operation);
	}

	private static BasicDataSource dataSource;
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306/db_jpa";
	
	public static Connection dbConnect() throws ClassNotFoundException, SQLException {

		Connection connection = null;
		
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("where is your My sql jdbc driver ?");
			e.printStackTrace();
			throw e;
		}

		System.out.println("Charging drivers successful");

		try {
			if(connection==null) {
				connection = DriverManager.getConnection(CONN_STRING, "root", "");
				System.out.println(" connexion database successful");
			}

		} catch (SQLException e) {
			System.out.println("wrong to connect on database");
			throw e;
		}
		return connection;
	}
	
	public static Connection getConnection() throws Exception {
        
		if(dataSource==null) {
			dataSource = new BasicDataSource();
			dataSource.setUrl("jdbc:mysql://localhost:3306/db_jpa");
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("root");
			dataSource.setPassword("");
		}
		return dataSource.getConnection();
	}
	
}
