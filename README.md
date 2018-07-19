# Projet N°3 du parcours développeur d'applications Java (OpenClassrooms). 

---------------------------------

## Auteur : ### * Mickaël L *

## Avancement du projet 
Le projet est fonctionnel et répond à la demande de l'énoncé du projet 3.
 
## Description 
Application proposant des jeux de logique. Un jeu de recherche d'une combinaison à l'aide d'indication '+-=' et le célèbre Mastermind.

## Pour lancer l'application  
java -jar jeu.jar

## Pour lancer le jeu en mode développeur 
Cette option permet l'affichage de la solution.
* java -jar jeu.jar --dev
* ou dans le fichier de configuration (config.properties) : developpeur=true

## Pour ajouter un nouveau jeu de logique 
Dans controller.ControllerJeuFactory
* Ajouter une propriété static : public static final int MON_NOUVEAU_JEU = 3;
* Puis case 3 : return new MON_NOUVEAU_JEU();
* Ajouter la classe dans le package model

## Information complémentaire
log4j est installé dans le projet => dossier lib, aucune configuration complémentaire requise.

## Documentation 
Disponible dans le dossier Documentation
* Une javaDoc
* Un diagramme de classe
* Les matrices en format PDF



 

