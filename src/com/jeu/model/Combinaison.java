package com.jeu.model;

import java.util.HashMap;

/**
 * Cette classe représente une combinaison pour le jeu combinaison+-
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

}