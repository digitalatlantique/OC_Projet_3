package com.jeu.controller.attaquer;

import java.util.HashMap;

import com.jeu.model.Jeu;
/**
 * Cette classe permet à l'ordinateur (machine) de faire une proposition avec le jeu combinaison+-
 * En fonction des indications du défenseur
 * @author Workstation
 *
 */
public class AttaqueMCombinaison extends Attaque {
	
	private boolean premierCoup = true;
	/**
	 * Permet de faire une proposition en fonction des indications du défenseur
	 */
	@Override
	public String proposer(HashMap<String, String> reponse) {

		String laReponse = (String) reponse.get("combinaisonReponse");
		char caractereReponse;
		int temporaire;
		// Si c'est le premier coup, l'ordinateur fait une proposition d'une suite de zéro d'une longueur correspondante à la longueur de la combinaison
		if(premierCoup) {			
			premierCoup = false;
			
			return premierCoup();
		}
		// Ensuite une comparaison est effectuée en fonction des indications
		else {
			
			for(int i=0; i<laReponse.length(); i++) {
				
				caractereReponse = laReponse.charAt(i);
				temporaire = Character.digit(propositionTab[i], 10);				
				
				switch(caractereReponse) {
					// Si c'est plus, on ajoute un
					case '+' : {
						temporaire += 1;
						propositionTab[i] = Character.forDigit(temporaire, 10);						
						break;
					}
					// Si c'est moins, on retire un
					case '-' : {
						temporaire -= 1;
						propositionTab[i] = Character.forDigit(temporaire, 10);						
						break;
					}
				}
			}
			
			proposition = new String(propositionTab);
			
			return proposition;	
		}	
	}
	/**
	 * Génère la première proposition
	 * @return
	 */
	private String premierCoup() {
		
		for(int i=0; i<Jeu.longueurCombinaison; i++) {
			proposition += 5;
		}
		
		propositionTab = proposition.toCharArray();
		return proposition;
		
	}

}
