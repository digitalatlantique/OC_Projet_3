*****	Projet N�3 du parcours d�veloppeur d'applications Java (OpenClassrooms).	*****

Comp�tences � valider : Mise en oeuvre des concepts fondamentaux de la programmation en Java

Java Runtime Environment : JRE 9.0.1

Disponible dans ce projet:
- un diagramme de classe	=> diagrammes/ClassDiagram.png
- Les sources


Contexte :

Cr�er une application proposant des jeux de logique.

1 - La recherche d'une combinaison � l'aide d'indications +/-
2 - Le c�l�bre Mastermind. 


-----	Travail demand�		-----

Vous devez d�velopper une application en Java, soit en mode console soit avec Swing, permettant de lancer les diff�rents jeux.

Chaque jeu pourra �tre jou� selon 3 modes :

    Mode challenger o� vous devez trouver la combinaison secr�te de l'ordinateur
    Mode d�fenseur o� c'est � l'ordinateur de trouver votre combinaison secr�te
    Mode duel o� l'ordinateur et vous jouez tour � tour, le premier � trouver la combinaison secr�te de l'autre a gagn�

Voici le fonctionnement normal attendu dans l'application :

    Au d�marrage, l'utilisateur doit choisir le jeu auquel il veut jouer parmi les choix propos�s.
    L'utilisateur s�lectionne le jeu et le mode de son choix. L'application lance le jeu s�lectionn�.
    L'utilisateur joue. S'il perd, l'application affiche la solution.
    � la fin de la partie, l'utilisateur peut choisir :
        de rejouer au m�me jeu
        de lancer un autre jeu (retour � l'�cran de choix des jeux du d�but)
        de quitter l'application

Il doit �tre possible de lancer l'application dans un mode "d�veloppeur". Dans ce mode la solution est affich�e d�s le d�but. Cela permet de tester le bon comportement de l'application en cas de bonne ou de mauvaise r�ponse de l'utilisateur. Ceci est � r�aliser avec les m�canismes suivants :

    Passage d'un param�tre au lancement de l'application
    Propri�t� sp�cifique dans le fichier de configuration

Un fichier de configuration (config.properties) permettra de param�trer l'application, notamment :

    Pour chaque jeu :
        le nombre de cases de la combinaison secr�te
        le nombre d'essais possibles
    Pour le Mastermind :
        le nombre couleur/chiffre utilisables (de 4 � 10)

Un fichier de configuration (log4j.xml) permettra de param�trer les logs de l'application. La gestion des logs se fera avec Apache Log4j.
Livrables attendus

Vous livrerez, sur GitHub ou GitLab (dans un seul d�p�t Git) :

    le code source de l�application
    le fichier de configuration
    une documentation succincte (un fichier README.md suffit) expliquant comment compiler et lancer l'application


