package com.jeu.controller.defendre;

import com.jeu.model.Jeu;

public interface Defendre {
	
	void analyser(Jeu jeu, String proposition);
	String genererCombinaison();

}
