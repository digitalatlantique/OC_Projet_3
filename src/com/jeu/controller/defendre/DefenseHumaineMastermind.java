package com.jeu.controller.defendre;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Jeu;
/**
 * Cette classe permet de donner des indications � l'attaquant pour le jeu mastermind
 * @author Workstation
 *
 */
public class DefenseHumaineMastermind extends Defense {
	
	private int longueur = Jeu.longueurMastermind;

	@Override
	public void analyser(Jeu jeu, String proposition) {
		
		boolean test;
		int compteurPlace = 0;
		int compteurPresent = 0;
		String clePlace = "place";
		String clePresent = "present";

		vue.afficherMessage("T800 propose :                      " + proposition);		
		vue.afficherMessage("Indiquer le nombre de chiffre bien plac� : ");
		
		do {
			saisie = sc.next();
			
			try {
				test = testerReponseSaisie(saisie, Jeu.longueurMastermind);
			} catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMessage("Indiquer le nombre de chiffre bien plac� : ");
			}			
		}
		while(!test);
		
		compteurPlace = Integer.parseInt(saisie);
		
		if(compteurPlace < Jeu.longueurMastermind) {
			vue.afficherMessage("Pour les chiffres restants indiquer le nombre de chiffre pr�sent : ");
			do {
				saisie = sc.next();
				
				try {
					test = testerReponseSaisie(saisie, Jeu.longueurMastermind - compteurPlace);
				} catch (Exception e) {
	        		test = false;
	        		System.out.println(e.getMessage());
	        		vue.afficherMessage("Pour les chiffres restants indiquer le nombre de chiffre pr�sent : ");
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
		int chiffre = nombreDeChiffre - 1;
		String message = "Faire deviner une combinaison valide, compos�e de " + longueur + " chiffres\n"
							+"Les chiffres de la combinaison doivent �tre compris dans l'intervalle 0 et " + chiffre;
		
		vue.afficherMessage(message);
		
		do {			
			saisie = sc.next();
			
			try {
				test = testerCombinaisonSaisie(saisie, longueur, nombreDeChiffre);
			} 
			catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMessage(message);
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
			throw new Exception("Merci de saisir un nombre inf�rieur ou �gal � : " + longueur);			
		}
		return resultat;    	
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
				throw new Exception("Les chiffres doivent �tre compris dans l'intervalle 0 � " + chiffreUtilisable);
			}
		}		
		return resultat;    	
    }


}
