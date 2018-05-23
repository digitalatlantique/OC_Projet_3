package com.jeu.model;



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
    protected boolean[] combinaisonTest;


    public static int longueurCombinaison = 4;
    
    public static int nombreEssais = 10;
    
    public abstract void initialiser();
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
	public boolean[] getCombinaisonTest() {
		return combinaisonTest;
	}    

}