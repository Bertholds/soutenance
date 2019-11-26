package com.codetreatise.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Matiere;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.MatiereRepository;
import com.codetreatise.service.MethodUtilitaire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@Controller
public class SettingController implements Initializable {

	@FXML
	private Slider tailleMotDePasse;

	@FXML
	private ListView<String> listMenus;

	@FXML
	private ListView<String> listAdmin;

	@FXML
	private ListView<String> listSuperAdmin;

	@FXML
	private ListView<String> listBibliotheque;

	@FXML
	private ListView<String> listEnseignant;

	@FXML
	private ListView<String> listInfirmerie;

	@FXML
	private ListView<String> listPointeur;

	@FXML
	private ListView<String> listDiscipline;

	@FXML
	private ListView<String> listCreance;

	@FXML
	private Label previousStyle;

	@FXML
	private TextField schoolName;

	@FXML
	private Button btnBlueStyle;

	@FXML
	private Button btnDarkStyle;

	@FXML
	private Button btnWhiteStyle;

	@FXML
	private Pane stylePane;

	@FXML
	private ListView<TextField> listMatiere;
	@FXML
	private ListView<TextField> listMotDePasseMatiere;
	@FXML
	private ListView<TextField> listClasses;
	@FXML
	private ListView<TextField> listMotDePaaseSalle;
	int verification = 1;
	List<Matiere> matieres;
    List<Classe> classes;

	List<ListView<String>> listViews = new LinkedList<ListView<String>>();

	@Autowired
	ModuleController moduleController;
	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private MatiereRepository matiereRepository;

	@FXML
	private void handleGenerateKeyMatiere(ActionEvent event) {
		for (int i = 0; i < listMotDePasseMatiere.getItems().size(); i++) {
			System.out.println("////////////////////////" + listMotDePasseMatiere.getItems().size());
			TextField textField = listMotDePasseMatiere.getItems().get(i);
			textField.setText(aleatoirePassword());
		}
	}

	@FXML
	private void handleGenerateKeyClasse(ActionEvent event) {
		for (int i = 0; i < listMotDePaaseSalle.getItems().size(); i++) {
			TextField textField = listMotDePaaseSalle.getItems().get(i);
			textField.setText(aleatoirePassword());
		}
	}

	@FXML
	private void mouseSliderMoDePasseEntered(MouseEvent event) {
		tailleMotDePasse.setCursor(Cursor.HAND);
        tailleMotDePasse.setTooltip(new Tooltip("Définisser la longueur de vos mot de passe"));
	}

	@FXML
	private void MouseAnchorpaneSecuriteEntered(MouseEvent event) throws IOException {
		if (verification == 1) {
			matieres = matiereRepository.findAll();
			classes = classeRepository.findAll();
			verification++;
		} else {
			listMotDePaaseSalle.getItems().clear();
			listMotDePasseMatiere.getItems().clear();
			listMatiere.getItems().clear();
			listClasses.getItems().clear();
			matieres = matiereRepository.findAll();
			classes = classeRepository.findAll();
		}

		for (int i = 0; i < matieres.size(); i++) {
			TextField textField = new TextField(matieres.get(i).getNom());
			textField.setEditable(false);
			listMatiere.getItems().add(textField);
			listMotDePasseMatiere.getItems().add(new TextField());
		}
		for (int i = 0; i < classes.size(); i++) {
			TextField textField = new TextField(classes.get(i).getNom());
			textField.setEditable(false);
			listClasses.getItems().add(textField);
			listMotDePaaseSalle.getItems().add(new TextField());
		}

		readConfigurationMotDePasse("C:/wamp/saveconfigurationclassepassword.txt", listMotDePaaseSalle);
		readConfigurationMotDePasse("C:/wamp/saveconfigurationmatierepassword.txt", listMotDePasseMatiere);
	}

	@FXML
	private void handleMenuSensibleClick(ActionEvent event) {

	}

	@FXML
	private void handleSaveConfigurationClasseClick(ActionEvent event) throws IOException {
		saveConfigurationMotDePasseNote("C:\\wamp\\saveconfigurationclassepassword.txt", listMotDePaaseSalle);
		MethodUtilitaire.saveAlert(null, "Sauvegarde de configuration",
				"La configuration a ete sauvegarder avec success");
	}

