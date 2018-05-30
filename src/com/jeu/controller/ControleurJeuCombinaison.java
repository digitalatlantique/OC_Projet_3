package com.jeu.controller;

import com.jeu.model.Combinaison;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.model.TypeJoueur;

public class ControleurJeuCombinaison extends ControleurJeu {

	public ControleurJeuCombinaison() {

		this.jeuCombinaison = new Combinaison();
	}
	
	private Jeu jeuCombinaison;

	@Override
	public void modeChallenger() {
		
		Joueur joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		Joueur joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);
		
		joueurAttaque = new ControleurJoueur(joueurH, new AttaqueHumaine());
		joueurDefend = new ControleurJoueur(joueurM, new DefenseMCombinaison());
		jeuCombinaison.initialiser(joueurDefend.donnerCombinaison());
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);
		
	}

	@Override
	public void modeDefenseur() {

		Joueur joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		Joueur joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);
		
		joueurAttaque = new ControleurJoueur(joueurM, new AttaqueMCombinaison());
		joueurDefend = new ControleurJoueur(joueurH, new DefenseHumaine());
		jeuCombinaison.initialiser(joueurDefend.donnerCombinaison());
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);
		
	}

	@Override
	public void duel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean jouer() {

		boolean test;
		
		if(ChoixModeSession) {
			vue.afficherMessage("Mode Développeur, la solution est : " + jeuCombinaison.getCombinaisonSecrete());
		}
		
		proposition = joueurAttaque.propose(jeuCombinaison.getCombinaisonReponseMap());
		joueurDefend.analyse(jeuCombinaison, proposition);

		test = verifierVictoire(joueurAttaque.getJoueur());	

		return test;

	}

	@Override
	public boolean verifierVictoire(Joueur joueur) {
		
		boolean resultat = true;
		
		for(int i=0; i<jeuCombinaison.getCombinaisonTest().length; i++) {
			resultat = resultat && jeuCombinaison.getCombinaisonTest()[i];
		}
		
    	if(resultat) {
    		vue.afficherMessage(joueur.getType() + " a gagné !!");
    		vue.afficherMessage("La combinaison est : " + jeuCombinaison.getCombinaisonSecrete());
    		return false;
    	}
    	else if (joueur.getNombreEssais() == 1) {
    		vue.afficherMessage(joueur.getType() + " a Perdu");
    		vue.afficherMessage("La combinaison est : " + jeuCombinaison.getCombinaisonSecrete());
    		return false;
    	}
    	
    	else {
    		joueur.setNombreEssais(joueur.getNombreEssais() - 1);
    		
    		vue.afficherMessage("il vous reste : " + joueur.getNombreEssais() + " essai(s)");
    		vue.afficherMessage("Indice : " + new String(jeuCombinaison.getCombinaisonReponseTab()));
    		
    		return true;
    	}
		
	}

}
