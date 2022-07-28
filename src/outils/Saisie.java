package outils;

import java.util.Scanner;

public final class Saisie {
	private static Scanner monScanner = new Scanner(System.in);

	private Saisie() {
	}

	public static byte saisirUnByte(String message) {
		message += " : ";
		return (byte) saisirEntier(message, -128, 127);
	}

	public static byte saisirUnByte(String message, byte valeurMini,
			byte valeurMaxi) {
		return (byte) saisirUnEntier(message, valeurMini, valeurMaxi);
	}

	public static int saisirUnEntier(String message) {
		message += " : ";
		return saisirEntier(message, -2147483648, 2147483647);
	}

	public static int saisirUnEntier(String message, int valeurMini,
			int valeurMaxi) {
		message += String.format(" (%d -> %d) : ", valeurMini, valeurMaxi);
		return saisirEntier(message, valeurMini, valeurMaxi);
	}

	private static int saisirEntier(String message, int valeurMini,
			int valeurMaxi) {
		int rep = 0;
		boolean isError;
		// tant que isError est vrai
		do {
			System.out.print(message);
			try {
				// on essaye de convertir la saisie en valeur de type int
				rep = Integer.parseInt(monScanner.nextLine());
				// si on a réussi mais que la valeur n'est pas dans les bornes
				// définies
				// isError devient vrai sinon isError devient faux
				isError = rep < valeurMini || rep > valeurMaxi;
			} catch (Exception e) {
				// si la conversion en entier a échoué, isError devient vrai
				isError = true;
			}
		} while (isError);
		return rep;
	}

	public static String saisirUneChaineMaj(String message,
			String valeursAutorisees, int longueurDeChaine) {
		boolean isCorrect;
		String saisie;
		message = String.format("%s (%s) ", message, valeursAutorisees);
		// on recommence la saisie tant qu'elle n'est pas correcte
		do {
			saisie = saisirUneChaine(message).toUpperCase();
			isCorrect = saisie.length() == longueurDeChaine;
			int i = 0;
			while (isCorrect && i < longueurDeChaine) {
					isCorrect = isIn(valeursAutorisees, saisie.charAt(i++)) ;
			}
		} while (!isCorrect);
		return saisie;
	}

	private static boolean isIn(String chaineDeReference,
			char caractereCherche) {
		return chaineDeReference.indexOf(caractereCherche) >= 0;
	}

	public static String saisirUneChaine(String message) {
		System.out.print(message);
		return monScanner.nextLine();
	}

	public static String saisirUnTelephone(String message) {
		while (true)
			try {
				System.out.print(message);
				return OutilsChaine.toTelephone(monScanner.nextLine());
			} catch (TelephoneNumberException e) {
				System.out.println(e.getMessage());
			}
	}

}
