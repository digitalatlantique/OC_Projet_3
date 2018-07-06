package com.jeu.controller.attaquer;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jeu.controller.ControlleurJeu;
import com.jeu.model.Jeu;

public class AttaqueMMastermind extends Attaque {
	
	private Logger logger = LogManager.getLogger(AttaqueMMastermind.class);
	private boolean premierCoup = true;
	private boolean avance = true;
	private int trouve = 0;
	private int numeroCoup = 0;
	private int indice1 = 0;
	private int indice2 = 0;
	private boolean[] indiceDisponible;
	private int[] lesBonsChiffres;
	private int[][] historiquePropositions;
	private int[][] historiqueResultat;
	private int[][] historiquePosition;
	
	public AttaqueMMastermind() {
		
		historiquePropositions = new int[Jeu.nombreEssais][Jeu.longueurCombinaison];
		historiqueResultat = new int[Jeu.nombreEssais][2];
		indiceDisponible = new boolean[Jeu.longueurCombinaison];
		Arrays.fill(indiceDisponible, true);
		historiquePosition = new int[Jeu.longueurCombinaison][Jeu.longueurCombinaison];
		
		// On assigne toutes les positions à 2 (position incertaine)
		for(int i=0; i<historiquePosition.length;i++) {
			for (int j=0; j<historiquePosition[0].length; j++) {
				historiquePosition[i][j] = 2;
			}
		}
		lesBonsChiffres = new int[Jeu.longueurCombinaison];
	}
	
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
			
			/* Si il reste des chiffres à trouver
			 * On propose une série reprenant la combinaison précédente +1 à chaque chiffre restant à trouvé
			 */
			if(trouve < Jeu.longueurCombinaison) {
				
				proposerChiffre();
			}
			// Si tout les chiffres sont connus, on analyse les coups précédants puis on fait une nouvelle proposition
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
	 * Créer une première combinaison composé de 0 étendu de la longueur de la combinaison 
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
	 * Propose une suite de chiffre conservant les bon chiffres de la combinaison et ajoutant 1 pour les chiffre à deviner
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

	private void ajouterProposition() {		
		int longueurPropositionTab = propositionTab.length;
		for(int j = 0; j < longueurPropositionTab; j++ ) {
			historiquePropositions[numeroCoup][j] = Character.digit(propositionTab[j], 10);
		}		
	}
	
	/**
	 * Analyse les chiffres mal placés et correctement bien placé
	 */
	private void analyserResultat() {
		
		chercherChiffreMalPlace();		
		chercherChiffreBienPlace();
		
	}
	
