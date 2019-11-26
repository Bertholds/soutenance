package com.codetreatise.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.codetreatise.service.MethodUtilitaire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

@Controller
public class BackupController implements Initializable {
	@FXML
	private TextField cheminSauvegarde;
	@FXML
	private TextField cheminRestauration;
	@FXML
	private AnchorPane anchopane;

	static String dbName = "db_jpa";
	static String dbUser = "super";
	static String dbPass = "super";
	String source = "C:/Users/Berthold/Desktop/www/hello.sql";
	File directory;

	// Event Listener on Button.onAction
	@FXML
	public void handleParcourtSauvegarde(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Selection du repertoire de sauvegard");
		directoryChooser.setInitialDirectory(new File("C:\\Users\\Berthold\\Downloads"));
		Stage ownerWindow = (Stage) anchopane.getScene().getWindow();
		directory = directoryChooser.showDialog(ownerWindow);
		cheminSauvegarde.setText(directory.getAbsolutePath());

	}

	// Event Listener on Button.onAction
	@FXML
	public void handkleRestaurationClick(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selection du fichier sql");
		fileChooser.setInitialDirectory(new File("C:\\Users\\Berthold\\Downloads"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("fichier sql", "*.sql"));
		Stage ownerWindow = (Stage) anchopane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(ownerWindow);
		cheminRestauration.setText(file.getAbsolutePath());
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSauvegarderClick(ActionEvent event) throws IOException, InterruptedException {
		backupDB();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRestaurerClick(ActionEvent event) throws IOException, InterruptedException {
		restore();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void backupDB() throws IOException, InterruptedException {

		Process p = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			p = runtime.exec(
					"mysqldump --user=super --password=super --host=127.0.0.1 --port=3306 --skip-add-locks --skip-disable-keys --skip-set-charset --skip-comments --result-file="
							+ directory.getAbsolutePath() + "/enfin.sql --databases db_jpa");
//change the dbpass and dbname with your dbpass and dbname
			int processComplete = p.waitFor();

			if (processComplete == 0) {

				System.out.println("Backup created successfully!");
				MethodUtilitaire.saveAlert(null, "Sauvegarde réussie", "Sauvegarde réussie");

			} else {
				MethodUtilitaire.deleteNoPersonSelectedAlert("Error unable to save", "Error unable to save",
						"Verify server and try again");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void restore() throws IOException, InterruptedException {
		Process p = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			p = runtime.exec(
					"mysql --user=super --password=super  --host=127.0.0.1 --port=3306 db_jpa <C:/Users/Berthold/Desktop/www/enfin.sql", null, null);
			// change the dbpass and dbname with your dbpass and dbname
			int processComplete = p.waitFor();
                                                                                               
			if (processComplete == 0) {

				System.out.println("Restore created successfully!");

			} else {
				System.out.println("Could not create the Restore file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// mysql -u super -p super db_jpa -host=127.0.0.1 --port=3306 <
	// C:/Users/Berthold/Desktop/www/enfin.sql egalement utiliser pour restaurer
	// mysqldump -usuper -psuper db_jpa --host=127.0.0.1 --port=3306 >
	// C:/Users/Berthold/Desktop/www/hello.sql pour un backup
}
