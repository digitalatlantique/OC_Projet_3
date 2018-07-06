package com.jeu.controller;

import com.jeu.model.Jeu;
/**
 * Permet de retourner des instances de jeu : Combinaison+-, Mastermind
 * @author Workstation
 */
public class ControlleurJeuFactory {
	
	public static final int COMBINAISON = 1;
	public static final int MASTERMIND = 2;
	
    public static ControlleurJeu getJeuControleur(int choix) {
    	
    	switch (choix) {
    	
    		case 1 : {
    			return new ControlleurJeuCombinaison();
    		}
    		
    		case 2 : {
    			return new ControlleurJeuMastermind();
    		}
    		
    		default : return null;
    	}
    }

}
