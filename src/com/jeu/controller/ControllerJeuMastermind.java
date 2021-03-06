package com.jeu.controller;

import com.jeu.controller.attaquer.AttaqueHumaineCombinaison;
import com.jeu.controller.attaquer.AttaqueHumaineMastermind;
import com.jeu.controller.attaquer.AttaqueMMastermind;
import com.jeu.controller.defendre.DefenseHumaineMastermind;
import com.jeu.controller.defendre.DefenseMMastermind;
import com.jeu.model.Combinaison;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.model.Mastermind;
import com.jeu.model.TypeJoueur;
import com.jeu.view.VueConsole;

public class ControllerJeuMastermind extends ControllerJeu {
	
	public ControllerJeuMastermind() {
		vue = new VueConsole();
	}
	
	/**
	 * Correspond � la premi�re combinaison
	 */
	private Jeu jeuMastermind1;
	/**
	 * Correspond � la deuxi�me combinaison
	 */	
	private Jeu jeuMastermind2;
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
     * Permet l'affichage des �l�ments en console
     */
    private VueConsole vue;
    
	/**
	 * Configure le jeu en mode challenger (humain attaque, ordinateur d�fend)
	 */	
	@Override
	public void modeChallenger() {
		
		joueurH = new Joueur(TypeJoueur.George, Jeu.nombreEssais);
		joueurM = new Joueur(TypeJoueur.T800, Jeu.nombreEssais);

		modeDuel = false;
		// Le joueur George est en mode attaque
		joueur1 = new ControlleurJoueur(joueurH, new AttaqueHumaineMastermind());
		// Le joueur T800 est en mode d�fense
		joueur2 = new ControlleurJoueur(joueurM, new DefenseMMastermind());
		
		jeuMastermind1 = new Mastermind();
		jeuMastermind1.initialiser(joueur2.donnerCombinaison());
		
		tourDeJeu = true;
		
		vue.afficherJeuMastermindIntro(Jeu.nombreEssais); 
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
		joueur1 = new ControlleurJoueur(joueurM, new AttaqueMMastermind());
		// Le joueur George d�fend
		joueur2 = new ControlleurJoueur(joueurH, new DefenseHumaineMastermind());
		
		jeuMastermind1 = new Mastermind();
		jeuMastermind1.initialiser(joueur2.donnerCombinaison());
		
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
		joueur1 = new ControlleurJoueur(joueurH, new AttaqueHumaineMastermind(), new DefenseHumaineMastermind());
		joueur2 = new ControlleurJoueur(joueurM, new AttaqueMMastermind(), new DefenseMMastermind() );
		// T800 Initialise une combinaison
		jeuMastermind1 = new Combinaison();
		jeuMastermind1.initialiser(joueur2.donnerCombinaison());
		// George Initialise une combinaison
		jeuMastermind2 = new Combinaison();
		jeuMastermind2.initialiser(joueur1.donnerCombinaison());
		
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
		
		// Pour le mode duel
		if(modeDuel) {
			
			// Affiche la solution en mode d�veloppeur
			if(choixModeSession) {
				vue.afficherMessage("Mode D�veloppeur :\n"
									+ "la solution de " + joueur1.getJoueur().getType() + " est : " + jeuMastermind1.getCombinaisonSecrete() + "\n"
									+ "la solution de " + joueur2.getJoueur().getType() + " est : " + jeuMastermind2.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du d�fenseur 
			proposition = joueur1.propose(jeuMastermind1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuMastermind1, proposition);
			test = verifierVictoire(joueur1.getJoueur(), jeuMastermind1);

			if(!test) {
				return test;
			}
			
			// Le joueur2 propose en fonction de l'indication du d�fenseur 
			proposition = joueur2.propose(jeuMastermind2.getCombinaisonReponseMap());
			// Le joueur1 analyse la proposition et retourne une indication
			joueur1.analyse(jeuMastermind2, proposition);
			test = verifierVictoire(joueur2.getJoueur(), jeuMastermind2);
			return test;

			
		}
		// Pour les modes challenger ou d�fenseur
		else {
			
			// Affiche la solution en mode d�veloppeur
			if(choixModeSession) {
				vue.afficherMessage("Mode D�veloppeur, la solution est : " + jeuMastermind1.getCombinaisonSecrete());
			}
			// Le joueur1 propose en fonction de l'indication du d�fenseur 
			proposition = joueur1.propose(jeuMastermind1.getCombinaisonReponseMap());
			// Le joueur2 analyse la proposition et retourne une indication
			joueur2.analyse(jeuMastermind1, proposition);

			test = verifierVictoire(joueur1.getJoueur(), jeuMastermind1);	

			return test;			
		}
	}
	/**
	 * Permet de v�rifier si la partie est gagn�e, perdue, continue
	 */
	@Override
	public boolean verifierVictoire(Joueur joueur, Jeu jeu) {
		
		boolean resultat = false;
		int place = Integer.parseInt((String) jeu.getCombinaisonReponseMap().get("place"));
		// V�rifie si tous les chiffres sont plac�s
		if(place == Jeu.longueurMastermind) {
			resultat = true;
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
    		vue.afficherMessage("Pr�sent : " + jeu.getCombinaisonReponseMap().get("present")
    							+"\nPlac� : " + jeu.getCombinaisonReponseMap().get("place"));
    		vue.afficherMessage("***********************************************************************");
    		
    		return true;
    	}
		
	}
	
}
