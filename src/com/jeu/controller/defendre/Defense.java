package com.jeu.controller.defendre;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.model.Jeu;
import com.jeu.view.VueConsole;
/**
 * Cette classe abstraite permet de regrouper les propriétés du comportement défendre
 * @author Workstation
 *
 */
public abstract class Defense implements Defendre{
	
	protected Scanner sc = new Scanner(System.in);
	protected VueConsole vue = new VueConsole();
	protected String saisie;	
	protected String reponse;
	protected char[] reponseTab;
	public static int nombreDeChiffre = 10;
	
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
