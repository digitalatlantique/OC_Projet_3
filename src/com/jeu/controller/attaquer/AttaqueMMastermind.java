package com.jeu.controller.attaquer;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeu.controller.ControlleurJeu;
import com.jeu.model.Jeu;
/**
 * Cette classe permet � l'ordinateur (machine) de faire une proposition avec le jeu mastermind
 * En fonction des indications du d�fenseur : plac�, pr�sent(ou mauvaise position)
 * @author Workstation
 *
 */
public class AttaqueMMastermind extends Attaque {
	
	/**
	 * Permet la gestion des logs
	 */
	private Logger logger = LogManager.getLogger(AttaqueMMastermind.class);
	/**
	 * Permer l'initialisation du premier coup)
	 */
	private boolean premierCoup = true;
	/**
	 * Lorsqu'une proposition existe permet de faire une recherche de chiffre disponible dans le sens de lecture
	 */
	private boolean avance = true;
	/**
	 * Indique le nombre de chiffre trouv�
	 */
	private int trouve = 0;
	/**
	 * Indique le num�ro de tour de jeu (d�bute � z�ro)
	 */
	private int numeroCoup = 0;
	/**
	 * Initialise l'indice de recherche du chiffre 1
	 */
	private int indice1 = 0;
	/**
	 * Initialise l'indice de recherche du chiffre 2
	 */
	private int indice2 = 0;
	/**
	 * Permet de connaitre si un indice est disponible pour la proposition d'une nouvelle combinaison
	 */
	private boolean[] indiceDisponible;
	/**
	 * Tableau contenant les chiffres de la combinaison dans l'ordre d'apparition
	 * Les indices de ce tableau est en correspondance avec les lignes de la matrice historique position
	 */
	private int[] lesBonsChiffres;
	/**
	 * Correspond � l'historique des propositions
	 * Les lignes correspondent au num�ro du tour de jeu
	 * Les colonnes correspondent aux chiffres de la combinaison
	 */
	private int[][] historiquePropositions;
	/**
	 * Conserve l'historique des indications du d�fenseur
	 * Les lignes correspondent au num�ro du tour de jeu
	 * La colonne 0 = au nombre de chiffre pr�sent
	 * La colonne 1 = au nombre de chiffre plac�
	 */
	private int[][] historiqueResultat;
	/**
	 * Permet de connaitre si un chiffre est � la bonne place
	 * 0 : Le chiffre n'a pas sa place ici
	 * 1 : Le chiffre est bien plac�
	 * 2 : On ne sait pas
	 */
	private int[][] historiquePosition;
	
	/**
	 * Initialise les tableaux
	 */
	public AttaqueMMastermind() {
		
		historiquePropositions = new int[Jeu.nombreEssais][Jeu.longueurCombinaison];
		historiqueResultat = new int[Jeu.nombreEssais][2];
		indiceDisponible = new boolean[Jeu.longueurCombinaison];
		Arrays.fill(indiceDisponible, true);
		historiquePosition = new int[Jeu.longueurCombinaison][Jeu.longueurCombinaison];
		
		// On assigne toutes les positions � 2 (position incertaine)
		for(int i=0; i<historiquePosition.length;i++) {
			for (int j=0; j<historiquePosition[0].length; j++) {
				historiquePosition[i][j] = 2;
			}
		}
		lesBonsChiffres = new int[Jeu.longueurCombinaison];
	}
	/**
	 * L'ordinateur fait une proposition en fonction des indications du d�fenseur
	 */
	@Override
	public String proposer(HashMap<String, String> reponse) {
		// Pour le premier tour de jeu
		if(premierCoup) {
			
			premierCoup = false;
			
			return premierCoup();
		}		
		// Pour les tours suivant
		else {
			
			String place = reponse.get("place");
			String present = reponse.get("present");
			int compteurPlace = Integer.parseInt(place);
			int compteurPresent = Integer.parseInt(present);
			trouve = compteurPlace + compteurPresent;
			
			historiqueResultat[numeroCoup -1][0] = compteurPresent;
			historiqueResultat[numeroCoup -1][1] = compteurPlace;
			
			/* Si il reste des chiffres � trouver
			 * On propose une s�rie reprenant la combinaison pr�c�dente +1 � chaque chiffre restant � trouv�
			 */
			if(trouve < Jeu.longueurCombinaison) {
				
				proposerChiffre();
			}
			// Si tous les chiffres sont connus, on analyse les coups pr�c�dants puis on fait une nouvelle proposition
			else {
				
				analyserResultat();				
				miseAJourHistoriquePosition();									
				nouvelleProposition();
				
				return proposition;				
			}			
			return proposition;	
		}
	}
	
