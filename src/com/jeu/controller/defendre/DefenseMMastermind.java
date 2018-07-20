package com.jeu.controller.defendre;

import java.util.ArrayList;
import java.util.Iterator;

import com.jeu.model.Jeu;
/**
 * Cette classe permet � l'ordinateur (machine) de donner des indications pour le jeu mastermind
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

		// Boucle sur les chiffres de la combinaison secr�te
		for (int i=0; i< tab.length; i++) {		
			
			// V�rifie les chiffres bien plac�s
			if(proposition.charAt(i) == tab[i]) {
	
				compteurPlace++;
			}
			else {
				// Cr�ation des listes de combinaison secr�te et propos�e non plac�
				listSecrete.add(tab[i]);
				listProposition.add(proposition.charAt(i));
			}

		}
		
		Iterator<Character> itProposition = listProposition.iterator();
		// Boucle sur la liste de chiffre propos� par l'adversaire
		while(itProposition.hasNext()) {
			char tempProposition = itProposition.next();
			
			Iterator<Character> itSecrete = listSecrete.iterator();
			// Boucle sur la liste des chiffres de la combinaison
			while(itSecrete.hasNext()) {
				char tempSecrete = itSecrete.next();
				
				/**
				 *  Si le caract�re propos� est pr�sent dans la combinaison
				 *  On ajoute 1 au compteur pr�sent
				 *  On retire l'�l�ment courant de la combinaison secr�te
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