	@FXML
	private void handleSaveConfigurationMatiereClick(ActionEvent event) throws IOException {
		try {
			saveConfigurationMotDePasseNote("C:\\wamp\\saveconfigurationmatierepassword.txt", listMotDePasseMatiere);
			MethodUtilitaire.saveAlert(null, "Sauvegarde de configuration",
					"La configuration a ete sauvegarder avec success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handleAddAdminClick(ActionEvent event) {
		addMenu(listMenus, listAdmin);
	}

	@FXML
	void handleAddBibliothequeClick(ActionEvent event) {
		addMenu(listMenus, listBibliotheque);
	}

	@FXML
	void handleAddCreanceClick(ActionEvent event) {
		addMenu(listMenus, listCreance);
	}

	@FXML
	void handleAddDisciplineClick(ActionEvent event) {
		addMenu(listMenus, listDiscipline);
	}

	@FXML
	void handleAddEnseignantClick(ActionEvent event) {
		addMenu(listMenus, listEnseignant);
	}

	@FXML
	void handleAddInfirmerieClick(ActionEvent event) {
		addMenu(listMenus, listInfirmerie);
	}

	@FXML
	void handleAddPointeurClick(ActionEvent event) {
		addMenu(listMenus, listPointeur);
	}

	@FXML
	void handleAddSuperAdminClick(ActionEvent event) {
		addMenu(listMenus, listSuperAdmin);
	}

	@FXML
	private void handleBleuStyleClick(ActionEvent event) {
		stylePane.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		btnBlueStyle.setTextFill(Color.WHITE);
		String path = "C:/wamp/selectedStyle.txt";
		File file = new File(path);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(
					"stylePane.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));");
			bufferedWriter.newLine();
			bufferedWriter.write("previousStyle.setTextFill(Color.WHITE);");
			bufferedWriter.close();
			fileWriter.close();
			MethodUtilitaire.saveAlert(null, "Mise a jour du thème reussie",
					"Le thème de l'établissement a été mis a jour avec success !\n"
							+ "La modification sera prise en charge apres le redemarrage de l'application");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void handleDarkStyleClick(ActionEvent event) {
		stylePane.setBackground(
				new Background(new BackgroundFill(Color.web("#1d1d1d"), CornerRadii.EMPTY, Insets.EMPTY)));
		btnBlueStyle.setTextFill(Color.WHITE);
		String path = "C:/wamp/selectedStyle.txt";
		File file = new File(path);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(
					"stylePane.setBackground(new Background(new BackgroundFill(Color.web(\"#1d1d1d\"), CornerRadii.EMPTY, Insets.EMPTY)));");
			bufferedWriter.newLine();
			bufferedWriter.write("previousStyle.setTextFill(Color.WHITE);");
			bufferedWriter.close();
			fileWriter.close();
			MethodUtilitaire.saveAlert(null, "Mise a jour du thème reussie",
					"Le thème de l'établissement a été mis a jour avec success !\n"
							+ "La modification sera prise en charge apres le redemarrage de l'application");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void handleRemoveAdminClick(ActionEvent event) {
		removeMenu(listAdmin);
	}

	@FXML
	void handleRemoveBibliothequeClick(ActionEvent event) {
		removeMenu(listBibliotheque);
	}

	@FXML
	void handleRemoveCreanceClick(ActionEvent event) {
		removeMenu(listCreance);
	}

	@FXML
	void handleRemoveDisciplineClick(ActionEvent event) {
		removeMenu(listDiscipline);
	}

	@FXML
	void handleRemoveEnseignantClick(ActionEvent event) {
		removeMenu(listEnseignant);
	}

	@FXML
	void handleRemoveInfirmerieClick(ActionEvent event) {
		removeMenu(listInfirmerie);
	}

	@FXML
	void handleRemovePointeurClick(ActionEvent event) {
		removeMenu(listPointeur);
	}

	@FXML
	void handleRemoveSuperAdminClick(ActionEvent event) {
		String menu = listSuperAdmin.getSelectionModel().getSelectedItem();
		if (menu != null) {
			listSuperAdmin.getItems().remove(menu);
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No menu selected", "No menu selected",
					"Please select a menu firstly");
	}

	@FXML
	void handleSaveClick(ActionEvent event) throws IOException {
		for (ListView<String> list : getAllList()) {
			File file = new File("C:/wamp/" + list.getId() + ".txt");
			FileWriter fileWriter = new FileWriter(file);
			for (int i = 0; i < list.getItems().size(); i++) {
				String menu = list.getItems().get(i);
				fileWriter.write(menu + ";");
			}
			fileWriter.close();
		}
		MethodUtilitaire.saveAlert(null, "Sauvegarde reuusie", "La sauvegarde s'est achevé avec succès ");
	}

	@FXML
	void handleValidateClick(ActionEvent event) throws IOException {
		String path = "C:/wamp/writeLabel.txt";
		File file = new File(path);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(schoolName.getText());
		fileWriter.close();
		MethodUtilitaire.saveAlert(null, "Mise a jour du label reussie",
				"Le label de l'établissement a été mis a jour avec success !\n"
						+ "La modification sera prise en charge apres le redemarrage de l'application");
	}

	@FXML
	private void handleWhiteStyleClick(ActionEvent event) {
		stylePane.setBackground(
				new Background(new BackgroundFill(Color.web("#ddd6c6"), CornerRadii.EMPTY, Insets.EMPTY)));
		previousStyle.setTextFill(Color.BLACK);
		String path = "C:/wamp/selectedStyle.txt";
		File file = new File(path);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(
					"stylePane.setBackground(new Background(new BackgroundFill(Color.web(\"#ddd6c6\"), CornerRadii.EMPTY, Insets.EMPTY)));");
			bufferedWriter.newLine();
			bufferedWriter.write("previousStyle.setTextFill(Color.BLACK);");
			bufferedWriter.close();
			fileWriter.close();
			MethodUtilitaire.saveAlert(null, "Mise a jour du thème reussie",
					"Le thème de l'établissement a été mis a jour avec success !\n"
							+ "La modification sera prise en charge apres le redemarrage de l'application");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void mouseBlueStyleEntered(MouseEvent event) {
		stylePane.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		previousStyle.setTextFill(Color.WHITE);
	}

	@FXML
	void mouseDarkStyleEntered(MouseEvent event) {
		stylePane.setBackground(
				new Background(new BackgroundFill(Color.web("#1d1d1d"), CornerRadii.EMPTY, Insets.EMPTY)));
		previousStyle.setTextFill(Color.WHITE);
	}

	@FXML
	void mouseWhiteStyleEntered(MouseEvent event) {
		stylePane.setBackground(
				new Background(new BackgroundFill(Color.web("#ddd6c6"), CornerRadii.EMPTY, Insets.EMPTY)));
		previousStyle.setTextFill(Color.BLACK);
	}

	@FXML
	void mouseDarkStyleExited(MouseEvent event) throws FileNotFoundException {
		File file = new File("C:/wamp/selectedStyle.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		try {
			String s = bufferedReader.readLine();
			stylePane.setBackground(
					new Background(new BackgroundFill(Color.web("#ddd6c6"), CornerRadii.EMPTY, Insets.EMPTY)));
			previousStyle.setTextFill(Color.BLACK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void mouseBlueStyleExited(MouseEvent event) {

	}

	@FXML
	void mouseWhiteStyleExited(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Button> buttons = moduleController.getAllButton();
		for (Button b : buttons) {
			listMenus.getItems().add(b.getText());
		}

		try {
			loadMenu();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void addMenu(ListView<String> source, ListView<String> destination) {
		String menu = source.getSelectionModel().getSelectedItem();
		if (menu != null) {
			destination.getItems().add(menu);
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No menu selected", "No menu selected",
					"Please select a menu firstly");
	}

	private void removeMenu(ListView<String> source) {
		String menu = source.getSelectionModel().getSelectedItem();
		if (menu != null) {
			source.getItems().remove(menu);
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No menu selected", "No menu selected",
					"Please select a menu firstly");
	}

	private List<ListView<String>> getAllList() {

		listViews.add(listBibliotheque);
		listViews.add(listCreance);
		listViews.add(listDiscipline);
		listViews.add(listEnseignant);
		listViews.add(listInfirmerie);
		listViews.add(listMenus);
		listViews.add(listPointeur);
		listViews.add(listSuperAdmin);
		listViews.add(listAdmin);

		return listViews;
	}

	private void loadMenu() throws Exception {
		for (ListView<String> list : getAllList()) {
			File file = new File("C:/wamp/" + list.getId() + ".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s;
			String[] t = null;
			System.out.println("...........................................");
			while ((s = bufferedReader.readLine()) != null) {
				t = s.split(";");

			}

			for (String menu : t) {
				list.getItems().add(menu);
			}
			fileReader.close();
			bufferedReader.close();
		}

	}

	private String aleatoirePassword() {

		Random random = new Random();
		String alphabet = "AZERTYUIOPQSDFGHJKLMWXCVBNaqwzsxedcrfvtgbyhnuj,ikolpm123456789";
		int longueur = alphabet.length();
		String chaine = "";
		for (int i = 0; i < tailleMotDePasse.getValue(); i++) {
			int k = random.nextInt(longueur);
			chaine += alphabet.charAt(k);
		}
		return chaine;
	}

	private void saveConfigurationMotDePasseNote(String paths, ListView<TextField> list) throws IOException {
		String path = paths;
		File file = new File(path);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < list.getItems().size(); i++) {
			String mdp = list.getItems().get(i).getText();
			bufferedWriter.write(mdp + " ");
			// bufferedWriter.newLine();
		}
		bufferedWriter.close();
		fileWriter.close();
	}

	private void readConfigurationMotDePasse(String path, ListView<TextField> listView) throws IOException {
		File file = new File(path);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String s;
		String[] t = null;
		System.out.println("...........................................");
		while ((s = bufferedReader.readLine()) != null) {
			System.out.println(s.split(" "));
			t = s.split(" ");
		}

		for (int i = 0; i < listView.getItems().size(); i++) {
			System.out.println("---------------------llll" + listView.getItems().size());
			TextField textField = listView.getItems().get(i);
			System.out.println(t[i]);
			textField.setText(t[i]);
		}
		fileReader.close();
		bufferedReader.close();
	}

}
