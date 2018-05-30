package com.jeu.model;

import java.util.HashMap;

/**
 * 
 */
public class Combinaison extends Jeu {
	
	 /**
     * Default constructor
     */
    public Combinaison() {    	

    }

	@Override
	public void initialiser(String combinaison) {
		
		combinaisonSecrete = combinaison;
		combinaisonSecreteTab = this.combinaisonSecrete.toCharArray();
		combinaisonReponseTab = new char[longueurCombinaison];
		combinaisonReponseMap = new HashMap<String, String>(); 
		combinaisonTest = new boolean[longueurCombinaison];
		
	}

	@Override
	public String genererCombinaison(int longueur) {

		String combinaison = "";
		int temp;
		
		for(int i=0; i<longueur; i++) {
			
			temp = genererNombre(0, 9);
			combinaison += String.valueOf(temp);
		}

		return combinaison;
	}
	
    /**
     * Permet de générer un nombre compris dans un interval
     * @param min, le nombre minimum
     * @param max, le nombre maximum
     * @return un nombre aléatoire
     */
    public int genererNombre(int min, int max) {
    	int aleatoire = (int) (Math.random() * (max - min))+min;
    	return aleatoire;
    }
}