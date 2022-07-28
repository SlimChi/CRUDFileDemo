package fr.cs.application;

import java.io.File;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MenuController {

	private Main mainApp;

	@FXML
	MenuItem menuItemEnregistrer;

	@FXML
	MenuItem menuItemOuvrir;

	@FXML
	MenuItem menuItemEnregistrerSous;

	@FXML
	MenuItem menuItemFermer;

	@FXML
	MenuItem menuItemSQuitter;

	
	@FXML
	private MenuItem menuItemNouveau;

	@FXML
	private void initialize() {
		menuItemsDisable(true);
	}

	private void menuItemsDisable(boolean bool) {
		menuItemEnregistrer.setDisable(bool);
		menuItemEnregistrerSous.setDisable(bool);
		menuItemFermer.setDisable(bool);

	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void ouvrirFichiher() {
		
		fermerFichier();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrir un fichier");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("text File", "*.txt"));
		fileChooser.setInitialDirectory(new File("\\"));
		File fichierSelectionner = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (fichierSelectionner != null) {
			mainApp.getPrimaryStage().setTitle(Main.APP_NAME + "-" + fichierSelectionner);
			mainApp.setRepertoire(fichierSelectionner);
			mainApp.getRepertoire().charger();
			mainApp.showGestionContact();

			menuItemsDisable(false);

		}

	}

	@FXML
	private void nouveauFichier() {

		fermerFichier();
		File fichierSelectionner = choosFile();
		if (fichierSelectionner != null) {
			mainApp.getPrimaryStage().setTitle(Main.APP_NAME + "-" + fichierSelectionner);
			mainApp.setRepertoire(fichierSelectionner);
			mainApp.getRepertoire().sauver();
			mainApp.showGestionContact();

			menuItemsDisable(false);
		}
	}

	@FXML
	private void enregistrerFichier() {
		mainApp.getRepertoire().sauver();
	}

	@FXML
	private void enregistrerSous() {
		File fichierSelectionner = choosFile();
		if (fichierSelectionner != null) {
			mainApp.getPrimaryStage().setTitle(Main.APP_NAME + "-" + fichierSelectionner);
			mainApp.getRepertoire().changeFile(fichierSelectionner);
			mainApp.getRepertoire().sauver();
			mainApp.showGestionContact();

			menuItemsDisable(false);
		}
	}

	private File choosFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Ouvrir fichier");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("text File", "*.txt"));
		fileChooser.setInitialDirectory(new File("\\"));
		return fileChooser.showSaveDialog(mainApp.getPrimaryStage());
	}
//
//	public void RepertoireModifier() {
//
//	}

	@FXML
	private void fermerFichier() {
		
		menuItemsDisable(true);
		mainApp.getPrimaryStage().setTitle(Main.APP_NAME);
		mainApp.fermer();

	}

	@FXML
	private void quitter() {
		mainApp.repertoireChanged();
		Platform.exit();
		
	}
	
	public void help() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Gestion des contacts");
		alert.setHeaderText("A propos de : ");
		alert.setContentText("Exemple d'application JavaFX\nAuteur : Slimane CHIHATI");
		alert.showAndWait();
	}
}
