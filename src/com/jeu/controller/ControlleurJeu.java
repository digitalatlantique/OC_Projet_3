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

import com.jeu.controller.ControlleurJeuFactory;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
import com.jeu.view.VueConsole;

/**
 * 
 */
public abstract class ControlleurJeu implements ModeJeu {

    /**
     * Default constructor
     */
    public ControlleurJeu() {

    }

    /**
     * Permet de paramètrer de le joueur 1 (Attaque - Défense - Les deux)
     */
    protected ControlleurJoueur joueur1;
    
    /**
     * Permet de paramètrer de le joueur 2 (Attaque - Défense - Les deux)
     */
    protected ControlleurJoueur joueur2;

    /**
     * Correspond au contrôleur de jeu principale
     */
    private static ControlleurJeu controleurJeu;

    /**
     * Permet l'affichage des éléments en console
     */
    protected static VueConsole vue;
    
    /**
     * Pour récupérer les saisies des utilisateurs
     */
    protected static Scanner sc;
    
    /**
     * Représente la session la session
     */
    private static boolean session;
    
    /**
     * Représente une partie
     */
    private static boolean partie;
    /**
     * Pour un tour de jeu (coup, essai)
     */
    protected boolean tourDeJeu;
    
    /**
     * Définit le choix mode développeur ou normal
     */
    protected static boolean ChoixModeSession;
    
    /**
     * Définit le choix mode de jeu : challenger, défenseur, duel
     */
    protected int modeJeu;
    
    /**
     * Correspond à une proposition
     */
    protected String proposition;
    /**
     * Permet la gestion des logs
     */
    private static Logger logger = LogManager.getLogger(ControlleurJeu.class);
    
    public static void main(String[] args) {


    	ControlleurJeu.initialiserData();
    	ControlleurJeu.initialiserSession();
    	ControlleurJeu.demarrerSession();    	
    }
    
    public abstract boolean jouer();
    public abstract boolean verifierVictoire(Joueur joueur, Jeu jeu);
    /**
     * Initialise la session et paramètre le choix mode développeur ou non
     */
    public static void initialiserSession() {
    	
    	logger.debug("Demarrage de l'application");

    	vue = new VueConsole();
    	sc = new Scanner(System.in);
    	
    	// Affichage d'un message d'accueil
    	vue.afficherAccueil();
    	vue.afficherChoixSession();
    	
    	// Récupération du choix de session (Joueur / développeur)
    	initialiserChoixSession();  	
    }
    /**
     * Permet d'initialiser et de démarrer une session
     */
    public static void demarrerSession() {
    	
    	int choix;
     	session = true; 
     	
     	do {
        	// Choisir un jeu
         	vue.afficherMenuJeux();
        	choix = choixJeu();    	
        	controleurJeu = ControlleurJeuFactory.getJeuControleur(choix);    	
        	
        	// Choisir un mode de jeu
        	vue.afficherMenuMode();    	
        	choix = choixMode();
        	
        	// Lancement du jeu
        	partie = true;
        	do {
        		demarrerJeu(choix);
        		// Permet de choisir de poursuivre la partie
        		partie = poursuivrePartie();
        	}
        	while(partie);        	
        	
        	// Permet de choisir de poursuivre la session
        	session = poursuivreSession();
     	}
     	while(session);
    	
     	logger.debug("Fin de l'application");
    	
    }
    /**
     * Démarre je jeu en fonction du jeu et du mode choisi (challenger, défenseur, duel)
     * @param choixMode
     * @return boolean
     */
    public static boolean demarrerJeu(int choixMode) {
    	
    	switch (choixMode) {
    	
	    	case 1 : {
	    		controleurJeu.modeChallenger();
	    		break;
	    	}
	    	
	    	case 2 : {
	    		controleurJeu.modeDefenseur();
	    		break;
	    	}
	    	
	    	case 3 : {
	    		controleurJeu.duel();
	    		break;
	    	}
    	}
    	
    	return false;
    }


    /**
     * Permet de choisir entre le jeu combinaison +- et mastermind
     */
    public static int choixJeu() {
    	
    	String saisie;
    	boolean test;
    	
    	do {    		 
    		saisie = sc.next();
    		try {
    			test = testerMenu(saisie);
    		}
    		catch (Exception e) {
        		test = false;
        		logger.debug(e.getMessage());
    	    	vue.afficherMenuJeux();
    		}
    	}
    	while(!test);
    	
    	return Integer.parseInt(saisie);
    }

    /**
     * Permet de choisir le mode de jeu entre Challenger - Défenseur - Duel
     */
    public static int choixMode() {

    	String saisie;
    	boolean test;
    	
    	do {    		
    		saisie = sc.next();
    		try {
    			test = testerChoixMode(saisie);
    		}
    		catch (Exception e) {
        		test = false;
        		logger.debug(e.getMessage());
        		vue.afficherMenuMode();
    		}
    	}
    	while(!test);
    	
    	return Integer.parseInt(saisie);
    }
    
