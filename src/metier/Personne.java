package metier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import outils.OutilsChaine;

public class Personne {
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty telephone;

	public Personne(String nom, String prenom, String telephone) {
		
		this.nom = new SimpleStringProperty(nom.toUpperCase());
		this.prenom = new SimpleStringProperty(OutilsChaine.toNomPropre(prenom));
		this.telephone = new SimpleStringProperty(OutilsChaine.toTelephone(telephone));
		setAll(nom, prenom, telephone);
	}

	public void setAll(String nom, String prenom, String telephone) {
		this.nom.set(nom.toUpperCase());
		this.prenom.set(OutilsChaine.toNomPropre(prenom));
		this.telephone.set(OutilsChaine.toTelephone(telephone));
	}

	public String getNom() {
		return nom.get();
	}

	public StringProperty getNomProperty() {
		return nom;
	}

	public String getPrenom() {
		return prenom.getValue();
	}

	public StringProperty getPrenomProperty() {
		return prenom;
	}

	public StringProperty getTelephone() {
		return telephone;
	}

	public String getTelephoneProperty() {
		return telephone.getValue();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other instanceof Personne otherPersonne)
			return nom.equals(otherPersonne.nom) && prenom.equals(otherPersonne.prenom)
					&& telephone.equals(otherPersonne.telephone);
		return false;
	}

	@Override
	public int hashCode() {
		int resultat = 0;
		int i = 1;
		int mod = 10;
		for (char car : nom.get().toCharArray()){
			resultat += car * (i++ % mod + 1);
		}
		for (char car : prenom.get().toCharArray()) {
			resultat += car * (i++ % mod + 1);
		}
		for (char car : telephone.get().toCharArray()) {
			resultat += car * (i++ % mod + 1);
		}
		return resultat;
	}

	@Override
	public String toString() {
		return String.format("%-30s%20s", prenom + " " + nom, telephone);
	}
}
