package com.jeu.model.Model;

public class JeuFactory {
	
	public static final int COMBINAISON = 1;
	public static final int MASTERMIND = 2;
	
    public static Jeu getJeu(int choix) {
    	
    	switch (choix) {
    	
    		case 1 : {
    			return new Combinaison();
    		}
    		
    		case 2 : {
    			return new Mastermind();
    		}
    		
    		default : return null;
    	}
    }

}
