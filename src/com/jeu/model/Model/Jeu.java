package com.jeu.model.Model;


import java.util.*;

/**
 * 
 */
public abstract class Jeu implements JeuFactory {

    /**
     * Default constructor
     */
    public Jeu() {
    }

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

    /**
     * @return
     */
    public Jeu getJeuCombinaison() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Jeu getJeuMastermind() {
        // TODO implement here
        return null;
    }

}