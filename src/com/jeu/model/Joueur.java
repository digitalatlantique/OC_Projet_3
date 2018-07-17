package com.jeu.model;


import java.util.*;

/**
 * 
 */
public class Joueur {

    /**
     * Default constructor
     */
    public Joueur() {
    }
    
    public Joueur(TypeJoueur type, int nombreEssais) {
    	
    	this.type = type;
    	this.nombreEssais = nombreEssais;
    }
    

    /**
     * 
     */
    protected int nombreEssais;


    /**
     * 
     */
    protected TypeJoueur type;



	public TypeJoueur getType() {
		return type;
	}

	public void setType(TypeJoueur type) {
		this.type = type;
	}

	public int getNombreEssais() {
		return nombreEssais;
	}

	public void setNombreEssais(int nombreEssais) {
		this.nombreEssais = nombreEssais;
	}    
}