	private void chercherChiffreMalPlace() {
		int nombreDeChiffrePresent = nombreChiffrePresentMax();
		int nombreDeChiffrePlace = 0;
		int indice = -1;
		
		// Recherche des bons chiffres mal placé (placé = 0)
		while(nombreDeChiffrePresent>0) {
			int longueurHistoriqueResultat = historiqueResultat.length;
			int longueurHistoriquePositionLigne = historiquePosition.length;
			int longueurHistoriquePositionColonne = historiquePosition[0].length;
									
			for(int i=0; i<longueurHistoriqueResultat; i++) {					
				// Colonne 0 = chiffre présent & Colonne 1 = chiffre placé
				if(nombreDeChiffrePresent == historiqueResultat[i][0] && historiqueResultat[i][1] == nombreDeChiffrePlace) {

					for(int k=0; k<longueurHistoriquePositionLigne; k++) {
						for (int l=0; l<longueurHistoriquePositionColonne; l++) {
							// Si les bon chiffres sont mal placés, 
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
		 * Recherche d'un bon chiffre présent et mal placé
		 * En comparant (présent à présent - 1) et (placé = 1)
		 */
		nombreDeChiffrePresent = nombreChiffrePresentMax();
		nombreDeChiffrePlace = nombreChiffrePlaceMax();
		int ligne1 = -1;
		int ligne2 = -1;
		
		while(nombreDeChiffrePresent > 0) {
			
			while(nombreDeChiffrePlace > 0) {
				int longueurHistoriqueResultat =  historiqueResultat.length-1;
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
		 * ligne1 (présent = 3, place = 1)
		 * ligne2 (présent = 2, place = 2)
		 */
		nombreDeChiffrePresent = nombreChiffrePresentMax();
		nombreDeChiffrePlace = nombreChiffrePlaceMax();
				
		while(nombreDeChiffrePresent > 0) {
			
			while(nombreDeChiffrePlace > 0) {
				int longueurHistoriqueResultat =  historiqueResultat.length-1;
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
	 * Recherche le nombre maximun de nombre présent dans historique résultat
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
	 * Recherche le nombre maximun de nombre placé dans historique résultat
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

		
		// Création de 2 tableaux pour comparaison
		for (int j=0; j<historiquePropositions[ligne1].length; j++) {
			tab1[j] = historiquePropositions[ligne1][j];
			tab2[j] = historiquePropositions[ligne2][j];
		}		
		for(int j=0; j<tab1.length; j++) {
			// Si un chiffre est différent
			if(tab1[j] != tab2[j]) {
				
				boolean chiffre1Appartient = false;
				boolean chiffre2Appartient = false;
				int indice = -1;
				
				// Si le chiffre ligne1 appartient à la combinaison et ligne2 n'appartient pas à la combinaison alors il est mal placé
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
	
	// TODO variable initialisation boucle
	private void comparerChiffrePresentMalPlace(int ligne1, int ligne2) {
		
		int[] tab1 = new int[Jeu.longueurCombinaison];
		int[] tab2 = new int[Jeu.longueurCombinaison];

		
		// Création de 2 tableaux pour comparaison
		for (int j=0; j<historiquePropositions[ligne1].length; j++) {
			tab1[j] = historiquePropositions[ligne1][j];
			tab2[j] = historiquePropositions[ligne2][j];
		}		
		for(int j=0; j<tab1.length; j++) {
			// Si un chiffre est différent
			if(tab1[j] != tab2[j]) {
				// Si le chiffre fait parti de la combinaison alors il est mal placé
				for(int i=0; i<lesBonsChiffres.length; i++) {
					
					if(lesBonsChiffres[i] == tab1[j]) {						
						historiquePosition[i][j] = 0;
						break;
					}
				}
			}			
		}
	}
	
	private void chercherChiffreBienPlace() {
		// Cherche les chiffres bien placés
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
						
						if(historiquePropositions[i][j2] == lesBonsChiffres[i2] && chiffreUniqueLigne(i, lesBonsChiffres[i2])) {
							historiquePosition[i2][j2] = 1;
						}
					}					
				}				
			}					
		}
	}	
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
	private void bienPlaceParComparaison() {
		// comparer 2 lignes
		int nombreDeChiffrePresent = nombreChiffrePresentMax();
		int nombreDeChiffrePlace = nombreChiffrePlaceMax();
		int ligne1 = -1;
		int ligne2 = -1;

		while(nombreDeChiffrePresent > 0) {
			
			while(nombreDeChiffrePlace > 0) {
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
	 * ligne1 : présent 2 => placé 1
	 * ligne2 : présent 2 => placé 0 
	 */
	private void comparerDeuxLignesChiffrePlace(int ligne1, int ligne2) {

		int[] tab1 = new int[Jeu.longueurCombinaison];
		int[] tab2 = new int[Jeu.longueurCombinaison];
		// Création de 2 tableaux pour comparaison
		for (int j=0; j<historiquePropositions[ligne1].length; j++) {
			tab1[j] = historiquePropositions[ligne1][j];
			tab2[j] = historiquePropositions[ligne2][j];
		}
		
		for(int j=0; j<tab1.length; j++) {
			// Si un chiffre est différent
			if(tab1[j] != tab2[j]) {
				// Si ce chiffre fait parti de la combinaison et unique alors il est bien placé
				for(int i=0; i<lesBonsChiffres.length; i++) {
					
					if(lesBonsChiffres[i] == tab1[j] && chiffreUniqueLigne(ligne1, lesBonsChiffres[i])) {						
						historiquePosition[i][j] = 1;						
						break;
					}
				}
			}			
		}		
	}
	
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
	 * Mise à jour des lignes et des colonnes en fonction des chiffres trouvés
	 * @param ligne
	 * @param colonne
	 */
	private void mettreAjourPosition(int ligne, int colonne) {

		// Mise à jour des lignes
		for(int j=0; j<historiquePosition[ligne].length; j++) {
			
			if(j != colonne) {
				historiquePosition[ligne][j] = 0;
			}			
		}
		// Mise à jour des colonnes
		for(int i=0; i<historiquePosition.length; i++) {
			
			if(i != ligne) {
				historiquePosition[i][colonne] = 0;
			}			
		}		
	}
	
	/*
	 *  Mise à jour de l'indice disponible
	 *  Affectation de la valeur trouvée au tableau de proposition
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
	
	// Proposition d'une combinaison après analyse 
	private void nouvelleProposition() {
		initialiserProposition();
		propositionHistorique();

		// Si la proposition existe
		if(propositionExiste()) {
			
			boolean test = true;

			do {
				
				if(indice1 == propositionTab.length - 2 && indice2 == propositionTab.length -1) {
					avance = false;
				}
				
				if(avance) {
					logger.debug("Proposition différente dans le sens de lecture");
					test = chercherChiffreSuivantDisponible(test);
				}
				else {
					logger.debug("Proposition différente dans le sens inverse de lecture");
					test = chercherChiffrePrecedantDisponible(test);
				}
				
			}
			while(test);			
		}
		proposition = new String(propositionTab);
		ajouterProposition();
		numeroCoup++;		
	}

	private void propositionHistorique() {
		boolean chiffrePositionne = false;		
		
		for(int i=0; i< historiquePosition.length - 1; i++) {
			for(int j=0; j<historiquePosition[i].length; j++) {
				
				if(historiquePosition[i][j] == 2 && historiquePosition[i + 1][j] == 0 && indiceDisponible[j]) {
					propositionTab[j] = Character.forDigit(lesBonsChiffres[i], 10);
					indiceDisponible[j] = false;
					chiffrePositionne = true;					
					break;
				}
			}
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
		for(int i=0; i<indiceDisponible.length; i++) {
			if(indiceDisponible[i]) {
				propositionTab[i] = Character.forDigit(lesBonsChiffres[lesBonsChiffres.length - 1], 10);
				break;
			}
		}
	}
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

	private boolean chercherChiffreSuivantDisponible(boolean test) {
		
		while(indice1 < propositionTab.length -2 && test) {
			
			indice2 = indice1 + 1;
			
			while(indice2 < propositionTab.length -1 && test) {
				
				int chiffre1 = Character.digit(propositionTab[indice1], 10);
				int chiffre2 = Character.digit(propositionTab[indice2], 10);
				
				// Vérification que les nouveaux emplacement sont disponibles
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

	private boolean chercherChiffrePrecedantDisponible(boolean test) {
		indice1 = Jeu.longueurCombinaison - 1;
		indice2 = Jeu.longueurCombinaison - 2;
		
		while(indice1 > 0 && test) {
			
			indice2 = indice1 - 1;
			
			while(indice2 >= 0 && test) {
				int chiffre1 = Character.digit(propositionTab[indice1], 10);
				int chiffre2 = Character.digit(propositionTab[indice2], 10);
				
				// Vérification que les nouveaux emplacement sont disponibles
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
	private boolean verifierEtatChiffre(int chiffre, int position) {

		for(int i=0; i<lesBonsChiffres.length; i++) {
			if(lesBonsChiffres[i] == chiffre && historiquePosition[i][position] == 2) {
				return true;
			}
		}
		return false;		
	}
	private void inverserChiffre(int indiceChiffre1, int indiceChiffre2) {
		char temp = propositionTab[indiceChiffre1];
		propositionTab[indiceChiffre1] = propositionTab[indiceChiffre2];
		propositionTab[indiceChiffre2] = temp;
	}
}
