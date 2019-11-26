package com.codetreatise.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Coefficient;
import com.codetreatise.bean.Etudiant;
import com.codetreatise.bean.Matiere;
import com.codetreatise.bean.Note;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.CoefficientRepository;
import com.codetreatise.repository.MatiereRepository;
import com.codetreatise.repository.NoteRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.NoteServiceImpl;
import com.codetreatise.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Pair;

@Controller
public class SubjetController implements Initializable {
	@FXML
	private ListView<Button> listClasse;
	@FXML
	private ListView<Button> listNiveau;
	@FXML
	private ListView<Button> ListMatiere;
	@FXML
	private TableView<Note> noteTab;
	@FXML
	private TableColumn<Note, Long> id_tab;
	@FXML
	private TableColumn<Note, String> id_studentTab;
	@FXML
	private TableColumn<Note, String> nom;
	@FXML
	private TableColumn<Note, String> prenom;
	@FXML
	private TableColumn<Note, Float> normalTab;
	@FXML
	private TableColumn<Note, Float> ccTab;
	@FXML
	private TableColumn<Note, Float> finalNoteTab;
	@FXML
	private TableColumn<Note, Float> moyenneTab;
	@FXML
	private TableColumn<Note, Integer> rang;
	@FXML
	private TableColumn<Note, String> observation;
	@FXML
	private TableView<Etudiant> studentTab2;
	@FXML
	private TableColumn<Etudiant, Long> idTab2;
	@FXML
	private TableColumn<Etudiant, String> nomTab2;
	@FXML
	private TableColumn<Etudiant, String> prenomTab2;
	@FXML
	private TableColumn<Etudiant, String> classeTab2;
	@FXML
	private RadioButton avec_cc;

	@FXML
	private ToggleGroup radio;

	@FXML
	private RadioButton sans_cc;

	@FXML
	private Label ccLabel;

	@FXML
	private Slider slider;
	@FXML
	private Button add;
	@FXML
	private Button edit;
	@FXML
	private TextField recherche;
	@FXML
	private TextField id;
	@FXML
	private TextField preview;
	@FXML
	private TextField normal;
	@FXML
	private TextField cc;
	@FXML
	private TextField id_student;
	@FXML
	private TextField matiere;
	@FXML
	private BarChart<?, ?> Barchart;
	@FXML
	private ListView<String> listStudent;
	@FXML
	private ToggleButton nul;
	@FXML
	private ToggleGroup toggle1;
	@FXML
	private ToggleButton faible;
	@FXML
	private ToggleGroup toggle;
	@FXML
	private ToggleButton mediocre;
	@FXML
	private ToggleButton passable;
	@FXML
	private ToggleButton bien;
	@FXML
	private ToggleButton tresBien;
	@FXML
	private ToggleButton exellent;
	@FXML
	private Button validate;
	@FXML
	private Label coefLabel;
	@FXML
	private Label succes;
	@FXML
	private Label echec;
	@FXML
	private Label totaletudiant;

	@FXML
	private Button btnRefresh;

	@FXML
	private Spinner<Integer> coefSpinner;

	@FXML
	private GridPane status;

	private Button bk = new Button("Display all");
	String loggeur;
	String loggeur2;
	String loggeur3;
	boolean isEdit = false;
	boolean response = false;
	ToggleButton toggleButton;
	List<Matiere> matieres;
	List<Classe> classes;
	int verification = 1;
	int verification2 = 2;

	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private MatiereRepository matiereRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private NoteServiceImpl noteServiceImpl;
	@Autowired
	private CoefficientRepository coefficientRepository;

	@Autowired
	@Lazy
	private StageManager stageManager;

	ObservableList<Button> listNiveauButton = FXCollections.observableArrayList();
	ObservableList<Etudiant> listEtudiant = FXCollections.observableArrayList();
	ObservableList<Note> listNote = FXCollections.observableArrayList();
	SpinnerValueFactory<Integer> valueFactory = new IntegerSpinnerValueFactory(0, 10, 0);

