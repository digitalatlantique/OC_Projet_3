package com.jeu.controller;

import com.jeu.model.Jeu;

public class ControleurJeuFactory {
	
	public static final int COMBINAISON = 1;
	public static final int MASTERMIND = 2;
	
    public static ControleurJeu getJeuControleur(int choix) {
    	
    	switch (choix) {
    	
    		case 1 : {
    			return new ControleurJeuCombinaison();
    		}
    		
    		case 2 : {
    			return new ControleurJeuMastermind();
    		}
    		
    		default : return null;
    	}
    }

}
