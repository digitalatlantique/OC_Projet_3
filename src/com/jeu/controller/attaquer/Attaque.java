package com.jeu.controller.attaquer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jeu.view.VueConsole;

public abstract class Attaque implements Attaquer {
	
	protected VueConsole vue = new VueConsole();
	protected Scanner sc = new Scanner(System.in);
	protected String saisie;
	
	protected String proposition = "";
	protected char[] propositionTab;
	
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
