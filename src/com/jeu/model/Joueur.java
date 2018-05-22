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
    
    /**
     * 
     * @param pseudo
     */
    public Joueur(String pseudo) {
    	this.pseudo = pseudo;
    }

    /**
     * 
     */
    private String pseudo;

    /**
     * 
     */
    private TypeJoueur type;

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public TypeJoueur getType() {
		return type;
	}

	public void setType(TypeJoueur type) {
		this.type = type;
	}


    
}