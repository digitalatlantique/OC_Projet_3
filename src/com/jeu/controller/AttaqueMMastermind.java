package com.jeu.controller;

import java.util.HashMap;

import com.jeu.model.Jeu;

public class AttaqueMMastermind extends Attaque {
	
	private boolean premierCoup = true;
	private int trouve = 0;

	@Override
	public String proposer(HashMap<String, String> reponse) {

		// Pour le premier tour de jeu
		if(premierCoup) {
			
			premierCoup = false;			
			return premierCoup();
		}		
		// Pour les tours suivant
		else {
			
			String place = reponse.get("place");
			String present = reponse.get("present");
			int compteurPlace = Integer.parseInt(place);
			int compteurPresent = Integer.parseInt(present);
			trouve = compteurPlace + compteurPresent;
			
			/* Si il reste des chiffres � trouver
			 * On propose une s�rie reprenant la combinaison pr�c�dente +1 � chaque chiffre restant � trouv�
			 */
			if(trouve < Jeu.longueurCombinaison) {
				
				proposerChiffre();
			}
			// Sinon on d�place les chiffres
			else {
				
				switch(place) {
					// D�place tout les chiffres d'un indice
					case "0" : {
						deplacerChiffre(0, 1);	
						break;
					}
					// D�place tout les chiffres d'un indice sauf le premier
					case "1" : {
						deplacerChiffre(1, 1);
						break;
					}
					// D�place tout les chiffres d'un indice sauf les deux premiers
					case "2" : {
						deplacerChiffre(1, 1);
						break;
					}
					// D�place tout les chiffres d'un indice sauf les trois premiers
					case "3" : {
						deplacerChiffre(2, 1);
						break;
					}					
				}				
			}			
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
	
	private void proposerChiffre() {
		int temporaire;
		for (int i = trouve; i < propositionTab.length; i++) {

			temporaire = Character.digit(propositionTab[i], 10);
			temporaire += 1;
			propositionTab[i] = Character.forDigit(temporaire, 10);
		}
		
		proposition = new String(propositionTab);
	}
	
	private void deplacerChiffre(int indiceDepart, int indiceChoisi) {
		
		for(int i = indiceDepart; i < propositionTab.length - indiceChoisi; i++) {
			char temp = propositionTab[i];
			propositionTab[i] = propositionTab[i + indiceChoisi];
			propositionTab[i + indiceChoisi] = temp;

		}		
		proposition = new String(propositionTab);
	}

}
