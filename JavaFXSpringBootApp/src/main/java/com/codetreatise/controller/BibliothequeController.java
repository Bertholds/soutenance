package com.codetreatise.controller;

import java.net.Inet4Address;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Categorie;
import com.codetreatise.bean.Classe;
import com.codetreatise.bean.Document;
import com.codetreatise.bean.Emprint;
import com.codetreatise.bean.Etudiant;
import com.codetreatise.repository.CategorieRepository;
import com.codetreatise.repository.ClasseRepository;
import com.codetreatise.repository.DocumentRepository;
import com.codetreatise.repository.EmprintRepository;
import com.codetreatise.repository.StudentRepository;
import com.codetreatise.service.MethodUtilitaire;
import com.codetreatise.service.impl.CategorieServiceImpl;
import com.codetreatise.service.impl.DocumentServiceImpl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

@Controller
public class BibliothequeController implements Initializable {
	@FXML
	private DatePicker date;
	@FXML
	private TableView<Document> livreTab;

	@FXML
	private TableColumn<Document, Long> idDocumentC;

	@FXML
	private TableColumn<Document, String> titreC;

	@FXML
	private TableColumn<Document, String> auteurC;

	@FXML
	private TableColumn<Document, String> isbnC;

	@FXML
	private TableColumn<Document, String> idCategorieC;

	@FXML
	private TextField idDocument;

	@FXML
	private TextField titre;

	@FXML
	private TextField auteur;

	@FXML
	private TextField isbn;

	@FXML
	private ComboBox<String> categorie;

	@FXML
	private ComboBox<String> filter10;

	@FXML
	private TextField recherche1;

	@FXML
	private ComboBox<String> filtre2;

	@FXML
	private ListView<String> listCategorie;

	@FXML
	private TextField libele;

	@FXML
	private TableView<Document> livreEmprinterTab;
	
	@FXML
	private TableColumn<Document, Long> idDocumentCE;
	
	@FXML
	private TableColumn<Document, String> titreCE;
	
	@FXML
	private TableColumn<Document, String> auteurCE;
	
	@FXML
	private TableColumn<Document, String> isbnCE;

	@FXML
	private ListView<String> listDocumentSolliciter;

	@FXML
	private ListView<String> listEtudiantEmprintant;

	@FXML
	private TableView<Etudiant> studentTab;

	@FXML
	private TableColumn<?, ?> idCE;

	@FXML
	private TableColumn<?, ?> nomCE;

	@FXML
	private TableColumn<?, ?> classeCE;

	@FXML
	private ComboBox<String> filtreClasse;

	@FXML
	private TextField recherche2;

	@FXML
	private TableView<Emprint> emprintTabP;

	@FXML
	private TableColumn<Emprint, Long> idEmprintTC;

	@FXML
	private TableColumn<Emprint, Date> dateTC;

	@FXML
	private TableColumn<Emprint, String> statusTC;

	@FXML
	private TableColumn<Emprint, String> idDocumentTC;

	@FXML
	private TableColumn<Emprint, String> titreTC;

	@FXML
	private TableColumn<Emprint, String> auteurTC;

	@FXML
	private TableColumn<Emprint, String> idEtudiantTC;

	@FXML
	private TableColumn<Emprint, String> nomTC;

	@FXML
	private TableColumn<Emprint, String> prenomTC;

	@FXML
	private TableColumn<Emprint, String> classeTC;

	@FXML
	private ComboBox<String> filter;

	@FXML
	private Spinner<Integer> etudiantId;

	@FXML
	private Spinner<Integer> documentId;

	@FXML
	private TextField recherche3;

	int x = 1;
	List<Categorie> list;

	@Autowired
	CategorieRepository categorieRepository;
	@Autowired
	CategorieServiceImpl categorieServiceImpl;
	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private DocumentServiceImpl documentServiceImpl;
	@Autowired
	private EmprintRepository emprintRepository;

