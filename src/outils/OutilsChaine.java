package outils;


public class OutilsChaine {

    private OutilsChaine() {

    }

    public static String toNomPropre(String chaine) {
    	
    	if(!chaine.isEmpty()) {
            StringBuilder resultat = new StringBuilder();
            resultat.append(chaine.substring(0, 1).toUpperCase());
            for (int i = 1; i < chaine.length(); i++) {
                if (chaine.substring(i - 1, i).equals(" ")
                        || chaine.substring(i - 1, i).equals("-")) {
                    resultat.append(chaine.substring(i, i + 1).toUpperCase());
                } else {
                    resultat.append(chaine.substring(i, i + 1).toLowerCase());
                }
            }
            return resultat.toString();
    	}
    	
    	return chaine;

    }

    public static String toTelephone(String telephone) {
    	
    	if(!telephone.isEmpty()) {
    		
            StringBuilder stringBuilder = epureTelephone(telephone);
            int position = 2;
            if (stringBuilder.length() < 10)
                throw new TelephoneNumberException("Format incorrect");
            if (stringBuilder.charAt(0) == '+') {
                stringBuilder.insert(3," ");
                position=5;
            }
            for (; position < stringBuilder.length() - 1; position += 3) {
                stringBuilder.insert(position," ");
            }

            String resultat = stringBuilder.toString();
            if (resultat.charAt(0) == '+' && resultat.length() != 17)
                throw new TelephoneNumberException("Format incorrect");
            return stringBuilder.toString();
    	}
    	
    	return telephone;


    }

    private static StringBuilder epureTelephone(String chaine) {
        chaine = chaine.trim();
        StringBuilder stringBuilder = new StringBuilder();
        int pos;
        String chiffres = "+0123456789";
        for (int i = 0; i < chaine.length(); i++) {
            pos = chiffres.indexOf(chaine.substring(i, i + 1));
            if ((pos == 0 && i == 0) || pos > 0)
                stringBuilder.append(chiffres.substring(pos, pos + 1));
        }
        supprimeZeroPlus(stringBuilder);
        return stringBuilder;
    }

    private static void supprimeZeroPlus(StringBuilder stringBuilder) {
    	
    	if(!stringBuilder.isEmpty()) {
            if (stringBuilder.charAt(0) == '+' && stringBuilder.charAt(3) == '0')
                stringBuilder.deleteCharAt(3);
    	}

    }

}
