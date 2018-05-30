package com.jeu.controller;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.controller.ControleurJeuFactory;

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
    protected ControleurJoueur joueurAttaque;
    
    /**
     * 
     */
    protected ControleurJoueur joueurDefend;

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
     * 
     */
    private static boolean session;
    
    /**
     * 
     */
    protected boolean partie;
    
    /**
     * Définit le choix mode développeur ou normal
     */
    protected static boolean ChoixModeSession;
    
    /**
     * Définit le choix mode de jeu : challenger, défenseur, duel
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
    public abstract boolean verifierVictoire(Joueur joueur);
    
    public static void initialiserSession() {
    	
    	vue = new VueConsole();
    	sc = new Scanner(System.in);
    	
    	// Affichage d'un message d'accueil
    	vue.afficherAccueil();
    	vue.afficherChoixSession();
    	
    	// Récupération du choix de session (Joueur / développeur)
    	initialiserChoixSession();  	
    }
    
    public static void demarrerSession() {
    	
    	int choix;
     	session = true;    	
    	
    	// Choisir un jeu
     	vue.afficherMenuJeux();
    	choix = choixJeu();    	
    	controleurJeu = ControleurJeuFactory.getJeuControleur(choix);    	
    	
    	// Choisir un mode
    	vue.afficherMenuMode();    	
    	choix = choixMode();
    	
    	demarrerJeu(choix);
    	
    }
    
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
	    		System.out.println("En cours de développement");
	    		break;
	    	}
    	}
    	
    	return false;
    }


    /**
     * 
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
     * 
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
    
    public static boolean testerChoix(String string) throws Exception {			
      	 
		Pattern pattern = Pattern.compile("[12]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte, choisir 1 ou 2 !");
		}
		return resultat;

    }
    
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