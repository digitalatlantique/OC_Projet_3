package com.jeu.model;

import java.util.HashMap;

/**
 * 
 */
public abstract class Jeu {
	
    /**
     * 
     */
    protected String combinaisonSecrete;

    /**
     * 
     */
    protected String combinaisonReponse;

    /**
     * 
     */
    protected char[] combinaisonSecreteTab;
    
    /**
     * 
     */
    protected char[] combinaisonReponseTab;
    
    /**
     * 
     */
    protected HashMap<String, String> combinaisonReponseMap;

    /**
     * 
     */
    protected boolean[] combinaisonTest;


    public static int longueurCombinaison = 4;
    
    public static int nombreEssais = 10;
    
    public abstract void initialiser(String combinaison);
    public abstract String genererCombinaison(int longueur);
    
    public String getCombinaisonSecrete() {
		return combinaisonSecrete;
	}
	public char[] getCombinaisonReponseTab() {
		return combinaisonReponseTab;
	}

	public char[] getCombinaisonSecreteTab() {
		return combinaisonSecreteTab;
	}
	
	
	public void setCombinaisonReponseTab(char[] combinaisonReponseTab) {
		this.combinaisonReponseTab = combinaisonReponseTab;
	}
	public HashMap getCombinaisonReponseMap() {
		return combinaisonReponseMap;
	}
	public boolean[] getCombinaisonTest() {
		return combinaisonTest;
	}    

}