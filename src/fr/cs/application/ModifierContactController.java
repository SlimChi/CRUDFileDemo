package fr.cs.application;

import application.ControllTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import metier.Personne;

public class ModifierContactController {

	private Stage dialogStage;
	private Personne contact;
	private boolean confirmed;

	@FXML
	private GridPane gridPan;

	@FXML
	private ControllTextField modifNom;

	@FXML
	private ControllTextField modifPrenom;

	@FXML
	private ControllTextField modifTel;
	
	@FXML
	private Label title;

	@FXML
	public void initialize() {

		String telephone = "^(\\+33[\\s.]?[1-9]|0[1-9])([\\s.]*[0-9]{2}){4}$";
		modifTel.setParameter("  OK", " ERROR", telephone);

		String nom = "^[a-zA-Z]{1}[\s\\-'a-zA-Z]*$";
		modifNom.setParameter("  OK", " ERROR", nom);

		String prenom = "^[a-zA-Z]{1}[\s\\-'a-zA-Z]*$";
		modifPrenom.setParameter("  OK", " ERROR", prenom);

	}

	@FXML
	public void annuler() {
		confirmed = false;
		dialogStage.close();
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		confirmed = false;
	}

	public void modifierContact(Personne contact) {
		this.contact = contact;
		modifNom.setText(contact.getNom());
		modifPrenom.setText(contact.getPrenom());
		modifTel.setText(contact.getTelephoneProperty());

	}

	public boolean isConfirmed() {
		return confirmed;
	}

	@FXML
	public void confirmer() {
		if (!modifNom.isCorrect() || !modifPrenom.isCorrect() || !modifTel.isCorrect()) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur de saisie");
			alert.setContentText("Veuillez vérifier votre saisie !!");
			alert.showAndWait();

		} else {
			contact.setAll(modifNom.getText(), modifPrenom.getText(), modifTel.getText());
			confirmed = true;
			dialogStage.close();
		}
	}


	@FXML
	public void valider() {
		if (modifNom.isCorrect() && modifPrenom.isCorrect() && modifTel.isCorrect()) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur de saisie");
			alert.setContentText("Veuillez vérifier votre saisie !!");
			alert.showAndWait();
		} else {
			contact.setAll(modifNom.getText(), modifPrenom.getText(), modifTel.getText());
			confirmed = true;
			dialogStage.close();
		}
	}

	public void setTitle(String title) {
		
		this.title.setText(title);
	}

	


}
