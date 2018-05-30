package com.jeu.controller;

import java.util.HashMap;

import com.jeu.model.Jeu;

public class AttaqueMCombinaison extends Attaque {
	
	private boolean premierCoup = true;

	@Override
	public String proposer(HashMap<String, String> reponse) {

		String laReponse = (String) reponse.get("combinaisonReponse");
		char caractereReponse;
		int temporaire;
		
		if(premierCoup) {			
			premierCoup = false;
			
			return premierCoup();
		}
		else {
			
			for(int i=0; i<laReponse.length(); i++) {
				
				caractereReponse = laReponse.charAt(i);
				temporaire = Character.digit(propositionTab[i], 10);				
				
				switch(caractereReponse) {
				
					case '+' : {
						temporaire += 1;
						propositionTab[i] = Character.forDigit(temporaire, 10);						
						break;
					}
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
	
	private String premierCoup() {
		
		for(int i=0; i<Jeu.longueurCombinaison; i++) {
			proposition += 5;
		}
		
		propositionTab = proposition.toCharArray();
		return proposition;
		
	}

}
