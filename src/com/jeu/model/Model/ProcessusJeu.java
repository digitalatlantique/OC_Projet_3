package com.jeu.model.Model;

public interface ProcessusJeu {
	
	void initialiser();
	int genererCombinaison();
	boolean jouer();
	boolean verifierResultat();
	boolean verifierVictoire();	

}
