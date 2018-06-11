package com.jeu.controller;

import com.jeu.model.Combinaison;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.model.TypeJoueur;

public class ControleurJeuCombinaison extends ControleurJeu {

	public ControleurJeuCombinaison() {

	}
	
	private Jeu jeuCombinaison1;
	private Jeu jeuCombinaison2;
	private boolean modeDuel;
	private Joueur joueurH;
	private Joueur joueurM;

	@Override
	public void modeChallenger() {

		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais); 
		
		modeDuel = false;
		// Le joueur George est en mode attaque
		joueur1 = new ControleurJoueur(joueurH, new AttaqueHumaine());
		// Le joueur T800 est en mode défense
		joueur2 = new ControleurJoueur(joueurM, new DefenseMCombinaison());
		
		jeuCombinaison1 = new Combinaison();
		jeuCombinaison1.initialiser(joueur2.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);
		
	}

	@Override
	public void modeDefenseur() {
		
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);

		modeDuel = false;
		// Le joueur T800 attaque
		joueur1 = new ControleurJoueur(joueurM, new AttaqueMCombinaison());
		// Le joueur George défend
		joueur2 = new ControleurJoueur(joueurH, new DefenseHumaineCombinaison());
		
		jeuCombinaison1 = new Combinaison();
		jeuCombinaison1.initialiser(joueur2.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);		
	}

	@Override
	public void duel() {
		
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);
		
		modeDuel = true;
		// Joueur1 (Goerge) affronte Joueur2 (T800)
		joueur1 = new ControleurJoueur(joueurH, new AttaqueHumaine(), new DefenseHumaineCombinaison());
		joueur2 = new ControleurJoueur(joueurM, new AttaqueMCombinaison(), new DefenseMCombinaison() );
		// T800 Initialise une combinaison
		jeuCombinaison1 = new Combinaison();
		jeuCombinaison1.initialiser(joueur2.donnerCombinaison());
		// George Initialise une combinaison
		jeuCombinaison2 = new Combinaison();
		jeuCombinaison2.initialiser(joueur1.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);	
		
	}

	@Override
	public boolean jouer() {

		boolean test;
		

		
		if(modeDuel) {
			
			// Affiche la solution en mode développeur
			if(ChoixModeSession) {
				vue.afficherMessage("Mode Développeur :\n"
									+ "la solution de " + joueur1.getJoueur().getType() + " est : " + jeuCombinaison1.getCombinaisonSecrete() + "\n"
									+ "la solution de " + joueur2.getJoueur().getType() + " est : " + jeuCombinaison2.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du défenseur 
			proposition = joueur1.propose(jeuCombinaison1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuCombinaison1, proposition);
			test = verifierVictoire(joueur1.getJoueur(), jeuCombinaison1);

			if(!test) {
				return test;
			}
			
			// Le joueur2 propose en fonction de l'indication du défenseur 
			proposition = joueur2.propose(jeuCombinaison2.getCombinaisonReponseMap());
			// Le joueur1 analyse la proposition et retourne une indication
			joueur1.analyse(jeuCombinaison2, proposition);
			test = verifierVictoire(joueur2.getJoueur(), jeuCombinaison2);
			return test;

			
		}
		else {
			
			// Affiche la solution en mode développeur
			if(ChoixModeSession) {
				vue.afficherMessage("Mode Développeur, la solution est : " + jeuCombinaison1.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du défenseur 
			proposition = joueur1.propose(jeuCombinaison1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuCombinaison1, proposition);

			test = verifierVictoire(joueur1.getJoueur(), jeuCombinaison1);	

			return test;
			
		}
	}

	@Override
	public boolean verifierVictoire(Joueur joueur, Jeu jeu) {
		
		boolean resultat = true;
		
		for(int i=0; i<jeu.getCombinaisonTest().length; i++) {
			resultat = resultat && jeu.getCombinaisonTest()[i];
		}
		
    	if(resultat) {
    		vue.afficherMessage(joueur.getType() + " a gagné !!");
    		vue.afficherMessage("La combinaison est : " + jeu.getCombinaisonSecrete());
    		return false;
    	}
    	else if (joueur.getNombreEssais() == 1) {
    		vue.afficherMessage(joueur.getType() + " a Perdu");
    		vue.afficherMessage("La combinaison est : " + jeu.getCombinaisonSecrete());
    		return false;
    	}
    	
    	else {
    		joueur.setNombreEssais(joueur.getNombreEssais() - 1);
    		vue.afficherMessage("***********************************************************************");
    		vue.afficherMessage("                           " + joueur.getType());
    		vue.afficherMessage("il vous reste : " + joueur.getNombreEssais() + " essai(s)");
    		vue.afficherMessage("Votre proposition : " + proposition);
    		vue.afficherMessage("Indice : " + new String(jeu.getCombinaisonReponseTab()));
    		vue.afficherMessage("***********************************************************************");
    		
    		return true;
    	}
		
	}

}
