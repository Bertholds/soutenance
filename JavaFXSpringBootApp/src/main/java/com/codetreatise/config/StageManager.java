package com.codetreatise.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import org.slf4j.Logger;

import com.codetreatise.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

	private static final Logger LOG = getLogger(StageManager.class);
	private    Stage primaryStage ;
	private final SpringFXMLLoader springFXMLLoader;

	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}

	public void switchScene(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
		show(viewRootNodeHierarchy, view.getTitle());
	}
	
	//this method permit to display next stage and show previous stage
	public void switchSceneShowPreviousStage(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
		showPreviousStage(viewRootNodeHierarchy, view.getTitle());
	}
	
	//this method permit to show stage dialog in mode init owner
	public void switchSceneShowPreviousStageInitOwner(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
		showPreviousStageInitOwner(viewRootNodeHierarchy, view.getTitle());
	}

	private void show(final Parent rootnode, String title) {
		Scene scene = prepareScene(rootnode);
		// scene.getStylesheets().add("/styles/Styles.css");

		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();

		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
	}
	
	//this method create a new stage and display him
	private void showPreviousStage(final Parent rootnode, String title) {
		Scene scene = prepareSceneShowPreviousScene(rootnode);
		// scene.getStylesheets().add("/styles/Styles.css");

		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage = new Stage();
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.initModality(Modality.WINDOW_MODAL);
		//primaryStage.initOwner(this.primaryStage);

		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
	}
	
	//this method permit to define owner on stage dialog
	private void showPreviousStageInitOwner(final Parent rootnode, String title) {
		Scene scene = prepareSceneShowPreviousScene(rootnode);
		// scene.getStylesheets().add("/styles/Styles.css");

		// primaryStage.initStyle(StageStyle.TRANSPARENT);
	    Stage  primaryStage = new Stage();
	    primaryStage.setTitle(title);
	    primaryStage.setScene(scene);
	    primaryStage.sizeToScene();
	    primaryStage.centerOnScreen();
	    primaryStage.initModality(Modality.APPLICATION_MODAL);
	    primaryStage.initOwner(this.primaryStage);
	    primaryStage.setResizable(false);

		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
	}

	private Scene prepareScene(Parent rootnode) {
		
		Scene scene = primaryStage.getScene();
		System.out.println("numero 1");

		if (scene == null) {
			System.out.println("numero 2");
			scene = new Scene(rootnode);
		}
		System.out.println("numero 3");
		scene.setRoot(rootnode);
		return scene;
	}
	
private Scene prepareSceneShowPreviousScene(Parent rootnode) {
		
		Scene scene = new Scene(rootnode);
		System.out.println("numero 1");
		return scene;
	}
	

	/**
	 * Loads the object hierarchy from a FXML document and returns to root node of
	 * that hierarchy.
	 *
	 * @return Parent root node of the FXML document hierarchy
	 */
	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
		} catch (Exception exception) {
			logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
		}
		return rootNode;
	}

	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

}
