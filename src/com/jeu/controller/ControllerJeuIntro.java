package com.jeu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeu.controller.defendre.Defense;
import com.jeu.model.Jeu;
import com.jeu.view.VueConsole;

public class ControllerJeuIntro {
	

    
    /**
     * Permet la gestion des logs
     */
    private Logger logger = LogManager.getLogger(ControllerJeuIntro.class);
    /**
     * Permet l'affichage des éléments en console
     */
    protected VueConsole vue;
    
    /**
     * Pour récupérer les saisies des utilisateurs
     */
    protected Scanner sc;
    /**
     * Représente la session la session
     */
    private boolean session;
    /**
     * Correspond au contrôleur de jeu principale
     */
    private ControllerJeu controlleurJeu;
    /**
     * Représente une partie
     */
    private boolean partie;
	
    public static void main(String[] args) {
    	
    	ControllerJeuIntro jeuIntro = new ControllerJeuIntro();

    	jeuIntro.initialiserParametreLigneCommande(args);
    	jeuIntro.initialiserData();
    	jeuIntro.initialiserSession();
    	jeuIntro.demarrerSession();    	
    }

	private void initialiserParametreLigneCommande(String[] args) {

    	for(int i=0; i<args.length; i++) {
    		
    		if(args[i].equals("--dev")) {
    			ControllerJeu.choixModeSession = true;
    		}
    	}		
	}
	
    /**
     * Permet d'initialiser la longueur de la combinaison et le nombre d'essais
     */
    public void initialiserData() {
    	
		Properties property = new Properties();
		InputStream input = null;
		String file = "/config.properties";
		String longueurCombinaison;
		String longueurMastermind;
		String nombreDeChiffre;
		String essais;
		String developpeur = "";
		
		try {
			input = ControllerJeuIntro.class.getResourceAsStream(file);
			
			property.load(input);
			
			longueurCombinaison = property.getProperty("longueurCombinaison");
			longueurMastermind = property.getProperty("longueurMastermind");
			nombreDeChiffre = property.getProperty("nombreDeChiffre");
			essais = property.getProperty("essais");
			developpeur = property.getProperty("developpeur").toLowerCase();
			
			try {
				Jeu.longueurCombinaison = propertiesTestLongueur(longueurCombinaison);
				Jeu.longueurMastermind = propertiesTestLongueur(longueurMastermind);
				Defense.nombreDeChiffre = propertiesTestNombreDeChiffre(nombreDeChiffre);
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
		
		if(developpeur.equals("true")) {
			ControllerJeu.choixModeSession = true;
		}
		
    }
    
    private int propertiesTestNombreDeChiffre(String nombreDeChiffre) throws Exception {
    	
    	int nombre = Integer.parseInt(nombreDeChiffre);

    	if(nombre < 4 || nombre > 10) {
    		throw new Exception("Merci d'écrire un nombre compris entre 4 et 10 !\n"
    							+ "Initialisation du nombre de chiffre utilisable à 10");
    	}
    	else {
    		return nombre;
    	}
    	
	}

	/**
     * Cette méthode test la longueur saisie dans le fichier properties 
     * De type int et compris entre 4 et 10
     * @throws Exception 
     */
    public int propertiesTestLongueur(String data) throws Exception{
    	
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
    
    /**
     * Initialise la session et paramètre le choix mode développeur ou non
     */
    public void initialiserSession() {
    	
    	logger.debug("Demarrage de l'application");

    	vue = new VueConsole();
    	sc = new Scanner(System.in);
    	
    	// Affichage d'un message d'accueil
    	vue.afficherAccueil();    	
 	
    }
    
    /**
     * Permet d'initialiser et de démarrer une session
     */
    public void demarrerSession() {
    	
    	int choix;
     	session = true; 
     	
     	do {
        	// Choisir un jeu
         	vue.afficherMenuJeux();
        	choix = choixJeu();    	
        	controlleurJeu = ControllerJeuFactory.getJeuControleur(choix);    	
        	
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
     * Permet de choisir entre le jeu combinaison +- et mastermind
     */
    public int choixJeu() {
    	
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
    public int choixMode() {

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
     * Démarre je jeu en fonction du jeu et du mode choisi (challenger, défenseur, duel)
     * @param choixMode
     * @return boolean
     */
    public boolean demarrerJeu(int choixMode) {
    	
    	switch (choixMode) {
    	
	    	case 1 : {
	    		controlleurJeu.modeChallenger();
	    		break;
	    	}
	    	
	    	case 2 : {
	    		controlleurJeu.modeDefenseur();
	    		break;
	    	}
	    	
	    	case 3 : {
	    		controlleurJeu.duel();
	    		break;
	    	}
    	}
    	
    	return false;
    }
    /**
     * Permet de choisir de poursuivre la partie
     */
    public boolean poursuivrePartie() {
    	
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
     * Permet de choisir de poursuivre la session
     */
    public boolean poursuivreSession() {
    	
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
     * Teste le choix du menu 1 ou 2
     * @param string
     * @return
     * @throws Exception
     */
    public boolean testerMenu(String string) throws Exception {			
      	 
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
    public boolean testerChoixMode(String string) throws Exception {			
     	 
		Pattern pattern = Pattern.compile("[1-3]{1}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte, choisir 1 - 2 ou 3 !");
		}
		return resultat;    	
    }

}
