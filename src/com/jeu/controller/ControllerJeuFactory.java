package com.jeu.controller;

import com.jeu.model.Jeu;
/**
 * Permet de retourner des instances de jeu : Combinaison+-, Mastermind
 * @author Workstation
 */
public class ControllerJeuFactory {
	
	public static final int COMBINAISON = 1;
	public static final int MASTERMIND = 2;
	
    public static ControllerJeu getJeuControleur(int choix) {
    	
    	switch (choix) {
    	
    		case 1 : {
    			return new ControllerJeuCombinaison();
    		}
    		
    		case 2 : {
    			return new ControllerJeuMastermind();
    		}
    		
    		default : return null;
    	}
    }

}
