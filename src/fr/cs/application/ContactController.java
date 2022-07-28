package fr.cs.application;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import metier.Personne;
import metier.Repertoire;

public class ContactController {

	@FXML
	private TextField recherche;

	@FXML
	private TableView<Personne> tableContact;

	@FXML
	private TableColumn<Personne, String> nomColumn;

	@FXML
	private TableColumn<Personne, String> PrenomColumn;

	@FXML
	private TableColumn<Personne, String> NumTelColumn;

	@FXML
	private AnchorPane detailShow;

	@FXML
	private Label labelNom;

	@FXML
	private Label labelNumTel;

	@FXML
	private Label labelPrenom;

	@FXML
	private Button supprimer;

	@FXML
	MenuItem smallAjouter;

	@FXML
	MenuItem smallModifier;

	@FXML
	MenuItem smallSupprimer;

	private Repertoire repertoire;

	private Personne personneSelected;

	private Main mainApp;

	@FXML
	private void initialize() {

		nomColumn.setCellValueFactory(celleData -> celleData.getValue().getNomProperty());
		PrenomColumn.setCellValueFactory(celleData -> celleData.getValue().getPrenomProperty());
		NumTelColumn.setCellValueFactory(celleData -> celleData.getValue().getTelephone());
		recherche.textProperty().addListener((observable, oldValue, newValue) -> filtrer(newValue));
		tableContact.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> afficherDetails(newValue));

		tableContact.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setPersonneSelected(newValue));

		menuItemsDisable();
		detailDisable(false);
	}

	public void filtrer(String filter) {
		menuItemsDisable();
		repertoire.filtrerContact(filter);
		
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
		repertoire = mainApp.getRepertoire();
		tableContact.setItems(repertoire.getContacts());
		repertoire.getContacts().comparatorProperty().bind(tableContact.comparatorProperty());
	}

	private void detailDisable(boolean bool) {
		detailShow.setVisible(bool);

	}

	@FXML
	public void supprimerContact() {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.getButtonTypes().clear();

		alert.getButtonTypes().add(ButtonType.YES);
		alert.getButtonTypes().add(ButtonType.NO);
		alert.setTitle("Fichier modifié");

		alert.setContentText("Voulez vous supprimer le fichier ?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.YES) {

			repertoire.supprimer(personneSelected);
		}
	}

	@FXML
	public void modifer() {
		if (personneSelected != null) {
			mainApp.showModifContact(personneSelected, "Modifier contact");
			String newValue = recherche.getText();
			repertoire.filtrerContact(newValue);
			afficherDetails(personneSelected);
		}
	}

	@FXML
	public void ajouter() {
		Personne newContact = new Personne("", "", "");
		if (mainApp.showModifContact(newContact, "Ajouter contact")) {
			repertoire.ajouter(newContact);
			tableContact.getSelectionModel().select(null);
		}

	}

	@FXML
	public void imprimer() {
		PrinterJob printerJob = PrinterJob.createPrinterJob();
		printerJob.showPrintDialog(mainApp.getPrimaryStage());
		boolean success = printerJob.printPage(tableContact);
		if (success) {
			printerJob.endJob();
		}

	}

	private void setPersonneSelected(Personne personneSelected) {
		this.personneSelected = personneSelected;

	}

	private void afficherDetails(Personne personne) {
		if (personne != null) {
			labelNom.setText(personne.getNom());
			labelPrenom.setText(personne.getPrenom());
			labelNumTel.setText(personne.getTelephoneProperty());

			detailDisable(true);
			
			smallModifier.setDisable(false);
			smallSupprimer.setDisable(false);
		}else {
			smallModifier.setDisable(true);
			smallSupprimer.setDisable(true);
		}
	}
	
	private void menuItemsDisable( ) {
		
		if (tableContact.getItems().isEmpty()) {
		
		smallModifier.setDisable(true);
		smallSupprimer.setDisable(true);
		}

	}

}
