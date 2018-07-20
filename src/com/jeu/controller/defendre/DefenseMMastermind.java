package com.jeu.controller.defendre;

import java.util.ArrayList;
import java.util.Iterator;

import com.jeu.model.Jeu;
/**
 * Cette classe permet à l'ordinateur (machine) de donner des indications pour le jeu mastermind
 * @author Workstation
 *
 */
public class DefenseMMastermind extends Defense {
	
	private int longueur = Jeu.longueurMastermind;

	@Override
	public void analyser(Jeu jeu, String proposition) {

		int compteurPlace = 0;
		int compteurPresent = 0;
		String clePlace = "place";
		String clePresent = "present";
		
		char[] tab = jeu.getCombinaisonSecreteTab();
		
		ArrayList<Character> listSecrete = new ArrayList<Character>();
		ArrayList<Character> listProposition = new ArrayList<Character>();	

		// Boucle sur les chiffres de la combinaison secrète
		for (int i=0; i< tab.length; i++) {		
			
			// Vérifie les chiffres bien placés
			if(proposition.charAt(i) == tab[i]) {
	
				compteurPlace++;
			}
			else {
				// Création des listes de combinaison secrète et proposée non placé
				listSecrete.add(tab[i]);
				listProposition.add(proposition.charAt(i));
			}

		}
		
		Iterator<Character> itProposition = listProposition.iterator();
		// Boucle sur la liste de chiffre proposé par l'adversaire
		while(itProposition.hasNext()) {
			char tempProposition = itProposition.next();
			
			Iterator<Character> itSecrete = listSecrete.iterator();
			// Boucle sur la liste des chiffres de la combinaison
			while(itSecrete.hasNext()) {
				char tempSecrete = itSecrete.next();
				
				/**
				 *  Si le caractère proposé est présent dans la combinaison
				 *  On ajoute 1 au compteur présent
				 *  On retire l'élément courant de la combinaison secrète
				 */				
				if(tempSecrete == tempProposition) {
					compteurPresent++;
					itSecrete.remove();
					break;
				}
			}						
		}		
		jeu.getCombinaisonReponseMap().put(clePlace, String.valueOf(compteurPlace));
		jeu.getCombinaisonReponseMap().put(clePresent, String.valueOf(compteurPresent));


	}

	@Override
	public String genererCombinaison() {
		String combinaison = "";
		int temp;
		
		for(int i=0; i<longueur; i++) {
			
			temp = genererNombre(0, nombreDeChiffre - 1);
			combinaison += String.valueOf(temp);
		}

		return combinaison;
	}

}
