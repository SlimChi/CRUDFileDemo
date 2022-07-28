package metier;

import java.io.File;

import dao.PersonneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class Repertoire {
	private File nom;
	private ObservableList<Personne> allContacts;
	private FilteredList<Personne> listeFilrer;
	private SortedList<Personne> listeTrier;
	private PersonneDAO personneDAO;
	private boolean modified;

	public Repertoire(File file) {

		personneDAO = new PersonneDAO(file);
		allContacts = FXCollections.observableArrayList(personneDAO.lire());
		
		
		listeFilrer = new FilteredList<>(allContacts, critere -> true);
		listeTrier = new SortedList<>(listeFilrer);
	}

	public File getNom() {
		return nom;
	}

	public SortedList<Personne> getContacts() {
		return listeTrier;
	}

	public void ajouter(Personne personneAjoute) {
		if (allContacts.indexOf(personneAjoute) == -1) {
			allContacts.add(personneAjoute);
			modified = true;
		}
	}

	public void supprimer(Personne personneSupprimer) {
		allContacts.remove(personneSupprimer);
		modified = true;
	}

	public boolean isModified() {
		return modified;
	}

	public void  setModified(boolean modified) {
		this.modified = modified;
	}

	public void charger() {
		allContacts = FXCollections.observableArrayList(personneDAO.lire());
		listeFilrer = new FilteredList<>(allContacts, critere -> true);
		listeTrier = new SortedList<>(listeFilrer);

	}

	public void sauver() {
		personneDAO.ecrire(allContacts);
		modified = false;
	}

	public void filtrerContact(String filtre) {
		listeFilrer.setPredicate(personne -> isContains(filtre, personne));
	}

	private boolean isContains(String filtre, Personne personne) {
		boolean inNom = personne.getNom().contains(filtre.toUpperCase());
		boolean inPrenom = personne.getPrenom().toUpperCase().contains(filtre.toUpperCase());
		return inNom || inPrenom;
	}

	public void changeFile(File fichierSelectionner) {
		personneDAO = new PersonneDAO(fichierSelectionner);

	}
}
