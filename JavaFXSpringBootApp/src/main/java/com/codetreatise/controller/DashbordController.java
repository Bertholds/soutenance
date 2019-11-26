package com.codetreatise.controller;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Operation;
import com.codetreatise.repository.OperationRepository;
import com.codetreatise.service.MethodUtilitaire;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

@Controller
public class DashbordController implements Initializable {
	@FXML
	private TableView<Operation> operationTab;
	@FXML
	private TableColumn<Operation, Long> id;
	@FXML
	private TableColumn<Operation, String> ip;
	@FXML
	private TableColumn<Operation, String> cible;
	@FXML
	private TableColumn<Operation, Time> heur;
	@FXML
	private TableColumn<Operation, String> operation;
	@FXML
	private TableColumn<Operation, String> userId;
	@FXML
	private TextField recherche;

	@Autowired
	private OperationRepository operationRepository;

	private ObservableList<Operation> operationList = FXCollections.observableArrayList();

	// Event Listener on Button.onAction
	@FXML
	public void handleDeleteClick(ActionEvent event) {
		ObservableList<Operation> selectedItems = operationTab.getSelectionModel().getSelectedItems();
		ArrayList<Operation> selectedIDs = new ArrayList<Operation>();
		for (Operation row : selectedItems) {
			selectedIDs.add(row);
		}
		System.out.println(selectedItems.size());
		System.out.println(" la taille de la liste est "+selectedIDs.size());
		if (selectedIDs.size() != 0) {
			if (MethodUtilitaire.confirmationDialog(operation, "Confirmation de suppression",
					"Confirmation de suppression", "Voulez vous supprimer cette liste d'operations ?")) {
				operationTab.getItems().removeAll(selectedIDs);
				for (Operation o : selectedIDs) {
					operationRepository.delete(o);
				}
				MethodUtilitaire.saveAlert(null, "Suppression reussie", "La suppression s'est effectu�e avec succ�s");
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Op�ration non selectionn�e", "Op�ration non selectionn�e",
					"Selectionner au prealable l'op�ration a supprimer et reessayer");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleRefreshClick(ActionEvent event) {
		operationList.clear();
		operationList.addAll(operationRepository.findAll());
		operationTab.setItems(operationList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleDeleteAllClick(ActionEvent event) {
		operationTab.getSelectionModel().selectAll();
		ObservableList<Operation> operation = operationTab.getSelectionModel().getSelectedItems();
		if (operation.size() != 0) {
			if(MethodUtilitaire.confirmationDialog(null, "Confirmation de suppression", "Confirmation de suppression", "Voulez vous vider lma liste des op�rations effectu�es au sein du syst�me ?")) {
				operationTab.getItems().removeAll(operation);
				operationRepository.deleteAll();
				MethodUtilitaire.saveAlert(null, "Suppression reussie",
						"Toutes les op�rations ont �t� effac�es avec succ�s !");
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Empty table", "The table is empty",
					"No items to delete in table.");
		}
	}

	// Event Listener on TextField.onKeyReleased
	@FXML
	public void handleReleasedClick(KeyEvent event) {
		FilteredList<Operation> filteredOperation = new FilteredList<Operation>(operationList, e -> true);
		recherche.setOnKeyReleased(e -> {
			recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredOperation.setPredicate((Predicate<? super Operation>) operation -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String newValueFilter = newValue.toLowerCase();
					if (operation.getUtilisateur().getNom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (operation.getUtilisateur().getPrenom().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (operation.getDate().toString().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (operation.getName().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (operation.getAddress().toLowerCase().contains(newValueFilter)) {
						return true;
					} else if (operation.getCible().toLowerCase().contains(newValueFilter)) {
						return true;
					}
					return false;
				});
			});
		});

		SortedList<Operation> sortedList = new SortedList<Operation>(filteredOperation);
		sortedList.comparatorProperty().bind(operationTab.comparatorProperty());
		operationTab.setItems(sortedList);
	}

	// Event Listener on Button.onAction
	@FXML
	public void handlePrintClick(ActionEvent event) {
		// TODO Autogenerated
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setColumProperties();
		operationTab.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	private void setColumProperties() {
		id.setCellValueFactory(new PropertyValueFactory<>("id_operation"));
		ip.setCellValueFactory(new PropertyValueFactory<>("address"));
		cible.setCellValueFactory(new PropertyValueFactory<>("cible"));
		heur.setCellValueFactory(new PropertyValueFactory<>("date"));
		operation.setCellValueFactory(new PropertyValueFactory<>("name"));
		userId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operation, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Operation, String> param) {
						return new SimpleStringProperty(param.getValue().getUtilisateur().getNom() + " "
								+ param.getValue().getUtilisateur().getPrenom());
					}
				});

	}
}
