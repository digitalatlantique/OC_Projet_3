package com.jeu.controller;

import java.util.HashMap;

import com.jeu.controller.attaquer.Attaquer;
import com.jeu.controller.defendre.Defendre;
import com.jeu.model.Jeu;
import com.jeu.model.Joueur;
/**
 * Cette classe permet de configurer le comportement d'un joueur (attaque - d�fense ou les deux)
 * @author Workstation
 *
 */
public class ControlleurJoueur {
	
	public ControlleurJoueur(Joueur joueur, Attaquer attaque) {
		
		this.joueur = joueur;
		this.attaque = attaque;
	}
	
	public ControlleurJoueur(Joueur joueur, Defendre defend) {
		
		this.joueur = joueur;
		this.defend = defend;
	}
	
	public ControlleurJoueur(Joueur joueur, Attaquer attaque, Defendre defend) {
		
		this.joueur = joueur;
		this.attaque = attaque;
		this.defend = defend;
	}
	
	private Joueur joueur;
	private Attaquer attaque;
	private Defendre defend;
	
	public String propose(HashMap<String, String> reponse) {
		
		return attaque.proposer(reponse);
	}
	
	public void analyse(Jeu jeu, String proposition) {
		
		defend.analyser(jeu, proposition);
	}
	
	public String donnerCombinaison() {
		
		return defend.genererCombinaison();
	}

	public Joueur getJoueur() {
		return joueur;
	}	
	
}