	private void setSpinnerCoef() {
		coefSpinner.setValueFactory(valueFactory);
		coefSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
	}

	private void findStudentByClasse() {
		List<Etudiant> lists = studentRepository.findByClasse(loggeur);
		listEtudiant.clear();
		studentTab2.getItems().clear();
		listEtudiant.addAll(lists);
		studentTab2.setItems(listEtudiant);
	}

	private void setStatusValues() {
		totaletudiant.setText(String.valueOf(noteRepository.getTotalEtudiant(loggeur2, loggeur)));
		succes.setText(String.valueOf(noteRepository.getSucces(loggeur2, loggeur)));
		echec.setText(String.valueOf(noteRepository.getEchec(loggeur2, loggeur)));
		status.setVisible(true);
	}

	@FXML
	private void mousseOrdreMeriteClick(MouseEvent event) {
		listStudent.getItems().clear();
		List<Note> list = noteServiceImpl.findByClasseAndMatiereOverOrder(loggeur2, loggeur);
		for (int i = 0; i < list.size(); i++) {
			listStudent.getItems()
					.add(list.get(i).getEtudiant().getNom() + " " + list.get(i).getEtudiant().getPrenom());
		}
	}

	@FXML
	private void MousseOrdreMeriteEntered(MouseEvent event) {
		listStudent.setCursor(Cursor.HAND);
		listStudent.setTooltip(new Tooltip("Click here to charging list"));
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshClick(ActionEvent event) {
		loadDataOnTable();
	}

	// Event Listener on Button[#add].onAction
	@FXML
	public Etudiant handleAddClick(ActionEvent event) {
		Etudiant etudiant = studentTab2.getSelectionModel().getSelectedItem();
		if (etudiant != null) {
			isEdit = false;
			id_student.setText(etudiant.getId().toString());
			matiere.setText(loggeur2);
			validate.setDisable(false);
			avec_cc.setVisible(true);
			sans_cc.setVisible(true);
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No student selected", " nobody selected in student table",
					"Please select firstly student in second table");
		return etudiant;
	}

	// Event Listener on Button[#edit].onAction
	@FXML
	public void handleEditClick(ActionEvent event) {
		Note note = noteTab.getSelectionModel().getSelectedItem();
		if (note != null) {
			id_student.setText(note.getEtudiant().getId().toString());
			matiere.setText(loggeur2);
			id.setText(note.getId_note().toString());
			normal.setText(String.valueOf(note.getNormal()));
			cc.setText(String.valueOf(note.getCc()));
			avec_cc.setVisible(true);
			sans_cc.setVisible(true);
			if(avec_cc.isSelected())
				slider.setVisible(true);
			isEdit = true;
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No row selected", " no row selected in note table",
					"Please select firstly row in first table");
	}

	@FXML
	public void handleClearFeildClick(ActionEvent event) {
		clearField();
		isEdit = false;
	}

	// Event Listener on Button[#validate].onAction
	@FXML
	public void handleValidateClick(ActionEvent event) {
		Note newNote = null;
		if (isInputValid()) {
			if (isEdit) {
				Note note = noteRepository.findOne(Long.parseLong(id.getText()));
				note.setNormal(getNormal());
				note.setAppreciation(getObservation());
				if (avec_cc.isSelected()) {
					note.setCc(getcc());
					System.out.println("---------------------------avec cc" + cc.getText());
					System.out.println(slider.getValue());
					note.setNote(getNote());
					note.setMoyenne((getNote() * Integer.parseInt(coefSpinner.getEditor().getText()))
							/ getTotalCoefficientMatieres());
				} else if (sans_cc.isSelected()) {
					note.setCc(getNormal());
					System.out.println("---------------------------sans cc");
					note.setNote(getNormal());
					note.setMoyenne((getNormal() * Integer.parseInt(coefSpinner.getEditor().getText()))
							/ getTotalCoefficientMatieres());
				}
				newNote = noteServiceImpl.update(note);
				MethodUtilitaire.saveAlert(newNote, "update successful",
						"the student " + newNote.getEtudiant().getNom() + " " + newNote.getEtudiant().getPrenom()
								+ " has been updated whith normal " + newNote.getNormal() + " and cc "
								+ newNote.getCc());
				clearField();
				loadDataOnTable();
				validate.setDisable(true);
				setStatusValues();
			} else {
				Note note = new Note();
				note.setEtudiant(handleAddClick(event));
				note.setMatiere(getMatieres());
				note.setClasse(getClasses());
				note.setNormal(getNormal());
				note.setAppreciation(getObservation());
				if (avec_cc.isSelected()) {
					note.setCc(getcc());
					System.out.println("---------------------------avec cc" + cc.getText());
					System.out.println(slider.getValue());
					note.setNote(getNote());
					note.setMoyenne((getNote() * Integer.parseInt(coefSpinner.getEditor().getText()))
							/ getTotalCoefficientMatieres());
				} else if (sans_cc.isSelected()) {
					note.setCc(getNormal());
					System.out.println("---------------------------sans cc");
					note.setNote(getNormal());
					note.setMoyenne((getNormal() * Integer.parseInt(coefSpinner.getEditor().getText()))
							/ getTotalCoefficientMatieres());
				}
				newNote = noteRepository.save(note);
				MethodUtilitaire.saveAlert(newNote, "Save successful",
						"the student " + newNote.getEtudiant().getNom() + " " + newNote.getEtudiant().getPrenom()
								+ " has been saved whith normal " + newNote.getNormal() + " and cc " + newNote.getCc());
				clearField();
				loadDataOnTable();
				validate.setDisable(true);
				setStatusValues();
			}
		}
	}

	private float getNote() {
		int n = (int) (100 - slider.getValue());
		float cc = (float) (getcc() * slider.getValue()) / 100;
		float normal = (getNormal() * n) / 100;
		return normal + cc;
	}

	private int getTotalCoefficientMatieres() {
		return coefficientRepository.findByCLasse(classeRepository.findByNom(loggeur));
	}

	@FXML
	private void handleSubjectClick(ActionEvent event) {
		stageManager.switchSceneShowPreviousStageInitOwner(FxmlView.MATIERE);
	}

	@FXML
	private void handleAvec_cc(ActionEvent event) {
		slider.setVisible(true);
		ccLabel.setVisible(true);
		cc.setVisible(true);
	}

	@FXML
	private void handleSans_cc(ActionEvent event) {
		cc.setText("0");
		slider.setVisible(false);
		cc.setVisible(false);
		ccLabel.setVisible(false);
	}

//	@FXML
//	private void handleSelectRow(MouseEvent event) {
//		edit.setDisable(false);
//		validate.setDisable(false);
//	}

	private void setListNiveau() {
		bk.setPrefSize(98, 29);
		listNiveauButton.clear();
		ArrayList<String> niveau = classeRepository.loadAllNiveau();
		for (String n : niveau) {
			Button btn = new Button(n);
			btn.setPrefSize(150, 29);
			btn.setOnAction(e -> {
				disableBtn();
				loggeur3 = n;
				ArrayList<Classe> classe = classeRepository.findByNiveau(n);
				studentTab2.getItems().clear();
				noteTab.getItems().clear();
				ListMatiere.getItems().clear();
				listClasse.getItems().clear();
				for (Classe c : classe) {
					Button b = new Button(c.getNom());
					b.setPrefSize(98, 29);
					b.setOnAction(event -> {
						studentTab2.getItems().clear();
						noteTab.getItems().clear();
						disableBtn();
						loggeur = b.getText();
						ListMatiere.getItems().clear();
						studentTab2.getItems().clear();
						try {
							if(dialog(readConfigurationMotDePasse("C:/wamp/saveconfigurationclassepassword.txt",
									getIndexOfSelectedClasse()), loggeur)==true) {
								ArrayList<String> list = matiereRepository.loadMatiereByClasse("%" + c.getNom() + "%");
								for (String m : list) {
									Button btne = new Button(m);
									btne.setPrefSize(200, 29);
									btne.setOnAction(evene -> {
										try {
											loggeur2 = btne.getText();
											if(dialog(readConfigurationMotDePasse("C:/wamp/saveconfigurationmatierepassword.txt", getIndexOfSelectedMatiere()), loggeur2)==true) {
												for (Classe cl : listDeClasse()) {
													if (loggeur != cl.getNom()) {
														//loggeur2 = btne.getText(); a ete ramener a quelques lignes plus haut(4)
														coefSpinner.getEditor().setText(String.valueOf(getCoefficient()));
														findStudentByClasse();
														btnRefresh.setVisible(true);
														recherche.setVisible(true);
													}
												}
												setStatusValues();
											}
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									});
									ListMatiere.getItems().add(btne);
								}
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					});
					listClasse.getItems().add(b);
				}

			});
			listNiveauButton.add(btn);
		}
		listNiveau.setPadding(new Insets(0, 110, 0, 110));
		Button button = new Button("Display all classe");
		button.setOnAction(e -> {
			disableBtn();
			studentTab2.getItems().clear();
			noteTab.getItems().clear();
			ListMatiere.getItems().clear();
			listClasse.getItems().clear();
			ArrayList<String> list = classeRepository.loadAllClass();
			for (String str : list) {
				Button bt = new Button(str);
				bt.setPrefSize(98, 29);
				bt.setOnAction(ev -> {
					loggeur = bt.getText();
					disableBtn();
					studentTab2.getItems().clear();
					noteTab.getItems().clear();
					ListMatiere.getItems().clear();
					try {
						if(dialog(readConfigurationMotDePasse("C:/wamp/saveconfigurationclassepassword.txt",
								getIndexOfSelectedClasse()), loggeur)==true) {
							for (Classe k : listDeClasse()) {
								if (bt.getText().equals(k.getNom())) {
									ArrayList<String> lis = matiereRepository.loadMatiereByClasse("%" + k.getNom() + "%");
									for (String m : lis) {
										Button btne = new Button(m);
										btne.setPrefSize(200, 29);
										btne.setOnAction(evenement -> {
											//loggeur = bt.getText(); a ete transferer a quelques ligne plus haut(13)
											loggeur2 = btne.getText();
											coefSpinner.getEditor().setText(String.valueOf(getCoefficient()));
											findStudentByClasse();
											btnRefresh.setVisible(true);
											recherche.setVisible(true);
											setStatusValues();
										});
										ListMatiere.getItems().add(btne);
									}
									break;
								}
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				listClasse.getItems().add(bt);
			}
			bk.setOnAction(ev -> {
				ListMatiere.getItems().clear();
				ArrayList<String> lists = matiereRepository.loadAllMatiere();
				for (String m : lists) {
					Button btne = new Button(m);
					btne.setDisable(true);
					coefSpinner.getEditor().setText("0");
					btne.setPrefSize(200, 29);
					ListMatiere.getItems().add(btne);
				}
			});
			listClasse.getItems().add(this.bk);
		});
		button.setPrefSize(150, 29);
		listNiveau.getItems().clear();
		listNiveau.setItems(listNiveauButton);
		listNiveau.getItems().add(button);
	}

	private int getCoefficient() {
		Classe classe = classeRepository.findByNom(loggeur);
		Matiere matiere = matiereRepository.findByNom(loggeur2);
		Coefficient coefficient = coefficientRepository.findByCLasseAndMatiere(classe, matiere);
		return coefficient.getCoefficient();
	}

	@FXML
	private void setPreviewNote(ActionEvent event) {
		FloatBinding binding = Bindings.createFloatBinding(new Callable<Float>() {

			@Override
			public Float call() throws Exception {
				float value1 = (float) (normal.getText().trim().isEmpty() ? 0.0
						: (Float.parseFloat(normal.getText()) * (100 - slider.getValue())) / 100);
				float value2 = (float) (cc.getText().trim().isEmpty() ? 0.0
						: (Float.parseFloat(cc.getText()) * slider.getValue()) / 100);
				return value1 + value2;
			}
		}, normal.textProperty(), cc.textProperty());
		preview.textProperty().bind(binding.asString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		slider.setValue(30);
		setSpinnerCoef();
		SelectItemInStudentTab2();
		selectedItemNoteTab();
		disableBtn();
		setListNiveau();
		listDeClasse();
		setColumNoteProperties();
		setColumEtudiantProperties();
	}

	private void setColumNoteProperties() {
		id_tab.setCellValueFactory(new PropertyValueFactory<Note, Long>("id_note"));
		id_studentTab.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Note, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getId().toString());
					}
				});
		nom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Note, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getNom());
			}
		});
		prenom.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Note, String> param) {
				return new SimpleStringProperty(param.getValue().getEtudiant().getPrenom());
			}
		});
		normalTab.setCellValueFactory(new PropertyValueFactory<>("normal"));
		ccTab.setCellValueFactory(new PropertyValueFactory<>("cc"));
		finalNoteTab.setCellValueFactory(new PropertyValueFactory<>("note"));
		moyenneTab.setCellValueFactory(new PropertyValueFactory<>("moyenne"));
		rang.setCellValueFactory(new PropertyValueFactory<>("rang"));
		observation.setCellValueFactory(new PropertyValueFactory<>("appreciation"));
	}

	private void setColumEtudiantProperties() {
		idTab2.setCellValueFactory(new PropertyValueFactory<>("id"));
		nomTab2.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomTab2.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		classeTab2.setCellValueFactory(new PropertyValueFactory<>("classeNom"));
	}

	private List<Classe> listDeClasse() {
		ArrayList<Classe> l = classeRepository.loadAllClassObject();
		return l;
	}

	private void disableBtn() {
		validate.setDisable(true);
		edit.setVisible(false);
		add.setVisible(false);
		avec_cc.setVisible(false);
		sans_cc.setVisible(false);
		slider.setVisible(false);
		btnRefresh.setVisible(false);
		recherche.setVisible(false);
		status.setVisible(false);
	}

	private void clearField() {
		id_student.clear();
		matiere.clear();
		normal.clear();
		id.clear();
		cc.clear();
		toggleButton.setSelected(false);
	}

	private void loadDataOnTable() {
		listNote.clear();
		List<Note> list = noteRepository.findByClasseAndMatiere(loggeur2, loggeur);
		listNote.addAll(list);
		noteTab.setItems(listNote);
	}

	private void SelectItemInStudentTab2() {
		studentTab2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Etudiant>() {

			@Override
			public void changed(ObservableValue<? extends Etudiant> observable, Etudiant oldValue, Etudiant newValue) {
				add.setVisible(true);
			}
		});
	}

	private void selectedItemNoteTab() {
		noteTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {

			@Override
			public void changed(ObservableValue<? extends Note> observable, Note oldValue, Note newValue) {
				edit.setVisible(true);
				validate.setDisable(false);
			}
		});
	}

	private String getId_student() {
		return id_student.getText();
	}

	private String getMatiere() {
		return matiere.getText();
	}

	private float getNormal() {
		float value = 0;
		try {
			value = Float.parseFloat(normal.getText());
		} catch (Exception e) {
			MethodUtilitaire.errorMessageAlert("Error of value", "Value not acceptable",
					"Please check the value of cc or value of normal");
		}
		return value;
	}

	private float getcc() {
		float value = 0;
		try {
			value = Float.parseFloat(cc.getText());
		} catch (Exception e) {
			MethodUtilitaire.errorMessageAlert("Error of value", "Value not acceptable",
					"Please check the value of cc or value of normal");
		}
		return value;
	}

	private String getccIsInputValid() {

		return cc.getText();
	}

	private String getMethodeCalculMoyenne() {
		String str = null;
		if (avec_cc.isSelected())
			str = avec_cc.getText();
		if (sans_cc.isSelected())
			str = sans_cc.getText();
		return str;
	}

	private String getObservation() {
		String str = null;
		if (nul.isSelected()) {
			str = nul.getText();
			toggleButton = nul;
		} else if (faible.isSelected()) {
			str = faible.getText();
			toggleButton = faible;
		} else if (mediocre.isSelected()) {
			str = mediocre.getText();
			toggleButton = mediocre;
		} else if (passable.isSelected()) {
			str = passable.getText();
			toggleButton = passable;
		} else if (bien.isSelected()) {
			str = bien.getText();
			toggleButton = bien;
		} else if (tresBien.isSelected()) {
			str = tresBien.getText();
			toggleButton = tresBien;
		} else if (exellent.isSelected()) {
			str = exellent.getText();
			toggleButton = exellent;
		}
		return str;
	}

	private Matiere getMatieres() {
		Matiere m = matiereRepository.findByNom(getMatiere());
		return m;
	}

	private Classe getClasses() {
		return classeRepository.findByNom(loggeur);
	}

	/*
	 * private int getTotalCoefficientMatieres() { int tc = 0; for(int i=0;
	 * i<coefMap.size(); i++) { for(String matiere: ListMatie ) {
	 * 
	 * } } return tc; }
	 */

	private boolean isInputValid() {
		String errorMessage = "";

		if (getNormal() < 0) {
			errorMessage += "No valid field  note of normal!\n";
		}
		if (getMatiere() == null || getMatiere().length() == 0) {
			errorMessage += "No valid field subject!\n";
		}

		if (getObservation() == null || getObservation().length() == 0) {
			errorMessage += "No valid field observation!\n";
		}

		if (getMethodeCalculMoyenne() == null || getMethodeCalculMoyenne().length() == 0) {
			errorMessage += "Please check the strategie for count average!\n";
		}

		if (getId_student() == null || getId_student().length() == 0) {
			errorMessage += "No valid field id of student!\n";
		}

		if (getccIsInputValid() == null || getccIsInputValid().length() == 0) {
			errorMessage += "No valid field note of continual control!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

	private boolean dialog(String pass, String uname) {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Entr username and password");

		// Set the icon (must be included in the project).
		// dialog.setGraphic(new
		// ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setText(uname);
		username.setEditable(false);
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		password.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> password.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		//Window stage = loginButton.getScene().getWindow();
		//dialog.initOwner(stage);
		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent((usernamePassword) -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
			if (password.getText().equals(pass)) {
				response = true;
			} else {
				MethodUtilitaire.deleteNoPersonSelectedAlert("mot de passe incorrect", "mot de passe incorrect",
						"Vérifiez votre mot de passe et réessayez");
			    response = false;
			}
		});
		return response;
	}

	private int getIndexOfSelectedClasse() {
		int index = -1;
		if (verification == 1) {
			classes = classeRepository.findAll();
			verification++;
		} else {
			classes.clear();
			classes = classeRepository.findAll();
		}
		for (int i = 0; i < classes.size(); i++) {
			System.out.println("Le logueur est" + loggeur);
			System.out.println("Le logueur2 est" + loggeur2);
			if (classes.get(i).getNom().equals(loggeur))
				index = i;
		}
		System.out.println("L'indexb est de: " + index);
		return index;
	}

	private int getIndexOfSelectedMatiere() {
		int index = -1;
		if (verification2 == 2) {
			matieres = matiereRepository.findAll();
			verification2++;
		} else {
			matieres.clear();
			matieres = matiereRepository.findAll();
		}
		for (int i = 0; i < matieres.size(); i++) {
			if (matieres.get(i).getNom().equals(loggeur2))
				index = i;
		}
		return index;
	}

	private String readConfigurationMotDePasse(String path, int index) throws IOException {
		File file = new File(path);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String s;
		String[] t = null;
		System.out.println("...........................................");
		while ((s = bufferedReader.readLine()) != null) {
			System.out.println(s.split(" ").toString());
			t = s.split(" ");
		}
		fileReader.close();
		bufferedReader.close();
		return t[index];
	}

}
