package com.jeu.controller.defendre;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Jeu;
/**
 * Cette classe permet de donner des indications � l'attaquant pour le jeu combinaison+-
 * @author Workstation
 *
 */
public class DefenseHumaineCombinaison extends Defense {
	
	private int longueur = Jeu.longueurCombinaison;

	@Override
	public void analyser(Jeu jeu, String proposition) {
		
		boolean test;
		String cle = new String("combinaisonReponse");

		vue.afficherMessage("T800 propose : " + proposition);		
		vue.afficherMessage("Indiquer � l'aide de + - ou = la bonne combinaison d'une longueur de " + Jeu.longueurCombinaison + " �l�ments");
		
		do {
			saisie = sc.next();
			
			try {
				test = testerReponseSaisie(saisie, Jeu.longueurCombinaison);
			} catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMessage("Indiquer � l'aide de + - ou = la bonne combinaison d'une longueur de " + Jeu.longueurCombinaison + " �l�ments");
			}			
		}
		while(!test);
		
		reponse = saisie;
		reponseTab = reponse.toCharArray();
		jeu.setCombinaisonReponseTab(reponseTab);
		jeu.getCombinaisonReponseMap().put(cle, reponse);
		
		for(int i=0; i<reponseTab.length; i++) {
			if(reponseTab[i] == '=') {
				jeu.getCombinaisonTest()[i] = true;
			}
			else {
				jeu.getCombinaisonTest()[i] = false;
			}
		}
		
	}

	@Override
	public String genererCombinaison() {
		
		boolean test;
				
		vue.afficherMessage("Faire deviner une combinaison compos�e de " + longueur + " chiffres");
		
		do {			
			saisie = sc.next();
			
			try {
				test = testerCombinaisonSaisie(saisie, longueur);
			} 
			catch (Exception e) {
        		test = false;
        		System.out.println(e.getMessage());
        		vue.afficherMessage("Faire deviner une combinaison valide, compos�e de " + longueur + " chiffres");
			}
			
		}
		while(!test);
		
		return saisie;
	}
	
    public boolean testerReponseSaisie(String string, int longueur) throws Exception {			
    	 
		Pattern pattern = Pattern.compile("[+-=]{" + longueur + "}");
		Matcher matcher = pattern.matcher(string);
		boolean resultat = matcher.matches();
		
		if(!resultat) {
			throw new Exception("Saisie incorrecte !");
		}
		return resultat;    	
    }

}
