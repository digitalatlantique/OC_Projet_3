package com.jeu.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeu.model.Jeu;
/**
 * Permet de retourner des instances de jeu : Combinaison+-, Mastermind
 * @author Workstation
 */
public class ControllerJeuFactory {
	
    /**
     * Permet la gestion des logs
     */
    private static Logger logger = LogManager.getLogger(ControllerJeuFactory.class);
	
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
    		
    		default : {
    			logger.debug("Aucun jeu à charger !!");
    			return null;
    		}
    	}
    }

}
