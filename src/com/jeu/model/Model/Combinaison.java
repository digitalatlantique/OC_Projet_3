package com.jeu.model.Model;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.view.View.VueConsole;

/**
 * 
 */
public class Combinaison extends Jeu {
	
	 /**
     * Default constructor
     */
    public Combinaison() {    	

    }
    
    /**
     * 
     */
    public void modeChallenger() { 
    	
    	VueConsole vue = new VueConsole();
    	
    	    	
    	initialiser();
    	vue.afficherJeuCombinaisonIntro();    	
    	
    	do {
    		partie = jouer();
    	}
    	while(partie);
    	

    }

    /**
     * 
     */
    public void modeDefenseur() {
        // TODO implement here
    }

	@Override
	public void duel() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initialiser() {
		
    	sc = new Scanner(System.in);
    	partie = true;
		combinaisonSecrete = genererCombinaison(longueurCombinaison);
		combinaisonSecreteTab = this.combinaisonSecrete.toCharArray();
		combinaisonReponseTab = new char[longueurCombinaison];
		combinaisonTest = new boolean[longueurCombinaison];
		
	}
	
	@Override
	public boolean jouer() {
		
		boolean test;
		VueConsole vue = new VueConsole();
		
    	vue.afficherMessage("Choisir une combinaison à : " + longueurCombinaison + " chiffres.");
    	
    	do {
    		
    		saisie = sc.next();
    		try {
    			test = testerCombinaisonSaisie(saisie, longueurCombinaison);
    		}
    		catch(Exception e) {
    			test = false;
    			System.err.println(e.getMessage());
    			vue.afficherMessage("Choisir une combinaison à : " + longueurCombinaison + " chiffres.");
    		}    		
    	}
    	while(!test);
		
    	verifierResultat();
    	
    	if(verifierVictoire()) {
    		vue.afficherMessage("gagné");
    		vue.afficherMessage(combinaisonSecrete);
    		return false;
    	}
    	else if (nombreEssais == 1) {
    		vue.afficherMessage("Perdu");
    		vue.afficherMessage("La combinaison est : " + combinaisonSecrete);
    		return false;
    	}
    	
    	else {
    		nombreEssais--;
    		vue.afficherMessage("il vous reste : " + nombreEssais + " essai(s)");
    		vue.afficherMessage("Indice : " + new String(combinaisonReponseTab));
    		
    		return true;
    	}
    	
		
		
	}

	@Override
	public String genererCombinaison(int longueur) {
		
		System.out.println("Génère la combi");
		String combinaison = "";
		int temp;
		
		for(int i=0; i<longueur; i++) {
			
			temp = genererNombre(0, 9);
			combinaison += String.valueOf(temp);
		}
		System.out.println("la combi : " + combinaison);
		return combinaison;
	}



	@Override
	public void verifierResultat() {
		
		int resultat;
		
		for(int i=0; i<saisie.length(); i++) {
			
			resultat = Character.compare(saisie.charAt(i), combinaisonSecreteTab[i]);
			
			if(resultat < 0) {
				combinaisonReponseTab[i] = '+';
				combinaisonTest[i] = false;
			}
			
			else if (resultat > 0){
				combinaisonReponseTab[i] = '-';
				combinaisonTest[i] = false;
			}
			else {
				combinaisonReponseTab[i] = '=';
				combinaisonTest[i] = true;
			}
		}
	}

	@Override
	public boolean verifierVictoire() {
		
		boolean resultat = true;
		
		for(int i=0; i<combinaisonTest.length; i++) {
			resultat = resultat && combinaisonTest[i];
		}
		return resultat;
	}
	
    /**
     * Permet de générer un nombre compris dans un interval
     * @param min, le nombre minimum
     * @param max, le nombre maximum
     * @return un nombre aléatoire
     */
    public int genererNombre(int min, int max) {
    	int aleatoire = (int) (Math.random() * (max - min))+min;
    	return aleatoire;
    }
    
    public boolean testerCombinaisonSaisie(String string, int longueur) throws Exception {			
    	 
		Pattern pattern = Pattern.compile("[0-9]{" + longueur + "}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte !");
		}
		return resultat;    	
    }

}