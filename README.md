#Projet N°3 du parcours développeur d'applications Java (OpenClassrooms).  


Pour compiler l'application : 
java -jar Jeu.jar

Pour lancer le jeu en mode développeur :
java -jar Jeu.jar --dev
ou dans le fichier de configuration (config.properties) : developpeur=true

Pour ajouter un nouveau jeu de logique :
Dans ControllerJeuFactory
*Ajouter une propriété static : public static final int MON_NOUVEAU_JEU = 3;
*Puis case 3 : return new MON_NOUVEAU_JEU();
*Ajouter la classe dans le package model


Disponible dans ce projet:
*un diagramme de classe	=> diagrammes/ClassDiagram.png
*Les sources


 

