package com.jeu.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Jeu;
import com.jeu.view.VueConsole;

public abstract class Defense implements Defendre{
	
	protected Scanner sc = new Scanner(System.in);
	protected VueConsole vue = new VueConsole();
	protected int longueur = Jeu.longueurCombinaison;
	protected String saisie;	
	protected String reponse;
	protected char[] reponseTab;
	
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
