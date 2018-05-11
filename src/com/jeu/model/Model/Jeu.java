package com.jeu.model.Model;


import java.util.*;

/**
 * 
 */
public abstract class Jeu implements ModeJeu {
	
	/**
	 * 
	 */
	protected Scanner sc;
	
    /**
     * 
     */
    protected String combinaisonSecrete;

    /**
     * 
     */
    protected String combinaisonReponse;

    /**
     * 
     */
    protected String saisie;

    /**
     * 
     */
    protected char[] combinaisonSecreteTab;
    
    /**
     * 
     */
    protected char[] combinaisonReponseTab;

    /**
     * 
     */
    protected boolean[] combinaisonTest;
    
    /**
     * 
     */
    protected boolean partie;


    protected static int longueurCombinaison = 4;
    
    protected static int nombreEssais = 12;
    
    public abstract void initialiser();
    public abstract String genererCombinaison(int longueur);
    public abstract boolean jouer();
    public abstract void verifierResultat();
    public abstract boolean verifierVictoire();    

}