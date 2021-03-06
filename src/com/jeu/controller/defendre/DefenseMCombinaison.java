package com.jeu.controller.defendre;

import com.jeu.model.Jeu;
/**
 * Cette classe permet � l'ordinateur (machine) de donner des indications pour le jeu combinaison +-
 * @author Workstation
 *
 */
public class DefenseMCombinaison extends Defense {
	
	private int longueur = Jeu.longueurCombinaison;

	@Override
	public void analyser(Jeu jeu, String proposition) {
		
		int resultat;
		String cle = new String("combinaisonReponse");
		
		for(int i=0; i<proposition.length(); i++) {
			
			resultat = Character.compare(proposition.charAt(i), jeu.getCombinaisonSecreteTab()[i]);
			
			if(resultat < 0) {
				jeu.getCombinaisonReponseTab()[i] = '+';
				jeu.getCombinaisonTest()[i] = false;
			}
			
			else if (resultat > 0){
				jeu.getCombinaisonReponseTab()[i] = '-';
				jeu.getCombinaisonTest()[i] = false;
			}
			else {
				jeu.getCombinaisonReponseTab()[i] = '=';
				jeu.getCombinaisonTest()[i] = true;
			}
		}
		// Valeur pour la r�ponse de la map
		reponse = new String(jeu.getCombinaisonReponseTab());
		jeu.getCombinaisonReponseMap().put(cle, reponse);

	}

	@Override
	public String genererCombinaison() {
		String combinaison = "";
		int temp;
		
		for(int i=0; i<longueur; i++) {
			
			temp = genererNombre(0, 9);
			combinaison += String.valueOf(temp);
		}

		return combinaison;
	}

}
