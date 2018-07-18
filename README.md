# Projet N°3 du parcours développeur d'applications Java (OpenClassrooms).  


## Pour lancer l'application : 
java -jar jeu.jar

## Pour lancer le jeu en mode développeur :
* java -jar jeu.jar --dev
* ou dans le fichier de configuration (config.properties) : developpeur=true

## Pour ajouter un nouveau jeu de logique :
Dans controller.ControllerJeuFactory
* Ajouter une propriété static : public static final int MON_NOUVEAU_JEU = 3;
* Puis case 3 : return new MON_NOUVEAU_JEU();
* Ajouter la classe dans le package model

## Information complémentaire
log4j est installé dans le projet => dossier lib, aucune configuration complémentaire requise.




 

