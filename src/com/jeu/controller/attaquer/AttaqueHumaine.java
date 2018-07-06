package com.jeu.controller.attaquer;

import java.util.HashMap;

import com.jeu.model.Jeu;
/**
 * Cette classe permet au joueur humain de proposer une combinaison
 * @author Workstation
 *
 */
public class AttaqueHumaine extends Attaque  {

	@Override
	public String proposer(HashMap<String, String> reponse) {
		
		boolean test;
		
    	vue.afficherMessage("Trouvé la combinaison à : " + Jeu.longueurCombinaison + " chiffres.");
    	
    	do {
    		
    		saisie = sc.next();
    		try {
    			test = testerCombinaisonSaisie(saisie, Jeu.longueurCombinaison);
    		}
    		catch(Exception e) {
    			test = false;
    			System.out.println(e.getMessage());
    			vue.afficherMessage("Trouvé la combinaison à : " + Jeu.longueurCombinaison + " chiffres.");
    		}    		
    	}
    	while(!test);
    	
    	return saisie;
	}

}
