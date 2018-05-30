package com.jeu.controller;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.controller.ControleurJeuFactory;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.view.VueConsole;

/**
 * 
 */
public abstract class ControleurJeu implements ModeJeu {

    /**
     * Default constructor
     */
    public ControleurJeu() {

    }

    /**
     * 
     */
    protected ControleurJoueur joueur1;
    
    /**
     * 
     */
    protected ControleurJoueur joueur2;

    /**
     * 
     */
    private static ControleurJeu controleurJeu;

    /**
     * 
     */
    protected static VueConsole vue;
    
    /**
     * 
     */
    protected static Scanner sc;
    
    /**
     * Pour la session
     */
    private static boolean session;
    
    /**
     * 
     */
    private static boolean partie;
    /**
     * Pour un tour de jeu
     */
    protected boolean tourDeJeu;
    
    /**
     * D�finit le choix mode d�veloppeur ou normal
     */
    protected static boolean ChoixModeSession;
    
    /**
     * D�finit le choix mode de jeu : challenger, d�fenseur, duel
     */
    protected int modeJeu;
    
    /**
     * 
     */
    protected String proposition;
    
    public static void main(String[] args) {

    	ControleurJeu.initialiserSession();
    	ControleurJeu.demarrerSession();
    	
    }
    
    public abstract boolean jouer();
    public abstract boolean verifierVictoire(Joueur joueur, Jeu jeu);
    
    public static void initialiserSession() {
    	
    	vue = new VueConsole();
    	sc = new Scanner(System.in);
    	
    	// Affichage d'un message d'accueil
    	vue.afficherAccueil();
    	vue.afficherChoixSession();
    	
    	// R�cup�ration du choix de session (Joueur / d�veloppeur)
    	initialiserChoixSession();  	
    }
    
    public static void demarrerSession() {
    	
    	int choix;
     	session = true; 
     	
     	do {
        	// Choisir un jeu
         	vue.afficherMenuJeux();
        	choix = choixJeu();    	
        	controleurJeu = ControleurJeuFactory.getJeuControleur(choix);    	
        	
        	// Choisir un mode
        	vue.afficherMenuMode();    	
        	choix = choixMode();
        	
        	// Lancement du jeu
        	partie = true;
        	do {
        		demarrerJeu(choix);
        		// Permet de choisir de poursuivre la partie
        		partie = poursuivrePartie();
        	}
        	while(partie);
        	
        	
        	// Permet de choisir de poursuivre la session
        	session = poursuivreSession();
     	}
     	while(session);
    	

    	
    }
    /**
     * D�marre je jeu en fonction du mode choisi (challenger, d�fenseur, duel)
     * @param choixMode
     * @return
     */
    public static boolean demarrerJeu(int choixMode) {
    	
    	switch (choixMode) {
    	
	    	case 1 : {
	    		controleurJeu.modeChallenger();
	    		break;
	    	}
	    	
	    	case 2 : {
	    		controleurJeu.modeDefenseur();
	    		break;
	    	}
	    	
	    	case 3 : {
	    		controleurJeu.duel();
	    		break;
	    	}
    	}
    	
    	return false;
    }


    /**
     * Permet de choisir entre le jeu combinaison +- et mastermind
     */
    public static int choixJeu() {
    	
    	String saisie;
    	boolean test;
    	
    	do {    		 
    		saisie = sc.next();
    		try {
    			test = testerChoix(saisie);
    		}
    		catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
    	    	vue.afficherMenuJeux();
    		}
    	}
    	while(!test);
    	
    	return Integer.parseInt(saisie);
    }

    /**
     * Permet de choisir le mode de jeu entre Challenger - D�fenseur - Duel
     */
    public static int choixMode() {

    	String saisie;
    	boolean test;
    	
    	do {    		
    		saisie = sc.next();
    		try {
    			test = testerChoixMode(saisie);
    		}
    		catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMenuMode();
    		}
    	}
    	while(!test);
    	
    	return Integer.parseInt(saisie);
    }   

    /**
     * Permet de choisir de jouer en mode d�veloppeur ou joueur
     */
    public static void initialiserChoixSession() {
    	
    	String saisie;
    	boolean test;
    	
    	do {      		
    		saisie = sc.next();
        	try {
        		test = testerChoix(saisie);
        		
        		if(saisie.equals("1")) {
        			ChoixModeSession = false;        			
        		}
        		else {
        			ChoixModeSession = true;
        		}
        	}
        	catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
            	vue.afficherChoixSession();
        	}        	
    	}
    	while(!test);
   	
    }
    
    /**
     * Permet de choisir de poursuivre la session
     */
    public static boolean poursuivreSession() {
    	
    	vue.afficherPoursuivreSession();
    	String saisie;
    	boolean test;
    	boolean retour = false;
    	
    	do {
    		saisie = sc.next();
    		
    		try {
				test = testerChoix(saisie);
				
        		if(saisie.equals("1")) {
        			retour = true;        			
        		}
        		else {
        			retour = false;
        		}
			} catch (Exception e) {
				test = false;
        		System.out.println(e.getMessage());
        		vue.afficherPoursuivreSession();
			}
    	}
    	while(!test);
    	return retour;
    }
    
    /**
     * Permet de choisir de poursuivre la partie
     */
    public static boolean poursuivrePartie() {
    	
    	vue.afficherPoursuivrePartie();
    	String saisie;
    	boolean test;
    	boolean retour = false;
    	
    	do {
    		saisie = sc.next();
    		
    		try {
				test = testerChoix(saisie);
				
        		if(saisie.equals("1")) {
        			retour = true;        			
        		}
        		else {
        			retour = false;
        		}
			} catch (Exception e) {
				test = false;
        		System.out.println(e.getMessage());
        		vue.afficherPoursuivrePartie();
			}
    	}
    	while(!test);
    	return retour;
    }
    
    /**
     * Teste le choix du menu 1 ou 2
     * @param string
     * @return
     * @throws Exception
     */
    public static boolean testerChoix(String string) throws Exception {			
      	 
		Pattern pattern = Pattern.compile("[12]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte, choisir 1 ou 2 !");
		}
		return resultat;

    }
    
    /**
     * Teste le choix du menu 1,2 ou 3
     * @param string
     * @return
     * @throws Exception
     */
    public static boolean testerChoixMode(String string) throws Exception {			
     	 
		Pattern pattern = Pattern.compile("[1-3]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte, choisir 1 - 2 ou 3 !");
		}
		return resultat;    	
    }

}