	/**
	 * Cr�er une premi�re combinaison compos� de 0 �tendu de la longueur de la combinaison 
	 * @return
	 */
	private String premierCoup() {
		
		int longueurCombinaison = Jeu.longueurCombinaison;
		
		for(int i=0; i<longueurCombinaison; i++) {
			proposition += 0;
			lesBonsChiffres[i] = 0;
			historiquePropositions[numeroCoup][i] = 0;
			
		}
		numeroCoup++;
		
		propositionTab = proposition.toCharArray();
		return proposition;		
	}
	
	/**
	 * Propose une suite de chiffre conservant les bons chiffres de la combinaison et ajoutant 1 pour les chiffres � deviner
	 */
	private void proposerChiffre() {
		int temporaire;
		int longueurPropositionTab = propositionTab.length;
		for (int i = trouve; i < longueurPropositionTab; i++) {

			temporaire = Character.digit(propositionTab[i], 10);
			temporaire += 1;
			lesBonsChiffres[i] = temporaire;
			propositionTab[i] = Character.forDigit(temporaire, 10);
		}		
		ajouterProposition();
		numeroCoup++;
		proposition = new String(propositionTab);
	}
	/**
	 * Permet d'ajouter la proposition � l'historique des propositions
	 */
	private void ajouterProposition() {		
		int longueurPropositionTab = propositionTab.length;
		for(int j = 0; j < longueurPropositionTab; j++ ) {
			historiquePropositions[numeroCoup][j] = Character.digit(propositionTab[j], 10);
		}		
	}	
	/**
	 * Analyse les chiffres mal plac�s et correctement bien plac�
	 */
	private void analyserResultat() {
		
		chercherChiffreMalPlace();		
		chercherChiffreBienPlace();
		
	}
	/**
	 * Recherche les chiffres appartenant � la combinaison et mal plac�
	 */
	private void chercherChiffreMalPlace() {
		int nombreDeChiffrePresent = nombreChiffrePresentMax();
		int nombreDeChiffrePlace = 0;
		int indice = -1;
		
		// Recherche des bons chiffres mal plac� (plac� = 0)
		while(nombreDeChiffrePresent>0) {
			int longueurHistoriqueResultat = historiqueResultat.length;
			int longueurHistoriquePositionLigne = historiquePosition.length;
			int longueurHistoriquePositionColonne = historiquePosition[0].length;
									
			for(int i=0; i<longueurHistoriqueResultat; i++) {					
				// Colonne 0 = chiffre pr�sent & Colonne 1 = chiffre plac�
				if(nombreDeChiffrePresent == historiqueResultat[i][0] && historiqueResultat[i][1] == nombreDeChiffrePlace) {

					for(int k=0; k<longueurHistoriquePositionLigne; k++) {
						for (int l=0; l<longueurHistoriquePositionColonne; l++) {
							// Si les bon chiffres sont mal plac�s, 
							if(historiquePropositions[i][l] == lesBonsChiffres[k]) {
								historiquePosition[k][l] = 0;
							}
						}
					}	
				}							
			}			
			nombreDeChiffrePresent--;
		}
		
		/*
		 * Recherche d'un bon chiffre pr�sent et mal plac�
		 * En comparant du dernier coup au premier (pr�sent � pr�sent - 1) et (plac� = 1)
		 * Condition : le chiffre de la deuxi�me ligne ne doit pas appartenir � la combinaison, sinon risque de confusion avec un chiffre bien plac�
		 */
		nombreDeChiffrePresent = nombreChiffrePresentMax();
		nombreDeChiffrePlace = nombreChiffrePlaceMax();
		int ligne1 = -1;
		int ligne2 = -1;
		// On boucle � partir du nombre max de chiffre pr�sent
		while(nombreDeChiffrePresent > 0) {
			// On boucle � partir du nombre max de chiffre plac�
			while(nombreDeChiffrePlace > 0) {
				
				int longueurHistoriqueResultat =  historiqueResultat.length-1;
				// Si un nouveau chiffre est pr�sent alors il est mal plac�
				for(int i = longueurHistoriqueResultat ; i>=0; i--) {

					if(historiqueResultat[i][0] == nombreDeChiffrePresent && historiqueResultat[i][1] == nombreDeChiffrePlace) {
						ligne1 = i;
					}
					if(historiqueResultat[i][0] == nombreDeChiffrePresent - 1 && historiqueResultat[i][1] == nombreDeChiffrePlace) {
						ligne2 = i;
					}
					if(ligne1 != -1 && ligne2 != -1 && ligne1 - ligne2 == 1) {
						
							comparerNouveauChiffrePresentMalPlace(ligne1, ligne2);						
							
						ligne1 = -1;
						ligne2 = -1;
						break;
					}
				}
				ligne1 = -1;
				ligne2 = -1;
				nombreDeChiffrePlace--;
			}
			nombreDeChiffrePlace = nombreChiffrePlaceMax();
			nombreDeChiffrePresent--;
		}

		/**
		 * Comparaison exemple
		 * ligne1 (pr�sent = 3, place = 1)
		 * ligne2 (pr�sent = 2, place = 2)
		 */
		nombreDeChiffrePresent = nombreChiffrePresentMax();
		nombreDeChiffrePlace = nombreChiffrePlaceMax();
		// On boucle � partir du nombre max de chiffre pr�sent		
		while(nombreDeChiffrePresent > 0) {
			// On boucle � partir du nombre max de chiffre plac�
			while(nombreDeChiffrePlace > 0) {
				
				int longueurHistoriqueResultat =  historiqueResultat.length-1;
				// Si deux chiffres intervertis ajoute un pr�sent et soustrait un plac�, alors ils sont mal plac�s
				for(int i = longueurHistoriqueResultat ; i>=0; i--) {

					if(historiqueResultat[i][0] == nombreDeChiffrePresent && historiqueResultat[i][1] == nombreDeChiffrePlace - 1) {
						ligne1 = i;
					}
					if(historiqueResultat[i][0] == nombreDeChiffrePresent - 1 && historiqueResultat[i][1] == nombreDeChiffrePlace) {
						ligne2 = i;
					}					
					if(ligne1 != -1 && ligne2 != -1 && ligne1 - ligne2 == 1) {
						
						int totalChiffreLigne1 = historiqueResultat[ligne1][0] + historiqueResultat[ligne1][1];
						int totalChiffreLigne2 = historiqueResultat[ligne2][0] + historiqueResultat[ligne2][1];
						
						if(totalChiffreLigne1 == totalChiffreLigne2) {

							comparerChiffrePresentMalPlace(ligne1, ligne2);
							break;
						}
					}
				}
				ligne1 = -1;
				ligne2 = -1;
				nombreDeChiffrePlace--;
			}
			nombreDeChiffrePresent--;
		}		
	}


