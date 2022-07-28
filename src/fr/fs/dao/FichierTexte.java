package fr.fs.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author François
 *
 */
public class FichierTexte {
	private File nomFichier;

	public FichierTexte(File nomFichier) {
		this.nomFichier = nomFichier;
	}

	public List<String> lire() {
		List<String> lignes = new ArrayList<>();
		// On va essayer de lire le fichier nomFichier
		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(nomFichier))) {

			String ligne;
			while ((ligne = bufferedReader.readLine()) != null) {
				lignes.add(ligne);
			}
		}
		// Si on ne peut pas lire le fichier (mauvais chemin, nom fichier
		// erroné, ...)
		//
		catch (IOException e) {
			System.out.println("Erreur lors de la lecture : " + e.getMessage());
		}
		return lignes;
	}

	public void ecrire(List<String> lignes) {
		try (PrintWriter printwriter = new PrintWriter(
				new FileWriter(nomFichier))) {

			for (String ligne : lignes) {
				printwriter.println(ligne);
			}
		} catch (IOException exception) {
			System.out.println(
					"Erreur lors de l'écriture : " + exception.getMessage());
		}
	}

}
