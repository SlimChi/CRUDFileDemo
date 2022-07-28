package fr.cs.application;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import metier.Personne;
import metier.Repertoire;

public class Main extends Application {

	static final String APP_NAME = ("Gestion des contacts");
	private Stage primaryStage;
	private BorderPane menuLayout;
	private Repertoire repertoire;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APP_NAME);

		try {
			FXMLLoader myFXMLloader = new FXMLLoader(Main.class.getResource("menu.fxml"));
			menuLayout = myFXMLloader.load();
			Scene scene = new Scene(menuLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			MenuController menuController = myFXMLloader.getController();
			menuController.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static void main(String[] args) {

		launch(args);
	}

	public Repertoire getRepertoire() {
		return repertoire;
	}

	public void setRepertoire(File file) {

		this.repertoire = new Repertoire(file);
	}

	void repertoireChanged() {

		if (repertoire != null && repertoire.isModified()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

			alert.getButtonTypes().clear();

			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);
			alert.setTitle("Fichier modifié");

			alert.setContentText("Le fichier à été modifié, voulez vous sauvegarder ?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.YES) {

				repertoire.sauver();
			}
		}

	}

	public void showGestionContact() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("contact.fxml"));
			SplitPane contactView = loader.load();
			menuLayout.setCenter(contactView);

			ContactController contactController = loader.getController();

			contactController.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fermer() {
		repertoireChanged();
		repertoire = null;
		menuLayout.setCenter(null);

	}

	public boolean showModifContact(Personne contact, String titre) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("ModifierContact.fxml"));
			AnchorPane anchorePane = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle(titre);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(anchorePane);
			dialogStage.setScene(scene);

			ModifierContactController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.modifierContact(contact);
			controller.setTitle(titre);

			dialogStage.showAndWait();
			if (controller.isConfirmed())
				repertoire.setModified(true);
			return controller.isConfirmed();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public void stop() throws Exception {
		repertoireChanged();
		super.stop();
	}

}