	/*
	 * Recherche le nombre maximun de nombre pr�sent dans historique r�sultat
	 */
	private int nombreChiffrePresentMax() {

		int max = 0;
		int j = 0;

		for( int i=0; i<historiqueResultat.length; i++) {
			if(historiqueResultat[i][j] > max) {
				max = historiqueResultat[i][j];
			}
		}		
		return max;
	}
	/*
	 * Recherche le nombre maximun de nombre plac� dans historique r�sultat
	 */
	private int nombreChiffrePlaceMax() {

		int max = 0;
		int j = 1;

		for( int i=0; i<historiqueResultat.length; i++) {
			if(historiqueResultat[i][j] > max) {
				max = historiqueResultat[i][j];
			}
		}		
		return max;
	}
	
	private void comparerNouveauChiffrePresentMalPlace(int ligne1, int ligne2) {

		int[] tab1 = new int[Jeu.longueurCombinaison];
		int[] tab2 = new int[Jeu.longueurCombinaison];

		
		// Cr�ation de 2 tableaux pour comparaison
		for (int j=0; j<historiquePropositions[ligne1].length; j++) {
			tab1[j] = historiquePropositions[ligne1][j];
			tab2[j] = historiquePropositions[ligne2][j];
		}		
		for(int j=0; j<tab1.length; j++) {
			// Si un chiffre est diff�rent
			if(tab1[j] != tab2[j]) {
				
				boolean chiffre1Appartient = false;
				boolean chiffre2Appartient = false;
				int indice = -1;
				
				// Si le chiffre ligne1 appartient � la combinaison et ligne2 n'appartient pas � la combinaison alors il est mal plac�
				for(int i=0; i<lesBonsChiffres.length; i++) {
					
					if(lesBonsChiffres[i] == tab1[j]) {						
						chiffre1Appartient = true;
						indice = i;
					}
					if(lesBonsChiffres[i] == tab2[j]) {
						chiffre2Appartient = true;
					}
				}
				if(chiffre1Appartient && !chiffre2Appartient) {
					historiquePosition[indice][j] = 0;
					break;
				}
			}			
		}		
	}
	
	
	private void comparerChiffrePresentMalPlace(int ligne1, int ligne2) {
		
		int[] tab1 = new int[Jeu.longueurCombinaison];
		int[] tab2 = new int[Jeu.longueurCombinaison];

		
		// Cr�ation de 2 tableaux pour comparaison
		for (int j=0; j<historiquePropositions[ligne1].length; j++) {
			tab1[j] = historiquePropositions[ligne1][j];
			tab2[j] = historiquePropositions[ligne2][j];
		}		
		for(int j=0; j<tab1.length; j++) {
			// Si un chiffre est diff�rent
			if(tab1[j] != tab2[j]) {
				// Si le chiffre fait parti de la combinaison alors il est mal plac�
				for(int i=0; i<lesBonsChiffres.length; i++) {
					
					if(lesBonsChiffres[i] == tab1[j]) {						
						historiquePosition[i][j] = 0;
						break;
					}
				}
			}			
		}
	}
	/**
	 * Recherche les chiffres bien plac�
	 */
	private void chercherChiffreBienPlace() {
		// Cherche les chiffres bien plac�s
		unBienPlaceZeroPresent();		
		bienPlaceParComparaison();
	}

