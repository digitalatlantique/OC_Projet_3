package com.jeu.view;

/**
 * 
 */
public class VueConsole {

    /**
     * Default constructor
     */
    public VueConsole() {
    }
    
    /**
     * 
     */    
    public void afficherAccueil() {
    	
    	String message = 	"***********************************************************************\n"
    						+"*                            Bienvenue !                              *\n"
    						+"*      Cette application vous propose des jeux de combinaison.        *\n"
    						+"***********************************************************************";    	
    	System.out.println(message);
    }
    
    public void afficherMessage(String message) {
    	
    	System.out.println(message);
    }
    

    /**
     * 
     */
    public void afficherMenuJeux() {
    	String message = 	  "***********************************************************************\n"
							+ "*                            Choisir un jeu                           *\n"
							+ "*                                                                     *\n"
							+ "* 1 : Recherche d'une combinaison avec +/-                            *\n"
							+ "* 2 : Mastermind                                                      *\n"
							+ "***********************************************************************";
    	System.out.println(message);
    }
    
    public void afficherPoursuivreSession() {
    	String message = "Voulez-vous poursuivre la session ?\n"
						+ "1 : Oui\n"
						+ "2 : Non";
    	System.out.println(message);
    }
    
    public void afficherPoursuivrePartie() {
    	String message = "Voulez-vous poursuivre la partie ?\n"
						+ "1 : Oui\n"
						+ "2 : Non";
    	System.out.println(message);
    }

    /**
     * 
     */
    public void afficherMenuMode() {
    	String message = 	  "***********************************************************************\n"
							+ "*                            Choisir un mode                          *\n"
							+ "*                                                                     *\n"
							+ "* 1 : Challenger                                                      *\n"
							+ "* 2 : D�fenseur                                                       *\n"
							+ "* 3 : Duel                                                            *\n"
							+ "***********************************************************************";
    	System.out.println(message);
    }

    /**
     * 
     */
    public void afficherJeuCombinaisonIntro(int nombreEssais) {
    	String message = 	  "***********************************************************************\n"
							+ "*               D�couvrir la combinaison � l'aide de +/-              *\n"
							+ "*                        Vous avez " + nombreEssais + " essai(s)                         *\n"
							+ "*                           Bonne chance!                             *\n"
							+ "***********************************************************************";
    	System.out.println(message);
    }
    /**
     * 
     */
    public void afficherJeuMastermindIntro(int nombreEssais) {
    	String message = 	  "***********************************************************************\n"
							+ "*               Mastermind d�couvrir la combinaison secr�te           *\n"
							+ "*                        Vous avez " + nombreEssais + " essai(s)                        *\n"
							+ "*                           Bonne chance!                             *\n"
							+ "***********************************************************************";
    	System.out.println(message);
    }


}