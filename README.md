# Projet N�3 du parcours d�veloppeur d'applications Java (OpenClassrooms). 

---------------------------------

## Auteur : ### * Micka�l L *

## Avancement du projet 
Le projet est fonctionnel et r�pond � la demande de l'�nonc� du projet 3.
 
## Description 
Application proposant des jeux de logique. Un jeu de recherche d'une combinaison � l'aide d'indication '+-=' et le c�l�bre Mastermind.

## Pour lancer l'application  
java -jar jeu.jar

## Pour lancer le jeu en mode d�veloppeur 
Cette option permet l'affichage de la solution.
* java -jar jeu.jar --dev
* ou dans le fichier de configuration (config.properties) : developpeur=true

## Pour ajouter un nouveau jeu de logique 
Dans controller.ControllerJeuFactory
* Ajouter une propri�t� static : public static final int MON_NOUVEAU_JEU = 3;
* Puis case 3 : return new MON_NOUVEAU_JEU();
* Ajouter la classe dans le package model

## Information compl�mentaire
log4j est install� dans le projet => dossier lib, aucune configuration compl�mentaire requise.

## Documentation 
Disponible dans le dossier Documentation
* Une javaDoc
* Un diagramme de classe
* Les matrices en format PDF



 

