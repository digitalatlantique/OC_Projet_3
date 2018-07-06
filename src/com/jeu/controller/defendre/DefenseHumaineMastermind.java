package com.jeu.controller.defendre;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Jeu;
/**
 * Cette classe permet de donner des indications à l'attaquant pour le jeu mastermind
 * @author Workstation
 *
 */
public class DefenseHumaineMastermind extends Defense {

	@Override
	public void analyser(Jeu jeu, String proposition) {
		
		boolean test;
		int compteurPlace = 0;
		int compteurPresent = 0;
		String clePlace = "place";
		String clePresent = "present";

		vue.afficherMessage("T800 propose :                      " + proposition);		
		vue.afficherMessage("Indiquer le nombre de chiffre bien placé : ");
		
		do {
			saisie = sc.next();
			
			try {
				test = testerReponseSaisie(saisie, Jeu.longueurCombinaison);
			} catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMessage("Indiquer le nombre de chiffre bien placé : ");
			}			
		}
		while(!test);
		
		compteurPlace = Integer.parseInt(saisie);
		
		if(compteurPlace < Jeu.longueurCombinaison) {
			vue.afficherMessage("Pour les chiffres restants indiquer le nombre de chiffre présent : ");
			do {
				saisie = sc.next();
				
				try {
					test = testerReponseSaisie(saisie, Jeu.longueurCombinaison - compteurPlace);
				} catch (Exception e) {
	        		test = false;
	        		System.out.println(e.getMessage());
	        		vue.afficherMessage("Pour les chiffres restants indiquer le nombre de chiffre présent : ");
				}			
			}
			while(!test);
			
			compteurPresent = Integer.parseInt(saisie);
		}


		jeu.getCombinaisonReponseMap().put(clePlace, String.valueOf(compteurPlace));
		jeu.getCombinaisonReponseMap().put(clePresent, String.valueOf(compteurPresent));
		


	}

	@Override
	public String genererCombinaison() {
		
		boolean test;
		
		vue.afficherMessage("Faire deviner une combinaison composée de " + longueur + " chiffres");
		
		do {			
			saisie = sc.next();
			
			try {
				test = testerCombinaisonSaisie(saisie, longueur);
			} 
			catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMessage("Faire deviner une combinaison valide, composée de " + longueur + " chiffres");
			}
			
		}
		while(!test);
		
		return saisie;
	}
	
    public boolean testerReponseSaisie(String string, int longueur) throws Exception {			
   	 
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte !");
		}
		
		if(Integer.parseInt(string) > longueur) {			
			throw new Exception("Merci de saisir un nombre inférieur ou égal à : " + longueur);			
		}
		return resultat;    	
    }


}