	private void unBienPlaceZeroPresent() {
		
		int nombreDeChiffrePresent = 0;
		int nombreDeChiffrePlace = 1;
			
		for(int i=0; i<historiqueResultat.length; i++) {					
			
			if(historiqueResultat[i][0] == nombreDeChiffrePresent && historiqueResultat[i][1] == nombreDeChiffrePlace ) {				
				
				for(int i2=0; i2<historiquePosition.length; i2++) {
					for(int j2=0; j2<historiquePosition[i2].length; j2++) {
						// Si le chiffre est unique alors il est bien plac�
						if(historiquePropositions[i][j2] == lesBonsChiffres[i2] && chiffreUniqueLigne(i, lesBonsChiffres[i2])) {
							historiquePosition[i2][j2] = 1;
						}
					}					
				}				
			}					
		}
	}
	/**
	 * Permet de connaitre l'unicit� d'un chiffre
	 * @param ligne
	 * @param chiffre
	 * @return
	 */
	private boolean chiffreUniqueLigne(int ligne, int chiffre) {

		int compteur = 0;
		
		for(int j=0; j<historiquePropositions[ligne].length; j++) {

			if(historiquePropositions[ligne][j] == chiffre) {
				compteur++;
			}			
		}		
		if(compteur == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Recherche des chiffres bien plac�s par comparaison
	 */
	private void bienPlaceParComparaison() {
		// comparer 2 lignes
		int nombreDeChiffrePresent = nombreChiffrePresentMax();
		int nombreDeChiffrePlace = nombreChiffrePlaceMax();
		int ligne1 = -1;
		int ligne2 = -1;

		while(nombreDeChiffrePresent > 0) {
			
			while(nombreDeChiffrePlace > 0) {
				// Si un chiffre de la combinaison �tait bien plac�
				for(int i = historiqueResultat.length-1 ; i>=0; i--) {	
					
					if(historiqueResultat[i][0] == nombreDeChiffrePresent && historiqueResultat[i][1] == nombreDeChiffrePlace ) {
						ligne1 = i;	
					}
					if(historiqueResultat[i][0] == nombreDeChiffrePresent && historiqueResultat[i][1] == nombreDeChiffrePlace - 1) {
						ligne2 = i;
					}
					if(ligne1 != -1 && ligne2 != -1 && ligne1 - ligne2 == 1) {
						comparerDeuxLignesChiffrePlace(ligne1, ligne2);
						break;
					}
				}
				ligne1 = -1;
				ligne2 = -1;
				nombreDeChiffrePlace--;
			}
			nombreDeChiffrePlace = nombreChiffrePlaceMax();
			nombreDeChiffrePresent--;
		}		
	}
	
	/* Comparaison exemple
	 * ligne1 : pr�sent 2 => plac� 1
	 * ligne2 : pr�sent 2 => plac� 0 
	 */
	private void comparerDeuxLignesChiffrePlace(int ligne1, int ligne2) {

		int[] tab1 = new int[Jeu.longueurCombinaison];
		int[] tab2 = new int[Jeu.longueurCombinaison];
		// Cr�ation de 2 tableaux pour comparaison
		for (int j=0; j<historiquePropositions[ligne1].length; j++) {
			tab1[j] = historiquePropositions[ligne1][j];
			tab2[j] = historiquePropositions[ligne2][j];
		}
		
		for(int j=0; j<tab1.length; j++) {
			// Si un chiffre est diff�rent
			if(tab1[j] != tab2[j]) {
				// Si ce chiffre fait parti de la combinaison et unique alors il est bien plac�
				for(int i=0; i<lesBonsChiffres.length; i++) {
					
					if(lesBonsChiffres[i] == tab1[j] && chiffreUniqueLigne(ligne1, lesBonsChiffres[i])) {						
						historiquePosition[i][j] = 1;						
						break;
					}
				}
			}			
		}		
	}
	/**
	 * Permet de mettre � jour les lignes et colonnes correspondant aux chiffres bien plac�s
	 */
	private void miseAJourHistoriquePosition() {
		
		for(int i=0; i<historiquePosition.length; i++) {
			for(int j=0; j<historiquePosition[i].length; j++) {
				
				if(historiquePosition[i][j] == 1) {
					
					mettreAjourPosition(i, j);					
				}
			}
		}		
	}
	/**
	 * Mise � jour des lignes et des colonnes en fonction des chiffres trouv�s
	 * @param ligne
	 * @param colonne
	 */
	private void mettreAjourPosition(int ligne, int colonne) {

		// Mise � jour des lignes
		for(int j=0; j<historiquePosition[ligne].length; j++) {
			
			if(j != colonne) {
				historiquePosition[ligne][j] = 0;
			}			
		}
		// Mise � jour des colonnes
		for(int i=0; i<historiquePosition.length; i++) {
			
			if(i != ligne) {
				historiquePosition[i][colonne] = 0;
			}			
		}		
	}
	
	/*
	 *  Mise � jour de l'indice disponible
	 *  Affectation de la valeur trouv�e au tableau de proposition
	 */
	private void initialiserProposition() {

		Arrays.fill(indiceDisponible, true);
		
		for(int i=0; i<historiquePosition.length; i++) {
			for(int j=0; j<historiquePosition[i].length; j++) {
				
				if(historiquePosition[i][j] == 1) {
					indiceDisponible[j] = false;
					propositionTab[j] = Character.forDigit(lesBonsChiffres[i], 10);
 				}
			}
		}		
	}
	
	// Proposition d'une combinaison apr�s analyse 
	private void nouvelleProposition() {
		initialiserProposition();
		propositionHistorique();

		// Si la proposition existe
		if(propositionExiste()) {
			
			boolean test = true;

			do {
				// Si les indices sont � la fin de la proposition on repart en arri�re
				if(indice1 == propositionTab.length - 2 && indice2 == propositionTab.length -1) {
					avance = false;
				}
				// Recherche d'une proposition disponible dans le sens de lecture
				if(avance) {
					logger.debug("Proposition diff�rente dans le sens de lecture");
					test = chercherChiffreSuivantDisponible(test);
				}
				// Recherche d'une proposition disponible dans le sens inverse
				else {
					logger.debug("Proposition diff�rente dans le sens inverse de lecture");
					test = chercherChiffrePrecedantDisponible(test);
				}
				
			}
			while(test);			
		}
		proposition = new String(propositionTab);
		ajouterProposition();
		numeroCoup++;		
	}
	/**
	 * G�n�re une propostion en fonction du tableau des positions disponibles
	 */
	private void propositionHistorique() {
		boolean chiffrePositionne = false;		
		
		for(int i=0; i< historiquePosition.length - 1; i++) {
			for(int j=0; j<historiquePosition[i].length; j++) {
				// Si La place est disponible et que le chiffre suivant ne se positionne pas dessu alors on affecte le chiffre
				if(historiquePosition[i][j] == 2 && historiquePosition[i + 1][j] == 0 && indiceDisponible[j]) {
					propositionTab[j] = Character.forDigit(lesBonsChiffres[i], 10);
					indiceDisponible[j] = false;
					chiffrePositionne = true;					
					break;
				}
			}
			// Si le chiffre n'est pas positionn� on prend la premi�re position ok et indice disponible
			if(chiffrePositionne == false) {
				for(int j=0; j<historiquePosition[i].length; j++) {
					
					if(historiquePosition[i][j] == 2 && indiceDisponible[j] == true) {
						propositionTab[j] = Character.forDigit(lesBonsChiffres[i], 10);
						indiceDisponible[j] = false;
						break;
					}
				}
			}
			chiffrePositionne = false;
		}
		// On affecte le dernier chiffre au dernier indice disponible
		for(int i=0; i<indiceDisponible.length; i++) {
			if(indiceDisponible[i]) {
				propositionTab[i] = Character.forDigit(lesBonsChiffres[lesBonsChiffres.length - 1], 10);
				break;
			}
		}
	}
	/**
	 * V�rifie si la proposition existe
	 * @return
	 */
	private boolean propositionExiste() {		
		
		// Comparaison nouvelle proposition avec les anciennes propositions
		boolean propositionEgale = true;
		
		for(int i=0; i<historiquePropositions.length; i++) {
			
			propositionEgale = true;
			
			for(int j=0; j<historiquePropositions[i].length; j++) {

				
				if(historiquePropositions[i][j] == Character.digit(propositionTab[j], 10)) {
					propositionEgale = propositionEgale & true;
				}
				else {					
					propositionEgale = propositionEgale & false;
				}
			}			
			if(propositionEgale) {
				
				return true;
			}			
		}		
		return propositionEgale;
	}
	// Dans le sens de lecture 
	private boolean chercherChiffreSuivantDisponible(boolean test) {
		
		while(indice1 < propositionTab.length -2 && test) {
			
			indice2 = indice1 + 1;
			
			while(indice2 < propositionTab.length -1 && test) {
				
				int chiffre1 = Character.digit(propositionTab[indice1], 10);
				int chiffre2 = Character.digit(propositionTab[indice2], 10);
				
				// V�rification que les nouveaux emplacement sont disponibles
				if(verifierEtatChiffre(chiffre1, indice2) && verifierEtatChiffre(chiffre2, indice1)) {
					
					inverserChiffre(indice1, indice2);
				
					if(propositionExiste()) {
						test = true;								
					}
					else {
						test = false;								
						break;
					}
				}						
				indice2++;
			}						
			indice1++;
		}
		return test;
	}
	// Dans le sens inverse de lecture
	private boolean chercherChiffrePrecedantDisponible(boolean test) {
		indice1 = Jeu.longueurCombinaison - 1;
		indice2 = Jeu.longueurCombinaison - 2;
		
		while(indice1 > 0 && test) {
			
			indice2 = indice1 - 1;
			
			while(indice2 >= 0 && test) {
				int chiffre1 = Character.digit(propositionTab[indice1], 10);
				int chiffre2 = Character.digit(propositionTab[indice2], 10);
				
				// V�rification que les nouveaux emplacement sont disponibles
				if(verifierEtatChiffre(chiffre1, indice2) && verifierEtatChiffre(chiffre2, indice1)) {
					inverserChiffre(indice1, indice2);
					
					if(propositionExiste()) {
						test = true;								
					}
					else {
						test = false;
						indice1 = 0;
						indice2 = 1;
						break;
					}
				}						
				indice2--;
			}					
			indice1--;
		}
		return test;
	}
	/**
	 * V�rifie si la position d'un chiffre est disponible
	 * @param chiffre
	 * @param position
	 * @return
	 */
	private boolean verifierEtatChiffre(int chiffre, int position) {

		for(int i=0; i<lesBonsChiffres.length; i++) {
			if(lesBonsChiffres[i] == chiffre && historiquePosition[i][position] == 2) {
				return true;
			}
		}
		return false;		
	}
	/**
	 * Inverse deux chiffres
	 * @param indiceChiffre1
	 * @param indiceChiffre2
	 */
	private void inverserChiffre(int indiceChiffre1, int indiceChiffre2) {
		char temp = propositionTab[indiceChiffre1];
		propositionTab[indiceChiffre1] = propositionTab[indiceChiffre2];
		propositionTab[indiceChiffre2] = temp;
	}
}
