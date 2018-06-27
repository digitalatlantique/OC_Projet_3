package com.jeu.controller;

import com.jeu.model.Combinaison;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.model.Mastermind;
import com.jeu.model.TypeJoueur;

public class ControlleurJeuMastermind extends ControlleurJeu {
	
	private Jeu jeuMastermind1;
	private Jeu jeuMastermind2;
	private boolean modeDuel;
	private Joueur joueurH;
	private Joueur joueurM;

	@Override
	public void modeChallenger() {
		
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);

		modeDuel = false;
		// Le joueur George est en mode attaque
		joueur1 = new ControlleurJoueur(joueurH, new AttaqueHumaine());
		// Le joueur T800 est en mode défense
		joueur2 = new ControlleurJoueur(joueurM, new DefenseMMastermind());
		
		jeuMastermind1 = new Mastermind();
		jeuMastermind1.initialiser(joueur2.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuMastermindIntro(Jeu.nombreEssais); 
		
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
		joueur1 = new ControlleurJoueur(joueurM, new AttaqueMMastermind());
		// Le joueur George défend
		joueur2 = new ControlleurJoueur(joueurH, new DefenseHumaineMastermind());
		
		jeuMastermind1 = new Mastermind();
		jeuMastermind1.initialiser(joueur2.donnerCombinaison());
		
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
		joueur1 = new ControlleurJoueur(joueurH, new AttaqueHumaine(), new DefenseHumaineMastermind());
		joueur2 = new ControlleurJoueur(joueurM, new AttaqueMMastermind(), new DefenseMMastermind() );
		// T800 Initialise une combinaison
		jeuMastermind1 = new Combinaison();
		jeuMastermind1.initialiser(joueur2.donnerCombinaison());
		// George Initialise une combinaison
		jeuMastermind2 = new Combinaison();
		jeuMastermind2.initialiser(joueur1.donnerCombinaison());
		
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
		
		// Pour le mode duel
		if(modeDuel) {
			
			// Affiche la solution en mode développeur
			if(ChoixModeSession) {
				vue.afficherMessage("Mode Développeur :\n"
									+ "la solution de " + joueur1.getJoueur().getType() + " est : " + jeuMastermind1.getCombinaisonSecrete() + "\n"
									+ "la solution de " + joueur2.getJoueur().getType() + " est : " + jeuMastermind2.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du défenseur 
			proposition = joueur1.propose(jeuMastermind1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuMastermind1, proposition);
			test = verifierVictoire(joueur1.getJoueur(), jeuMastermind1);

			if(!test) {
				return test;
			}
			
			// Le joueur2 propose en fonction de l'indication du défenseur 
			proposition = joueur2.propose(jeuMastermind2.getCombinaisonReponseMap());
			// Le joueur1 analyse la proposition et retourne une indication
			joueur1.analyse(jeuMastermind2, proposition);
			test = verifierVictoire(joueur2.getJoueur(), jeuMastermind2);
			return test;

			
		}
		// Pour les modes challenger ou défenseur
		else {
			
			// Affiche la solution en mode développeur
			if(ChoixModeSession) {
				vue.afficherMessage("Mode Développeur, la solution est : " + jeuMastermind1.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du défenseur 
			proposition = joueur1.propose(jeuMastermind1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuMastermind1, proposition);

			test = verifierVictoire(joueur1.getJoueur(), jeuMastermind1);	

			return test;
			
		}
	}


	@Override
	public boolean verifierVictoire(Joueur joueur, Jeu jeu) {
		
		boolean resultat = false;
		int place = Integer.parseInt((String) jeu.getCombinaisonReponseMap().get("place"));
		
		if(place == Jeu.longueurCombinaison) {
			resultat = true;
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
    		vue.afficherMessage("Présent : " + jeu.getCombinaisonReponseMap().get("present")
    							+"\nPlacé : " + jeu.getCombinaisonReponseMap().get("place"));
    		vue.afficherMessage("***********************************************************************");
    		
    		return true;
    	}
		
	}
	
}
