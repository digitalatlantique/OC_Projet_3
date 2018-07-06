package com.jeu.controller;

import com.jeu.controller.attaquer.AttaqueHumaine;
import com.jeu.controller.attaquer.AttaqueMCombinaison;
import com.jeu.controller.defendre.DefenseHumaineCombinaison;
import com.jeu.controller.defendre.DefenseMCombinaison;
import com.jeu.model.Combinaison;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.model.TypeJoueur;

public class ControlleurJeuCombinaison extends ControlleurJeu {

	public ControlleurJeuCombinaison() {

	}
	/**
	 * Correspond � la premi�re combinaison
	 */
	private Jeu jeuCombinaison1;
	/**
	 * Correspond � la deuxi�me combinaison
	 */
	private Jeu jeuCombinaison2;
	/**
	 * Permet de v�rifier si le mode duel est actif
	 */
	private boolean modeDuel;
	/**
	 * Repr�sente le joueur humain
	 */
	private Joueur joueurH;
	/**
	 * Repr�sente l'ordinateur (machine)
	 */
	private Joueur joueurM;
	/**
	 * Configure le jeu en mode challenger (humain attaque, ordinateur d�fend)
	 */
	@Override
	public void modeChallenger() {
		// Initialisation des joueurs
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais); 
		
		modeDuel = false;
		// Le joueur George est en mode attaque
		joueur1 = new ControlleurJoueur(joueurH, new AttaqueHumaine());
		// Le joueur T800 est en mode d�fense
		joueur2 = new ControlleurJoueur(joueurM, new DefenseMCombinaison());
		// Initialisation la combinaison +-
		jeuCombinaison1 = new Combinaison();
		jeuCombinaison1.initialiser(joueur2.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		// Correspond � un tour de jeu
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);
		
	}
	/**
	 * Configure le jeu en mode d�fenseur (ordinateur attaque, humain d�fend)
	 */
	@Override
	public void modeDefenseur() {
		
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);

		modeDuel = false;
		// Le joueur T800 attaque
		joueur1 = new ControlleurJoueur(joueurM, new AttaqueMCombinaison());
		// Le joueur George d�fend
		joueur2 = new ControlleurJoueur(joueurH, new DefenseHumaineCombinaison());
		
		jeuCombinaison1 = new Combinaison();
		jeuCombinaison1.initialiser(joueur2.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		// Correspond � un tour de jeu
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);		
	}
	/**
	 * Configure le jeu en mode duel (Humain VS ordinateur)
	 */
	@Override
	public void duel() {
		
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);
		
		modeDuel = true;
		// Joueur1 (Goerge) affronte Joueur2 (T800)
		joueur1 = new ControlleurJoueur(joueurH, new AttaqueHumaine(), new DefenseHumaineCombinaison());
		joueur2 = new ControlleurJoueur(joueurM, new AttaqueMCombinaison(), new DefenseMCombinaison() );
		// T800 Initialise une combinaison
		jeuCombinaison1 = new Combinaison();
		jeuCombinaison1.initialiser(joueur2.donnerCombinaison());
		// George Initialise une combinaison
		jeuCombinaison2 = new Combinaison();
		jeuCombinaison2.initialiser(joueur1.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		// Correspond � un tour de jeu
    	do {
    		tourDeJeu = jouer();
    	}
    	while(tourDeJeu);	
		
	}
	/**
	 * Configuration d'un tour de jeu
	 * En mode duel : Le joueur 1 attaque et le joueur 2 d�fend et inversement
	 * Dans les autres mode : Le joueur 1 attaque et le joueur 2 d�fend
	 */
	@Override
	public boolean jouer() {

		boolean test;		

		// En mode duel
		if(modeDuel) {
			
			// Affiche la solution en mode d�veloppeur
			if(ChoixModeSession) {
				vue.afficherMessage("Mode D�veloppeur :\n"
									+ "la solution de " + joueur1.getJoueur().getType() + " est : " + jeuCombinaison1.getCombinaisonSecrete() + "\n"
									+ "la solution de " + joueur2.getJoueur().getType() + " est : " + jeuCombinaison2.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du d�fenseur 
			proposition = joueur1.propose(jeuCombinaison1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuCombinaison1, proposition);
			// Test la victoire ou non
			test = verifierVictoire(joueur1.getJoueur(), jeuCombinaison1);

			if(!test) {
				return test;
			}
			
			// Le joueur2 propose en fonction de l'indication du d�fenseur 
			proposition = joueur2.propose(jeuCombinaison2.getCombinaisonReponseMap());
			// Le joueur1 analyse la proposition et retourne une indication
			joueur1.analyse(jeuCombinaison2, proposition);
			test = verifierVictoire(joueur2.getJoueur(), jeuCombinaison2);
			return test;

			
		}
		// Pour les modes challenger ou d�fenseur
		else {
			
			// Affiche la solution en mode d�veloppeur
			if(ChoixModeSession) {
				vue.afficherMessage("Mode D�veloppeur, la solution est : " + jeuCombinaison1.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du d�fenseur 
			proposition = joueur1.propose(jeuCombinaison1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuCombinaison1, proposition);
			// Test la victoire ou non
			test = verifierVictoire(joueur1.getJoueur(), jeuCombinaison1);	

			return test;
			
		}
	}
	/**
	 * Permet de v�rifier si la partie est gagn�e, perdue, continue
	 */
	@Override
	public boolean verifierVictoire(Joueur joueur, Jeu jeu) {
		
		boolean resultat = true;
		// V�rifie si tous les chiffres sont exact
		for(int i=0; i<jeu.getCombinaisonTest().length; i++) {
			resultat = resultat && jeu.getCombinaisonTest()[i];
		}
		// Si oui c'est gagn�
    	if(resultat) {
    		vue.afficherMessage(joueur.getType() + " a gagn� !!");
    		vue.afficherMessage("La combinaison est : " + jeu.getCombinaisonSecrete());
    		return false;
    	}
    	// Si le joueur a jouer son dernier coup, alors c'est perdu
    	else if (joueur.getNombreEssais() == 1) {
    		vue.afficherMessage(joueur.getType() + " a Perdu");
    		vue.afficherMessage("La combinaison est : " + jeu.getCombinaisonSecrete());
    		return false;
    	}
    	// Sinon la partie continue
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
