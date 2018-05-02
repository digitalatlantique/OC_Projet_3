package com.jeu.controller.Controlleur;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Model.Jeu;
import com.jeu.model.Model.JeuFactory;
import com.jeu.model.Model.Joueur;
import com.jeu.view.View.VueConsole;

/**
 * 
 */
public class ControleurJeu {

    /**
     * Default constructor
     */
    public ControleurJeu() {
    	
    	vue = new VueConsole();
    	sc = new Scanner(System.in);
    }

    /**
     * 
     */
    private Joueur joueur1;
    
    /**
     * 
     */
    private Joueur joueur2;

    /**
     * 
     */
    private Jeu jeu;

    /**
     * 
     */
    private VueConsole vue;
    
    /**
     * 
     */
    private static Scanner sc;
    
    /**
     * 
     */
    private boolean session;
    



    public static void main(String[] args) {
    	
    	
    	ControleurJeu cJeu = new ControleurJeu();
    	cJeu.initialiserSession();
    	cJeu.demarrerSession();
    	
    }
    
    public void initialiserSession() {
    	
    	// Affichage d'un message d'accueil
    	vue.afficherAccueil();
    	vue.afficherMessage("Choisir un pseudo : ");
    	
    	// Récupération du pseudo après test saisie pour créer le joueur 1
    	String pseudo = recupererPseudo();
    	
    	joueur1 = new Joueur(pseudo);  	
    }
    
    public void demarrerSession() {
    	
    	int choix;
     	session = true;    	
    	
    	// Choisir un jeu
     	vue.afficherMenuJeux();
    	choix = recupererChoixJeu();    	
    	jeu = JeuFactory.getJeu(choix);    	
    	
    	// Menu pour choisir un mode
    	vue.afficherMenuMode();    	
    	choix = recupererChoixMode();
    	
    	switch (choix) {
	    	case 1 : {
	    		modeChallenger();
	    	}
    	}
    	
    }

    /**
     * 
     */
    public void modeChallenger() {
        // TODO implement here
    }

    /**
     * 
     */
    public void modeDefenseur() {
        // TODO implement here
    }

    /**
     * 
     */
    public void Duel() {
        // TODO implement here
    }

    /**
     * 
     */
    public void choixJeu() {
        // TODO implement here
    }

    /**
     * 
     */
    public void choixMode() {
        // TODO implement here
    }

    public String recupererPseudo() {
    	
    	String pseudo;
    	boolean test;
    	
    	do {      		
        	pseudo = sc.next();
        	try {
        		test = testerChaine(pseudo);
        	}
        	catch (Exception e) {
        		test = false;
        		System.err.println(e.getMessage());
            	vue.afficherMessage("Choisir un pseudo : ");
        	}        	
    	}
    	while(!test);
    	
    	return pseudo;    	
    }
    
    public int recupererChoixJeu() {
    	String saisie;
    	boolean test;
    	
    	do {    		 
    		saisie = sc.next();
    		try {
    			test = testerChoixJeu(saisie);
    		}
    		catch (Exception e) {
        		test = false;
        		System.err.println(e.getMessage());
    	    	vue.afficherMenuJeux();
    		}
    	}
    	while(!test);
    	
    	return Integer.parseInt(saisie);
    }
    
    public int recupererChoixMode(){
    	String saisie;
    	boolean test;
    	
    	do {    		
    		saisie = sc.next();
    		try {
    			test = testerChoixMode(saisie);
    		}
    		catch (Exception e) {
        		test = false;
        		System.err.println(e.getMessage());
        		vue.afficherMenuMode();
    		}
    	}
    	while(!test);
    	
    	return Integer.parseInt(saisie);
    }
    
    public boolean testerChaine(String string) throws Exception {			
   	 
		Pattern pattern = Pattern.compile("[a-zA-Z0-9àêëéèùü]+");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Caractère non valide !");
		}
		return resultat;
		    	
    }
    
    public boolean testerChoixJeu(String string) throws Exception {			
      	 
		Pattern pattern = Pattern.compile("[12]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte !");
		}
		return resultat;

    }
    
    public boolean testerChoixMode(String string) throws Exception {			
     	 
		Pattern pattern = Pattern.compile("[1-3]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte !");
		}
		return resultat;    	
    }

}