	ObservableList<String> classeList = FXCollections.observableArrayList();
	ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
	ObservableList<String> categoryList = FXCollections.observableArrayList();
	ObservableList<String> categoryFiltreList = FXCollections.observableArrayList();
	ObservableList<String> voir10DernierList = FXCollections.observableArrayList();
	ObservableList<Document> documentList = FXCollections.observableArrayList();
	ObservableList<Emprint> emprintList = FXCollections.observableArrayList();
	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0);
	SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0);

    @FXML
    private void handleLivreSolliciterReleased(MouseEvent event) {
          List<Integer> list = emprintRepository.findLivreSolliciter();
          System.out.println(list.size());
         // h
    	//listDocumentSolliciter.getItems().a
    }
    
    @FXML
    private void handleEtudiantEmprintantReleased(MouseEvent event) {

    }
	
	// Event Listener on Button.onAction 
	@FXML
	public void handleDeleteClick(ActionEvent event) {
		Document selectedDocument = livreTab.getSelectionModel().getSelectedItem();
		if (selectedDocument != null) {
			if (MethodUtilitaire.confirmationDialog(selectedDocument, "confirm to delete document",
					"confirm to delete document", "delete document " + selectedDocument.getTitre() + " ?")) {
				livreTab.getItems().remove(livreTab.getSelectionModel().getSelectedIndex());
				documentRepository.delete(selectedDocument);
				MethodUtilitaire.saveAlert(selectedDocument, "Delete successful",
						"document " + selectedDocument.getTitre() + "has Deleted successful");
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No document Selected",
					"Please select a document in the table.");
		}
	}

	@FXML
	public void handleEditDocumentClick(ActionEvent event) {
		if (isInputValid()) {
			Document document = documentRepository.findOne(Long.parseLong(getIdDocument()));
			document.setAuteur(getAuteur());
			document.setCategorie(getCategoryObjet());
			document.setISBN(getIsbn());
			document.setTitre(getTitre());
			if (MethodUtilitaire.confirmationDialog(document, "confirm to update document",
					"confirm to update document", "update document " + document.getTitre() + " ?"))
				documentServiceImpl.update(document);
			MethodUtilitaire.saveAlert(document, "updated successful", "this document has Deleted successful");
			clearField();
			loadDataOnTableDocument();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClearClick(ActionEvent event) {
		clearField();
	}

	@FXML
	public void handleLoadStudentClick(ActionEvent event) {
		loadDataOntablestudentTab();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshClick(ActionEvent event) {
		loadDataOnTableDocument();
	}

	// Event Listener on TextField[#recherche1].onKeyReleased
	@FXML
	public void handleFilterPressed1(KeyEvent event) {
		FilteredList<Document> filteredDocument = new FilteredList<Document>(documentList, e -> true);
		recherche1.setOnKeyReleased(e -> {
			recherche1.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredDocument.setPredicate((Predicate<? super Document>) document -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (document.getId_document().toString().contains(newValueFilter)) {
						return true;
					} else if (document.getTitre().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (document.getAuteur().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (document.getISBN().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Document> sortedList = new SortedList<Document>(filteredDocument);
		sortedList.comparatorProperty().bind(livreTab.comparatorProperty());
		livreTab.setItems(sortedList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSaveClick(ActionEvent event) {
		if (isInputValid()) {
			Document document = new Document();
			document.setAuteur(getAuteur());
			document.setCategorie(getCategoryObjet());
			document.setISBN(getIsbn());
			document.setTitre(getTitre());
			document.setStatus("disponible");
			if (MethodUtilitaire.confirmationDialog(document, "Confirm to save document", "Confirm to save document",
					"Do you want to save document " + document.getTitre() + " ?"))
				documentRepository.save(document);
			MethodUtilitaire.saveAlert(document, "Save successful",
					"document " + document.getTitre() + " has saved successful");
			clearField();
			loadDataOnTableDocument();
		}
	}

	@FXML
	public void handeDeleteCategoryClick(ActionEvent event) {
		String selecteCategory = listCategorie.getSelectionModel().getSelectedItem();
		if (selecteCategory != null) {
			if (MethodUtilitaire.confirmationDialog(selecteCategory, "Confirm to delete category",
					"Confirm to delete category", "delete category " + selecteCategory + " ?"))
				listCategorie.getItems().remove(selecteCategory);
			Categorie categorie = categorieRepository.findByLibele(selecteCategory);
			categorieRepository.delete(categorie);
			MethodUtilitaire.saveAlert(categorie, "Delete successful",
					"Category " + categorie.getLibele() + " has deleted successful");
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No category Selected",
					"Please select a category in the list.");
		}
	}

	public void handleEditCategoryClick(ActionEvent event) {
		String selecteCategory = listCategorie.getSelectionModel().getSelectedItem();
		if (selecteCategory != null) {
			Categorie categorie = categorieRepository.findByLibele(selecteCategory);
			if (MethodUtilitaire.confirmationDialog(selecteCategory, "Confirm to update category",
					"Confirm to update category",
					"update category " + selecteCategory + " to " + libele.getText() + " ?")) {
				categorie.setLibele(libele.getText());
				categorieServiceImpl.update(categorie);
				MethodUtilitaire.saveAlert(categorie, "update successful",
						"Category " + selecteCategory + " has updated successful");
			}
			loadAllCategory();
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No category Selected",
					"Please select a category in the list.");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleAddClick(ActionEvent event) {
		if (libele.getText() != null || libele.getText().length() != 0) {
			Categorie categorie = new Categorie();
			categorie.setLibele(libele.getText());
			if (MethodUtilitaire.confirmationDialog(categorie, "Confirm to save category", "Confirm to save category",
					"Do you want to create category " + categorie.getLibele() + " ?")) {
				categorieRepository.save(categorie);
				MethodUtilitaire.saveAlert(categorie, "Save successful",
						"Category " + categorie.getLibele() + " has saved successful");
				libele.clear();
			}
			loadAllCategory();
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Invalid field", "Invalid field", "Check field libele");
		}
	}

	// Event Listener on TextField[#recherche2].onKeyReleased
	@FXML
	public void handleFilterPressed2(KeyEvent event) {
		FilteredList<Etudiant> filteredetudiants = new FilteredList<Etudiant>(etudiantList, e -> true);
		recherche2.setOnKeyReleased(e -> {
			recherche2.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredetudiants.setPredicate((Predicate<? super Etudiant>) etudiant -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (etudiant.getId().toString().contains(newValueFilter)) {
						return true;
					} else if (etudiant.getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (etudiant.getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Etudiant> sortedList = new SortedList<Etudiant>(filteredetudiants);
		sortedList.comparatorProperty().bind(studentTab.comparatorProperty());
		studentTab.setItems(sortedList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshLivreEmprinterClick(ActionEvent event) {
		documentList.clear();
		documentList.addAll(documentRepository.findByStatus());
		livreEmprinterTab.setItems(documentList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handlePrintClick(ActionEvent event) {

	}

	// Event Listener on Button.onAction
	@FXML
	public void handleDeleteEmprintClick(ActionEvent event) {
		ObservableList<Emprint> selectedItems = emprintTabP.getSelectionModel().getSelectedItems();
		ArrayList<Emprint> selectedIDs = new ArrayList<Emprint>();
		for (Emprint row : selectedItems) {
			selectedIDs.add(row);
		}
		if (selectedIDs.size() != 0) {
			emprintTabP.getItems().removeAll(selectedIDs);
			for (Emprint e : selectedIDs) {
				emprintRepository.delete(e);
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("No Selection", "No pointing Selected",
					"Please select a pointing in the table.");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handle10dernierClick(ActionEvent event) {
		emprintList.clear();
		if (filter10.getSelectionModel().getSelectedIndex() == 0) {
			emprintList.addAll(emprintRepository.findByStatus());
			emprintTabP.setItems(emprintList);
		} else if (filter10.getSelectionModel().getSelectedIndex() == 1) {
			emprintList.addAll(emprintRepository.findByStatus2());
			emprintTabP.setItems(emprintList);
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshTabClick(ActionEvent event) {
		loadDataOnTableEmprint();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleLivreDispo(ActionEvent event) {
		emprintList.clear();
		// emprintList.addAll(emprintRepository.findByStatus(disponible));
		emprintTabP.setItems(emprintList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleEmprinterClick(ActionEvent event) throws ParseException {
		if (isInputValidTabEmprint()) {
			try {
				Document document = getDocument();
				if(document.getStatus()=="Non disponible")
					MethodUtilitaire.deleteNoPersonSelectedAlert("Document is busing", "Document is busing", "Warning! this document is busing");
				else {
					Emprint emprint = new Emprint();
					emprint.setDate(getDateEmprint());
					emprint.setStatus("Non disponible");
					document.setStatus("Non disponible");
					emprint.setEtudiant(getEtudiant());
					if (MethodUtilitaire.confirmationDialog(emprint, "Confirm to save trace", "Confirm to save trace",
							"Do you want to save trace concerning " + emprint.getEtudiant().getNom() + " "
									+ emprint.getEtudiant().getPrenom() + " whit document " + document.getTitre() + " ?")) {
						emprint.setDocument(documentServiceImpl.update(document));
						emprintRepository.save(emprint);
						MethodUtilitaire.saveAlert(emprint, "Save successful", "trace has saved successful");
						loadDataOnTableEmprint();
					}
					clearFieldEmprint();
				}
			} catch (Exception e) {
				e.printStackTrace();
				MethodUtilitaire.deleteNoPersonSelectedAlert("Etudiant id or document id is not exist",
						"Etudiant id or document id is not exist", "please check etudiant id or document id");
			}
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRemettreClick(ActionEvent event) throws ParseException {
		if (isInputValidTabEmprint()) {
			try {
				Emprint emprint = new Emprint();
				emprint.setDate(getDateEmprint());
				emprint.setStatus("Disponible");
				Document document = getDocument();
				document.setStatus("Disponible");
				emprint.setEtudiant(getEtudiant());
				if (MethodUtilitaire.confirmationDialog(emprint, "Confirm to save delivery", "Confirm to save delivery",
						"Do you want to save delivery concerning " + emprint.getEtudiant().getNom() + " "
								+ emprint.getEtudiant().getPrenom() + " whit document " + document.getTitre() + " ?")) {
					emprint.setDocument(documentServiceImpl.update(document));
					emprintRepository.save(emprint);
					MethodUtilitaire.saveAlert(emprint, "Save successful", "delivery has saved successful");
					loadDataOnTableEmprint();
				}
				clearFieldEmprint();
			} catch (Exception e) {
				e.printStackTrace();
				MethodUtilitaire.deleteNoPersonSelectedAlert("Etudiant id or document id is not exist",
						"Etudiant id or document id is not exist", "please check etudiant id or document id");
			}
		}
	}

	// Event Listener on TextField[#recherche2].onKeyReleased
	@FXML
	public void handleFilterPressed3(KeyEvent event) {
		FilteredList<Emprint> filteredEmprint = new FilteredList<Emprint>(emprintList, e -> true);
		recherche3.setOnKeyReleased(e -> {
			recherche3.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredEmprint.setPredicate((Predicate<? super Emprint>) emprint -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (emprint.getEtudiant().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (emprint.getEtudiant().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (emprint.getDocument().getTitre().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (emprint.getDocument().getAuteur().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (emprint.getDate().toString().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Emprint> sortedList = new SortedList<Emprint>(filteredEmprint);
		sortedList.comparatorProperty().bind(emprintTabP.comparatorProperty());
		emprintTabP.setItems(sortedList);
	}

	private List<Integer> trieListInteger(List<Integer> list){

		for(int i=0; i<list.size(); i++) {
			for(int j=i+1; j==list.size(); j++) {
				if(list.get(i)<list.get(j)) {
					list.add(i, j);
					list.add(j, i);
					list.remove(j+1);
				}
			}
		}
		
		return null;
	}
	
	private void convertFormatDateAllPicker() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		date.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	private void setFiltreClasse() {
		classeList.clear();
		List<Classe> list = classeRepository.findAll();
		for (Classe c : list) {
			classeList.add(c.getNom());
		}
		classeList.add("All class");
		filtreClasse.setItems(classeList);
		filtreClasse.getSelectionModel().selectLast();
	}

	private void setCategorie() {
		categoryList.clear();
		list = categorieRepository.findAll();
		for (Categorie c : list) {
			categoryList.add(c.getLibele());
		}
		categorie.setItems(categoryList);
	}

	private void setFiltre2() {
		categoryFiltreList.clear();
		categoryFiltreList.addAll(categoryList);
		categoryFiltreList.add("All document");
		filtre2.setItems(categoryFiltreList);
		filtre2.getSelectionModel().selectLast();
	}

	private void setFiltre10() {
		voir10DernierList.clear();
		voir10DernierList.addAll("derniers empreints", "dernieres remises");
		filter10.setItems(voir10DernierList);
	}

	private void setFilter() {
		categoryFiltreList.clear();
		categoryFiltreList.addAll(categoryList);
		categoryFiltreList.add("All document");
		filter.setItems(categoryFiltreList);
		filter.getSelectionModel().selectLast();
	}

	private void loadAllCategory() {
		List<Categorie> list = categorieRepository.findAll();
		if (x == 1) {
			for (Categorie c : list) {
				listCategorie.getItems().add(c.getLibele());
			}
			x++;
			System.out.println(x);
		} else {
			list.clear();
			listCategorie.getItems().clear();
			System.out.println("clear");
			list = categorieRepository.findAll();
			for (Categorie c : list) {
				listCategorie.getItems().add(c.getLibele());
			}
		}
	}

	private void loadDataOnTableEmprint() {
		emprintList.clear();
		if (filter.getSelectionModel().getSelectedItem() == "All document")
			emprintList.addAll(emprintRepository.findAll());
		else
			emprintList.addAll(emprintRepository.findByCategory(filter.getSelectionModel().getSelectedItem()));
		emprintTabP.setItems(emprintList);
	}

	private void loadDataOnTableDocument() {
		documentList.clear();
		if (filtre2.getSelectionModel().getSelectedItem() == "All document")
			documentList.addAll(documentRepository.findAll());
		else
			documentList.addAll(documentRepository.findByCategory(filtre2.getSelectionModel().getSelectedItem()));
		livreTab.setItems(documentList);
	}

	private void loadDataOntablestudentTab() {
		etudiantList.clear();
		if (filtreClasse.getSelectionModel().getSelectedItem() == "All class") {
			etudiantList.addAll(studentRepository.findAll());
			studentTab.setItems(etudiantList);
		} else {
			etudiantList.addAll(studentRepository.findByClasse(filtreClasse.getSelectionModel().getSelectedItem()));
			studentTab.setItems(etudiantList);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		emprintTabP.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		loadAllCategory();
		setCategorie();
		setFiltre2();
		setFilter();
		setFiltre10();
		setstudentTabPropertiesValue();
		setlivreTabPropertiesValue();
		setEmprintTabPropertiesValue();
		setLivreEmprinterTabPropertiesValues();
		setFiltreClasse();
		etudiantId.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		documentId.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		etudiantId.setValueFactory(valueFactory);
		documentId.setValueFactory(valueFactory2);
		convertFormatDateAllPicker();
		livreTab.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			EditDocument(newValue);
		});
		emprintTabP.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			EditEmprint(newValue);
		});
	}

	private void setLivreEmprinterTabPropertiesValues() {
		idDocumentCE.setCellValueFactory(new PropertyValueFactory<>("id_document"));
		titreCE.setCellValueFactory(new PropertyValueFactory<>("titre"));
		auteurCE.setCellValueFactory(new PropertyValueFactory<>("auteur"));
		isbnCE.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
	}

	private void setEmprintTabPropertiesValue() {
		idEmprintTC.setCellValueFactory(new PropertyValueFactory<>("id_emprint"));
		dateTC.setCellValueFactory(new PropertyValueFactory<>("date"));
		statusTC.setCellValueFactory(new PropertyValueFactory<>("status"));
		idDocumentTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getDocument().getId_document().toString());
					}
				});
		titreTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getDocument().getTitre());
					}
				});
		auteurTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getDocument().getAuteur());
					}
				});
		idEtudiantTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getId().toString());
					}
				});
		nomTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getNom());
					}
				});
		prenomTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getPrenom());
					}
				});
		classeTC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Emprint, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Emprint, String> param) {
						return new SimpleStringProperty(param.getValue().getEtudiant().getClasse().getNom());
					}
				});

	}

	private void setstudentTabPropertiesValue() {
		idCE.setCellValueFactory(new PropertyValueFactory<>("id"));
		nomCE.setCellValueFactory(new PropertyValueFactory<>("nom"));
		classeCE.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	}

	private void setlivreTabPropertiesValue() {
		idDocumentC.setCellValueFactory(new PropertyValueFactory<>("id_document"));
		titreC.setCellValueFactory(new PropertyValueFactory<>("titre"));
		auteurC.setCellValueFactory(new PropertyValueFactory<>("auteur"));
		isbnC.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
		idCategorieC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Document, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Document, String> param) {
						return new SimpleStringProperty(param.getValue().getCategorie().getLibele());
					}
				});
	}

	private String getIdDocument() {
		return idDocument.getText();
	}

	private String getTitre() {
		return titre.getText();
	}

	private String getAuteur() {
		return auteur.getText();
	}

	private String getIsbn() {
		return isbn.getText();
	}

	private String getCategory() {
		return categorie.getSelectionModel().getSelectedItem();
	}

	private Categorie getCategoryObjet() {
		Categorie categorie = categorieRepository.findByLibele(getCategory());
		return categorie;
	}

	private String getEtudiantId() {
		return etudiantId.getEditor().getText();
	}

	private String getDocumentId() {
		return documentId.getEditor().getText();
	}

	private String getDate() {
		return date.getEditor().getText();
	}

	private Date getDateEmprint() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(getDate());
		return date;
	}

	private Document getDocument() {
		Document document = documentRepository.findOne(Long.parseLong(getDocumentId()));
		return document;
	}

	private Etudiant getEtudiant() {
		Etudiant etudiant = studentRepository.findOne(Long.parseLong(getEtudiantId()));
		return etudiant;
	}

	private void clearField() {
		idDocument.clear();
		titre.clear();
		auteur.clear();
		isbn.clear();
		categorie.getSelectionModel().clearSelection();
	}

	private void clearFieldEmprint() {
		etudiantId.getEditor().setText("0");
		documentId.getEditor().setText("0");
		date.getEditor().clear();
	}

	private void EditDocument(Document document) {
		if(document!=null) {
			idDocument.setText(document.getId_document().toString());
			titre.setText(document.getTitre());
			auteur.setText(document.getAuteur());
			isbn.setText(document.getISBN());
			categorie.getSelectionModel().select(document.getCategorie().getLibele());
		}
	}

	private void EditEmprint(Emprint newValue) {
		if(newValue!=null) {
			etudiantId.getEditor().setText(newValue.getEtudiant().getId().toString());
			documentId.getEditor().setText(newValue.getDocument().getId_document().toString());
			date.getEditor().setText(newValue.getDate().toString());
		}

	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (getTitre() == null || getTitre().length() == 0) {
			errorMessage += "No valid field title!\n";
		}

		if (getAuteur() == null || getAuteur().length() == 0) {
			errorMessage += "No valid field auteur!\n";
		}

		if (getIsbn() == null || getIsbn().length() == 0) {
			errorMessage += "No valid field ISBN!\n";
		}

		if (getCategory() == null || getCategory().length() == 0) {
			errorMessage += "No valid field category!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}

	private boolean isInputValidTabEmprint() {
		String errorMessage = "";

		if (getEtudiantId() == null || getEtudiantId().length() == 0) {
			errorMessage += "No valid field etudiant id!\n";
		}

		if (getDocumentId() == null || getDocumentId().length() == 0) {
			errorMessage += "No valid field document id!\n";
		}

		if (getDate() == null || getDate().length() == 0) {
			errorMessage += "No valid field date!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			MethodUtilitaire.errorMessageAlert("Invalid Fields", "Please correct invalid fields", errorMessage);
			return false;
		}
	}
}
