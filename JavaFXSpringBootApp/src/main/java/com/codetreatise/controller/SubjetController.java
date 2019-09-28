package com.codetreatise.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
	private ListView<?> listStudent;
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
	private Spinner<Integer> coefSpinner;

	@FXML
	private Button btnIgnore;

	private Button bk = new Button("Display all");
	String loggeur;
	String loggeur2;
	String loggeur3;
	boolean isEdit = false;
	ToggleButton toggleButton;

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
			avec_cc.setDisable(false);
			sans_cc.setDisable(false);
		} else
			MethodUtilitaire.deleteNoPersonSelectedAlert("No student selected", " nobody selected in student table",
					"Please select firstly student in second table");
		return etudiant;
	}

	// Event Listener on Button[#edit].onAction
	@FXML
	public void handleEditClick(ActionEvent event) {
         Note note = noteTab.getSelectionModel().getSelectedItem();
         if(note !=null) {
        		id_student.setText(note.getEtudiant().getId().toString());
    			matiere.setText(loggeur2);
    			id.setText(note.getId_note().toString());
    			normal.setText(String.valueOf(note.getNormal()));
    			cc.setText(String.valueOf(note.getCc()));
    			avec_cc.setDisable(false);
    			sans_cc.setDisable(false);
    			isEdit = true;
         }else
        	 MethodUtilitaire.deleteNoPersonSelectedAlert("No row selected", " no row selected in note table",
 					"Please select firstly row in first table"); 
	}
	
	@FXML
	public void handleClearFeildClick(ActionEvent event) {
          clearField();
          isEdit = false;
	}
	
	 @FXML
	  private  void handleSelectRow(MouseEvent  event) {
		 edit.setDisable(false);
		 validate.setDisable(false);
	    }

	// Event Listener on Button[#validate].onAction
	@FXML
	public void handleValidateClick(ActionEvent event) {
		Note newNote = null;
		if (isInputValid()) {
			if(isEdit) {
				Note note = noteRepository.findOne(Long.parseLong(id.getText()));
				note.setNormal(getNormal());
				note.setAppreciation(getObservation());
				if (avec_cc.isSelected()) {
	                note.setCc(getcc());
	                System.out.println("---------------------------avec cc"+cc.getText());
	                System.out.println(slider.getValue());
	                note.setNote(getNote());
	                note.setMoyenne((getNote()*Integer.parseInt(coefSpinner.getEditor().getText()))/getTotalCoefficientMatieres());
				} else if(sans_cc.isSelected()) {
					note.setCc(getNormal());
					   System.out.println("---------------------------sans cc");
					note.setNote(getNormal());
					note.setMoyenne((getNormal() * Integer.parseInt(coefSpinner.getEditor().getText()))
							/ getTotalCoefficientMatieres());
				}
				newNote = noteServiceImpl.update(note);
				MethodUtilitaire.saveAlert(newNote, "update successful", "the student " + newNote.getEtudiant().getNom() + " "
						+ newNote.getEtudiant().getPrenom() + " has been updated whith normal " + newNote.getNormal()+" and cc "+newNote.getCc());
				clearField();
				loadDataOnTable();
				validate.setDisable(true);
			}
			else {
				Note note = new Note();
				note.setEtudiant(handleAddClick(event));
				note.setMatiere(getMatieres());
				note.setNormal(getNormal());
				note.setAppreciation(getObservation());
				if (avec_cc.isSelected()) {
	                note.setCc(getcc());
	                System.out.println("---------------------------avec cc"+cc.getText());
	                System.out.println(slider.getValue());
	                note.setNote(getNote());
	                note.setMoyenne((getNote()*Integer.parseInt(coefSpinner.getEditor().getText()))/getTotalCoefficientMatieres());
				} else if(sans_cc.isSelected()) {
					note.setCc(getNormal());
					   System.out.println("---------------------------sans cc");
					note.setNote(getNormal());
					note.setMoyenne((getNormal() * Integer.parseInt(coefSpinner.getEditor().getText()))
							/ getTotalCoefficientMatieres());
				}
				newNote = noteRepository.save(note);
				MethodUtilitaire.saveAlert(newNote, "Save successful", "the student " + newNote.getEtudiant().getNom() + " "
						+ newNote.getEtudiant().getPrenom() + " has been saved whith normal " + newNote.getNormal()+" and cc "+newNote.getCc());
				clearField();
				loadDataOnTable();
				validate.setDisable(true);
			}
		}
	}

	private float getNote() {
		int n = (int) (100-slider.getValue());
		float cc = (float) (getcc()*slider.getValue())/100;
		float normal = (getNormal()*n)/100;
		return normal+cc;
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

	@FXML
	private void handleIgnoreClick(ActionEvent event) {

	}

	private void setListNiveau() {
		bk.setPrefSize(98, 29);
		listNiveauButton.clear();
		ArrayList<String> niveau = classeRepository.loadAllNiveau();
		for (String n : niveau) {
			Button btn = new Button(n);
			btn.setPrefSize(150, 29);
			btn.setOnAction(e -> {
				loggeur3 = n;
				ArrayList<Classe> classe = classeRepository.findByNiveau(n);
				studentTab2.getItems().clear();
				ListMatiere.getItems().clear();
				listClasse.getItems().clear();
				for (Classe c : classe) {
					Button b = new Button(c.getNom());
					b.setPrefSize(98, 29);
					b.setOnAction(event -> {
						loggeur = b.getText();
						ListMatiere.getItems().clear();
						studentTab2.getItems().clear();
						ArrayList<String> list = matiereRepository.loadMatiereByClasse("%" + c.getNom() + "%");
						for (String m : list) {
							Button btne = new Button(m);
							btne.setPrefSize(200, 29);
							btne.setOnAction(evene -> {
								for (Classe cl : listDeClasse()) {
									if (loggeur != cl.getNom()) {
										loggeur2 = btne.getText();
										if (coefSpinner.isVisible()) {
											coefSpinner.getEditor().setText(String.valueOf(getCoefficient()));
										}
										if (btnIgnore.isVisible()) {

										}
										List<Etudiant> lists = studentRepository.findByClasse(loggeur);
										listEtudiant.clear();
										studentTab2.getItems().clear();
										listEtudiant.addAll(lists);
										studentTab2.setItems(listEtudiant);
									}
								}
							});
							ListMatiere.getItems().add(btne);
						}
					});
					listClasse.getItems().add(b);
				}
				bk.setOnAction(ev -> {
					ListMatiere.getItems().clear();
					ArrayList<String> list = matiereRepository.loadAllMatiere();
					for (String m : list) {
						Button btne = new Button(m);
						btne.setPrefSize(200, 29);
						ListMatiere.getItems().add(btne);
					}
				});
				listClasse.getItems().add(bk);
			});
			listNiveauButton.add(btn);
		}
		listNiveau.setPadding(new Insets(0, 110, 0, 110));
		Button button = new Button("Display all classe");
		button.setOnAction(e -> {
			ListMatiere.getItems().clear();
			listClasse.getItems().clear();
			ArrayList<String> list = classeRepository.loadAllClass();
			for (String str : list) {
				Button bt = new Button(str);
				bt.setPrefSize(98, 29);
				bt.setOnAction(ev -> {
					ListMatiere.getItems().clear();
					for (Classe k : listDeClasse()) {
						if (bt.getText().equals(k.getNom())) {
							ArrayList<String> lis = matiereRepository.loadMatiereByClasse("%" + k.getNiveau() + "%");
							for (String m : lis) {
								Button btne = new Button(m);
								btne.setPrefSize(200, 29);
								ListMatiere.getItems().add(btne);
							}
							break;
						}
					}
				});
				listClasse.getItems().add(bt);
			}
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
				  float value1 = (float) (normal.getText().trim().isEmpty() ? 0.0 : (Float.parseFloat( normal.getText() )*(100-slider.getValue()))/100);
	                float value2 = (float) (cc.getText().trim().isEmpty() ? 0.0 :  (Float.parseFloat( cc.getText() )*slider.getValue())/100);
	                return value1 + value2;
			}
		}, normal.textProperty(),cc.textProperty());
		preview.textProperty().bind(binding.asString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		slider.setValue(30);
		setSpinnerCoef();
		SelectItemInStudentTab2();
		disableBtn();
		setListNiveau();
		listDeClasse();
		setColumNoteProperties();
		setColumEtudiantProperties();
	}

	private void setColumNoteProperties() {
		id_tab.setCellValueFactory(new PropertyValueFactory<Note, Long>("id_note"));
		id_studentTab.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Note,String>, ObservableValue<String>>() {
			
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
		edit.setDisable(true);
		add.setDisable(true);
		avec_cc.setDisable(true);
		sans_cc.setDisable(true);
		slider.setVisible(false);
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
		List<Note> list = noteRepository.findByClasse(loggeur2);
		listNote.addAll(list);
		noteTab.setItems(listNote);
	}

	private void SelectItemInStudentTab2() {
		studentTab2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Etudiant>() {

			@Override
			public void changed(ObservableValue<? extends Etudiant> observable, Etudiant oldValue, Etudiant newValue) {
				add.setDisable(false);
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

}
