package com.jeu.controller;

import java.util.HashMap;

import com.jeu.model.Jeu;

public class AttaqueMMastermind extends Attaque {
	
	private boolean premierCoup = true;
	private int trouve = 0;

	@Override
	public String proposer(HashMap<String, String> reponse) {

		if(premierCoup) {
			
			premierCoup = false;			
			return premierCoup();
		}		

		else {
			
			String place = reponse.get("place");
			String present = reponse.get("present");
			int compteurPlace = Integer.parseInt(place);
			int compteurPresent = Integer.parseInt(present);
			trouve = compteurPlace + compteurPresent;
			int temporaire;
			
			for (int i = trouve; i < propositionTab.length; i++) {

				temporaire = Character.digit(propositionTab[i], 10);
				temporaire += 1;
				propositionTab[i] = Character.forDigit(temporaire, 10);
			}
			
			proposition = new String(propositionTab);
			
			return proposition;	
			
		}
	}
	
	private String premierCoup() {
		
		for(int i=0; i<Jeu.longueurCombinaison; i++) {
			proposition += 0;
		}
		
		propositionTab = proposition.toCharArray();
		return proposition;
		
	}

}
