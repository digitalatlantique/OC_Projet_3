package com.jeu.model.Model;


import java.util.*;

/**
 * 
 */
public abstract class Jeu {
	
	public static final int COMBINAISON = 1;
	public static final int MASTERMIND = 2;


    /**
     * 
     */
    protected String combinaisonSecrete;

    /**
     * 
     */
    protected String combinaisonATrou;

    /**
     * 
     */
    protected String saisie;

    /**
     * 
     */
    protected char[] combinaisonTab;

    /**
     * 
     */
    protected boolean[] combinaisonTest;





    /**
     * 
     */
    public void initialiser() {
        // TODO implement here
    }

    /**
     * 
     */
    public void genererCombinaison() {
        // TODO implement here
    }

    /**
     * 
     */
    public void jouer() {
        // TODO implement here
    }

    /**
     * 
     */
    public void verifierResultat() {
        // TODO implement here
    }

    /**
     * 
     */
    public void verifierVictoire() {
        // TODO implement here
    }    

}