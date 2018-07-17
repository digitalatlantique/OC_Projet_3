package com.jeu.controller.attaquer;

import java.util.HashMap;

import com.jeu.model.Jeu;

public class AttaqueHumaineMastermind extends Attaque{
	
	@Override
	public String proposer(HashMap<String, String> reponse) {
		
		boolean test;
		
    	vue.afficherMessage("Trouver la combinaison à : " + Jeu.longueurMastermind + " chiffres.");
    	
    	do {
    		
    		saisie = sc.next();
    		try {
    			test = testerCombinaisonSaisie(saisie, Jeu.longueurMastermind);
    		}
    		catch(Exception e) {
    			test = false;
    			System.out.println(e.getMessage());
    			vue.afficherMessage("Trouver la combinaison à : " + Jeu.longueurMastermind + " chiffres.");
    		}    		
    	}
    	while(!test);
    	
    	return saisie;
	}

}
