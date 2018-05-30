package com.jeu.controller;

import java.util.HashMap;

import com.jeu.model.Jeu;

public class AttaqueHumaine extends Attaque  {

	@Override
	public String proposer(HashMap<String, String> reponse) {
		
		boolean test;
		
    	vue.afficherMessage("Choisir une combinaison à : " + Jeu.longueurCombinaison + " chiffres.");
    	
    	do {
    		
    		saisie = sc.next();
    		try {
    			test = testerCombinaisonSaisie(saisie, Jeu.longueurCombinaison);
    		}
    		catch(Exception e) {
    			test = false;
    			System.out.println(e.getMessage());
    			vue.afficherMessage("Choisir une combinaison à : " + Jeu.longueurCombinaison + " chiffres.");
    		}    		
    	}
    	while(!test);
    	
    	return saisie;
	}

}
