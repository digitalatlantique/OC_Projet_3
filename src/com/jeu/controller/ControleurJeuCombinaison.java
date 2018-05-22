package com.jeu.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Combinaison;
import com.jeu.model.Jeu;

public class ControleurJeuCombinaison extends ControleurJeu {

	public ControleurJeuCombinaison() {

		this.jeuCombinaison = new Combinaison();
	}
	
	private Jeu jeuCombinaison;

	@Override
	public void modeChallenger() {

		jeuCombinaison.initialiser();
		partie = true;
		
		vue.afficherJeuCombinaisonIntro(Jeu.nombreEssais); 
		
    	do {
    		partie = jouer();
    	}
    	while(partie);
		
	}

	@Override
	public void modeDefenseur() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void duel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean jouer() {

		boolean test;
		
		if(ChoixModeSession) {
			vue.afficherMessage("Mode Développeur, la solution est : " + jeuCombinaison.getCombinaisonSecrete());
		}
		
    	vue.afficherMessage("Choisir une combinaison à : " + Jeu.longueurCombinaison + " chiffres.");
    	
    	do {
    		
    		saisie = sc.next();
    		try {
    			test = testerCombinaisonSaisie(saisie, Jeu.longueurCombinaison);
    		}
    		catch(Exception e) {
    			test = false;
    			System.out.println(e.getMessage());
    			vue.afficherMessage("Choisir une combinaison à : " + Jeu.longueurCombinaison + " chiffres.");
    		}    		
    	}
    	while(!test);
		
    	verifierResultat();
    	
    	if(verifierVictoire()) {
    		vue.afficherMessage("gagné");
    		vue.afficherMessage(jeuCombinaison.getCombinaisonSecrete());
    		return false;
    	}
    	else if (Jeu.nombreEssais == 1) {
    		vue.afficherMessage("Perdu");
    		vue.afficherMessage("La combinaison est : " + jeuCombinaison.getCombinaisonSecrete());
    		return false;
    	}
    	
    	else {
    		Jeu.nombreEssais--;
    		vue.afficherMessage("il vous reste : " + Jeu.nombreEssais + " essai(s)");
    		vue.afficherMessage("Indice : " + new String(jeuCombinaison.getCombinaisonReponseTab()));
    		
    		return true;
    	}
    	
	}

	@Override
	public void verifierResultat() {
		
		int resultat;
		
		for(int i=0; i<saisie.length(); i++) {
			
			resultat = Character.compare(saisie.charAt(i), jeuCombinaison.getCombinaisonSecreteTab()[i]);
			
			if(resultat < 0) {
				jeuCombinaison.getCombinaisonReponseTab()[i] = '+';
				jeuCombinaison.getCombinaisonTest()[i] = false;
			}
			
			else if (resultat > 0){
				jeuCombinaison.getCombinaisonReponseTab()[i] = '-';
				jeuCombinaison.getCombinaisonTest()[i] = false;
			}
			else {
				jeuCombinaison.getCombinaisonReponseTab()[i] = '=';
				jeuCombinaison.getCombinaisonTest()[i] = true;
			}
		}
	}

	@Override
	public boolean verifierVictoire() {
		
		boolean resultat = true;
		
		for(int i=0; i<jeuCombinaison.getCombinaisonTest().length; i++) {
			resultat = resultat && jeuCombinaison.getCombinaisonTest()[i];
		}
		return resultat;
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
