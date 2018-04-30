package com.jeu.view.View;


import java.util.*;

/**
 * 
 */
public class VueConsole {

    /**
     * Default constructor
     */
    public VueConsole() {
    }

    /**
     * 
     */
    private Scanner scanner;
    
    /**
     * 
     */    
    public void afficherAccueil() {
    	
    	String message = 	"***********************************************************************\n"
    						+"*                            Bienvenue !                              *\n"
    						+"*      Cette application vous propose des jeux de logique.            *\n"
    						+"***********************************************************************";    	
    	System.out.println(message);
    }
    
    public void afficherMessage(String message) {
    	
    	System.out.println(message);
    }

    /**
     * 
     */
    public void afficherMenuJeux() {
    	String message = 	  "***********************************************************************\n"
							+ "*                            Choisir un jeu :	                     *\n"
							+ "*                                                                     *\n"
							+ "* 1 : Recherche d'une combinaison avec +-                             *\n"
							+ "* 2 : Mastermind                                                      *\n"
							+ "***********************************************************************";
    	System.out.println(message);
    }

    /**
     * 
     */
    public void afficherMenuMode() {
        // TODO implement here
    }

    /**
     * @param combinaison
     */
    public void afficherCombinaison(String combinaison) {
        // TODO implement here
    }

    /**
     * 
     */
    public void afficherVictoire() {
        // TODO implement here
    }

}