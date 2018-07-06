package com.jeu.model;


import java.util.*;

/**
 * Cette classe représente une combinaison pour le jeu mastermind
 */
public class Mastermind extends Jeu {

    /**
     * Default constructor
     */
    public Mastermind() {
    }

	@Override
	public void initialiser(String combinaison) {
		
		combinaisonSecrete = combinaison;
		combinaisonSecreteTab = this.combinaisonSecrete.toCharArray();		
		combinaisonReponseMap = new HashMap<String, String>(); 
		
	}

}