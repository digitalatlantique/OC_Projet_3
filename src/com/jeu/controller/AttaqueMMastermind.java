package com.jeu.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.jeu.model.Jeu;

public class AttaqueMMastermind extends Attaque {
	
	private boolean premierCoup = true;
	private int trouve = 0;
	private int numeroCoup = 0;
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
		
		// On assigne tout les positions à 2 (position incertaine)
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
			// Sinon 
			else {
				
				afficherHistoriquePropositions();
				afficherHistoriqueReponse();
				
				analyserResultat();
				
				afficherHistoriquePosition();
				
				miseAJourHistoriquePosition();				
				initialiserProposition();				
				nouvelleProposition();
				
				return proposition;				
			}			
			return proposition;				
		}
	}


	// Proposition d'une combinaison après analyse 
	private void nouvelleProposition() {
		
		boolean chiffrePositionne = false;		
		
		for(int i=0; i< historiquePosition.length - 1; i++) {
			for(int j=0; j<historiquePosition[i].length; j++) {
				
				if(historiquePosition[i][j] == 2 && historiquePosition[i + 1][j] == 0 && indiceDisponible[j] == true) {
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
			}
		}
		proposition = new String(propositionTab);
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

	private void miseAJourHistoriquePosition() {
		
		for(int i=0; i<historiquePosition.length; i++) {
			for(int j=0; j<historiquePosition[i].length; j++) {
				
				if(historiquePosition[i][j] == 1) {
					
					mettreAjourPosition(i, j);					
				}
			}
		}		
	}
	
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

	private void analyserResultat() {
		
		chercherChiffreMalPlace();
		
		chercherChiffreBienPlace();
		
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
	/* Comparaison 	ligne1 : présent 2 => placé 1
	 * 				ligne2 : présent 2 => placé 0 
	 */
	private void comparerDeuxLignes(int ligne1, int ligne2) {

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

	private void chercherChiffreBienPlace() {
		// Cherche les chiffres bien placés
		unBienPlaceZeroPresent();
		
		bienPlaceParComparaison();		

	}

	private void bienPlaceParComparaison() {
		// comparer 2 lignes
		int nombreDeBonChiffrePresent = Jeu.longueurCombinaison - 1;
		int ligne1 = -1;
		int ligne2 = -1;
		
		while(nombreDeBonChiffrePresent > 0) {
			for(int i = historiqueResultat.length-1 ; i>=0; i--) {

				if(historiqueResultat[i][0] == nombreDeBonChiffrePresent && historiqueResultat[i][1] == 1) {
					ligne1 = i;
				}
				if(historiqueResultat[i][0] == nombreDeBonChiffrePresent && historiqueResultat[i][1] == 0) {
					ligne2 = i;
				}
				if(ligne1 != -1 && ligne2 != -1) {
					comparerDeuxLignes(ligne1, ligne2);
					break;
				}
			}
			nombreDeBonChiffrePresent--;
		}
	}

	private void unBienPlaceZeroPresent() {
		
		int nombreDeBonChiffrePlace = 1;
			
		for(int i=0; i<historiqueResultat.length; i++) {					
			
			if(nombreDeBonChiffrePlace == historiqueResultat[i][1] && historiqueResultat[i][0] == 0) {				
				
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

	private void chercherChiffreMalPlace() {
		int nombreDeBonChiffrePresent = Jeu.longueurCombinaison;
		int indice = -1;
		
		// Recherche des bons chiffres mal placé (placé = 0)
		while(nombreDeBonChiffrePresent>0) {
			
			for(int i=0; i<historiqueResultat.length; i++) {					
				// Colonne 0 = chiffre présent et Colonne 1 = chiffre placé
				if(nombreDeBonChiffrePresent == historiqueResultat[i][0] && historiqueResultat[i][1] == 0) {
					
					indice = i;					
				}							
			}
			if(indice != -1) {
				
				for(int i=0; i<historiquePosition.length; i++) {
					for (int j=0; j<historiquePosition[0].length; j++) {
						// Si les bon chiffres sont mal placés, 
						if(historiquePropositions[indice][j] == lesBonsChiffres[i]) {
							historiquePosition[i][j] = 0;
						}
					}
				}				
			}
			indice = -1;			
			nombreDeBonChiffrePresent--;
		}
	}
	
	private String premierCoup() {
		
		for(int i=0; i<Jeu.longueurCombinaison; i++) {
			proposition += 0;
			lesBonsChiffres[i] = 0;
			historiquePropositions[numeroCoup][i] = 0;
			
		}
		numeroCoup++;
		
		propositionTab = proposition.toCharArray();
		return proposition;
		
	}
	
	private void proposerChiffre() {
		int temporaire;
		for (int i = trouve; i < propositionTab.length; i++) {

			temporaire = Character.digit(propositionTab[i], 10);
			temporaire += 1;
			lesBonsChiffres[i] = temporaire;
			propositionTab[i] = Character.forDigit(temporaire, 10);
		}
		
		for(int j = 0; j < propositionTab.length; j++ ) {
			historiquePropositions[numeroCoup][j] = Character.digit(propositionTab[j], 10);
		}
		numeroCoup++;
		
		proposition = new String(propositionTab);
	}
	
	private void afficherHistoriquePropositions() {
		System.out.println("Affichage historique propositions");
		for(int i = 0; i<historiquePropositions.length; i++) {
			for(int j=0; j<historiquePropositions[0].length; j++) {
				System.out.print(historiquePropositions[i][j]);
			}
			System.out.println();
		}
		System.out.println("------------------------------------");
	}

	private void afficherHistoriqueReponse() {
		System.out.println("Affichage historique réponses");
		for(int i=0; i<historiqueResultat.length; i++) {
			for(int j=0; j<historiqueResultat[0].length; j++){
				System.out.print(historiqueResultat[i][j]);
			}
			System.out.println();
		}
		System.out.println("------------------------------------");
	}

	private void afficherHistoriquePosition() {
		System.out.println("Affichage historique position");
		for (int i = 0; i < historiquePosition.length; i++) {
			
			for(int j=0; j<historiquePosition[i].length; j++) {
				System.out.print(historiquePosition[i][j]);
			}
			System.out.println();
		}
		System.out.println("------------------------------------");
	}
	

}
