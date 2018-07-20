package com.jeu.controller.attaquer;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.controller.defendre.Defense;
import com.jeu.model.Jeu;

public class AttaqueHumaineMastermind extends Attaque{
	
	@Override
	public String proposer(HashMap<String, String> reponse) {
		
		boolean test;
		int chiffre = Defense.nombreDeChiffre -1;
		String message = "Trouver la combinaison à : " + Jeu.longueurMastermind + " chiffres.\n"
							+ "Les chiffres doivent être compris dans l'intervalle 0 à " + chiffre;
		
    	vue.afficherMessage(message);
    	
    	do {
    		
    		saisie = sc.next();
    		try {
    			test = testerCombinaisonSaisie(saisie, Jeu.longueurMastermind, Defense.nombreDeChiffre);
    		}
    		catch(Exception e) {
    			test = false;
    			System.out.println(e.getMessage());
    			vue.afficherMessage(message);
    		}    		
    	}
    	while(!test);
    	
    	return saisie;
	}
    
    public boolean testerCombinaisonSaisie(String string, int longueur, int nombreUtilisable) throws Exception {			
   	 
		Pattern pattern = Pattern.compile("[0-9]{" + longueur + "}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		int chiffreUtilisable = nombreUtilisable - 1;
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte !");
		}

		char[] tab = string.toCharArray();
		
		for (int i=0; i<tab.length; i++) {
			int chiffre = Character.digit(tab[i], 10);
			
			if (chiffre > chiffreUtilisable) {
				throw new Exception("Les chiffres doivent être compris dans l'intervalle 0 à " + chiffreUtilisable);
			}
		}		
		return resultat;    	
    }

}