    /**
     * Permet d'initialiser la longueur de la combinaison et le nombre d'essais
     */
    public static void initialiserData() {
    	
		Properties property = new Properties();
		InputStream input = null;
		String file = "config.properties";
		String longueur;
		String essais;
		
		try {
			input = new FileInputStream(file);
			
			property.load(input);
			
			longueur = property.getProperty("longueur");
			essais = property.getProperty("essais");
			
			try {
				Jeu.longueurCombinaison = propertiesTestLongueur(longueur);
				Jeu.nombreEssais = propertiesTestEssais(essais);
			}
			catch(Exception e) {
				logger.debug(e.getMessage());				
			}
		}
		catch(IOException e) {
			logger.debug(e.getMessage());
		}
		finally {
			if(input != null) {
				try {
					input.close();
				}
				catch(IOException e) {
					logger.debug(e.getMessage());
				}
			}
		}
		
    }

    /**
     * Permet de choisir de jouer en mode développeur ou joueur
     */
    public static void initialiserChoixSession() {
    	
    	String saisie;
    	boolean test;
    	
    	do {      		
    		saisie = sc.next();
        	try {
        		test = testerMenu(saisie);
        		
        		if(saisie.equals("1")) {
        			ChoixModeSession = false;        			
        		}
        		else {
        			ChoixModeSession = true;
        		}
        	}
        	catch (Exception e) {
        		test = false;
        		logger.debug(e.getMessage());
            	vue.afficherChoixSession();
        	}        	
    	}
    	while(!test);
   	
    }
    
    /**
     * Permet de choisir de poursuivre la session
     */
    public static boolean poursuivreSession() {
    	
    	vue.afficherPoursuivreSession();
    	String saisie;
    	boolean test;
    	boolean retour = false;
    	
    	do {
    		saisie = sc.next();
    		
    		try {
				test = testerMenu(saisie);
				
        		if(saisie.equals("1")) {
        			retour = true;        			
        		}
        		else {
        			retour = false;
        		}
			} catch (Exception e) {
				test = false;
				logger.debug(e.getMessage());
        		vue.afficherPoursuivreSession();
			}
    	}
    	while(!test);
    	return retour;
    }
    
    /**
     * Permet de choisir de poursuivre la partie
     */
    public static boolean poursuivrePartie() {
    	
    	vue.afficherPoursuivrePartie();
    	String saisie;
    	boolean test;
    	boolean retour = false;
    	
    	do {
    		saisie = sc.next();
    		
    		try {
				test = testerMenu(saisie);
				
        		if(saisie.equals("1")) {
        			retour = true;        			
        		}
        		else {
        			retour = false;
        		}
			} catch (Exception e) {
				test = false;
				logger.debug(e.getMessage());
        		vue.afficherPoursuivrePartie();
			}
    	}
    	while(!test);
    	return retour;
    }
    
    /**
     * Teste le choix du menu 1 ou 2
     * @param string
     * @return
     * @throws Exception
     */
    public static boolean testerMenu(String string) throws Exception {			
      	 
		Pattern pattern = Pattern.compile("[12]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte, choisir 1 ou 2 !");
		}
		return resultat;
    }
    
    /**
     * Teste le choix du menu 1,2 ou 3
     * @param string
     * @return
     * @throws Exception
     */
    public static boolean testerChoixMode(String string) throws Exception {			
     	 
		Pattern pattern = Pattern.compile("[1-3]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte, choisir 1 - 2 ou 3 !");
		}
		return resultat;    	
    }
    
    /**
     * Cette méthode test la longueur saisie dans le fichier properties 
     * De type int et compris entre 4 et 10
     * @throws Exception 
     */
    public static int propertiesTestLongueur(String data) throws Exception{
    	
		Pattern pattern = Pattern.compile("[0-9]{1,2}");
		Matcher matcher = pattern.matcher(data);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Merci d'écrire un nombre compris entre 4 et 10 !\n"
								+ "Initialisation de la longueur de la combinaison à 4.");
			
		}
		int longueur = Integer.parseInt(data);
		
		if(longueur < 4 || longueur > 10) {
			throw new Exception("Merci d'écrire un nombre compris entre 4 et 10 !\n"
					+ "Initialisation de la longueur de la combinaison à 4.");
		}		
		return longueur;
		
    }
    /**
     * Cette méthode test le nombre d'essais saisie dans le fichier properties 
     * De type int et compris entre 4 et 25
     * @throws Exception 
     */
    public static int propertiesTestEssais(String data) throws Exception{
    	
		Pattern pattern = Pattern.compile("[0-9]{1,2}");
		Matcher matcher = pattern.matcher(data);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Merci d'écrire un nombre compris entre 4 et 25 !\n"
								+ "Initialisation du nombre d'essais à 10.");
			
		}
		int longueur = Integer.parseInt(data);
		
		if(longueur < 4 || longueur > 25) {
			throw new Exception("Merci d'écrire un nombre compris entre 4 et 25 !\n"
					+ "Initialisation du nombre d'essais à 10.");
		}		
		return longueur;		
    }
}