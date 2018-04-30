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
    	
    	String pseudo;
    	
    	// Affichage d'un message d'accueil
    	vue.afficherAccueil();
    	
    	// Récupération du pseudo pour créer le joueur 1
    	do {
    		vue.afficherMessage("Choisir un pseudo : ");
        	pseudo = sc.next();
    	}
    	while(!testerChaine(pseudo));
    	
    	joueur1 = new Joueur(pseudo);  	
    }
    
    public void demarrerSession() {
    	
    	String saisie;
    	session = true;
    	
    	do {    		
    		vue.afficherMenuJeux(); 
    		saisie = sc.next();
    	}
    	while(!testerChoixJeu(saisie));
    	
    	jeu = JeuFactory.getJeu(Integer.parseInt(saisie));
    	
    	
    	
    	
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

    /**
     * @param type 
     * @param sasie
     */
    public void verifierSaisie(String type, String sasie) {
        // TODO implement here
    }
    
    public boolean testerChaine(String string) {			
   	 
		Pattern pattern = Pattern.compile("[a-zA-Z0-9àêëéèùü]+");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		return resultat;    	
    }
    
    public boolean testerChoixJeu(String string) {			
      	 
		Pattern pattern = Pattern.compile("[12]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		return resultat;    	
    }

}