#Projet N�3 du parcours d�veloppeur d'applications Java (OpenClassrooms).  


Pour compiler l'application : 
java -jar jeu.jar

Pour lancer le jeu en mode d�veloppeur :
java -jar jeu.jar --dev
ou dans le fichier de configuration (config.properties) : developpeur=true

Pour ajouter un nouveau jeu de logique :
Dans ControllerJeuFactory
*Ajouter une propri�t� static : public static final int MON_NOUVEAU_JEU = 3;
*Puis case 3 : return new MON_NOUVEAU_JEU();
*Ajouter la classe dans le package model




 

