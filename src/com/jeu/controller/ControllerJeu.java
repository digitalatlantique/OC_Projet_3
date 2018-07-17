package com.jeu.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeu.controller.ControllerJeuFactory;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.view.VueConsole;

/**
 * 
 */
public abstract class ControllerJeu implements ModeJeu {

    /**
     * Default constructor
     */
    public ControllerJeu() {

    }
    
    /**
     * D�finit le choix mode d�veloppeur ou normal
     */
    protected static boolean choixModeSession = false;

    /**
     * Permet de param�trer de le joueur 1 (Attaque - D�fense - Les deux)
     */
    protected ControlleurJoueur joueur1;
    
    /**
     * Permet de param�trer de le joueur 2 (Attaque - D�fense - Les deux)
     */
    protected ControlleurJoueur joueur2;    

    /**
     * Pour un tour de jeu (coup, essai)
     */
    protected boolean tourDeJeu;
    
    /**
     * D�finit le choix mode de jeu : challenger, d�fenseur, duel
     */
    protected int modeJeu;
    
    /**
     * Correspond � une proposition
     */
    protected String proposition;

	public abstract boolean jouer();
    public abstract boolean verifierVictoire(Joueur joueur, Jeu jeu);








    

    

    

    

    

    

}