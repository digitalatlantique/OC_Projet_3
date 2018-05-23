*****	Projet N°3 du parcours développeur d'applications Java (OpenClassrooms).	*****

Compétences à valider : Mise en oeuvre des concepts fondamentaux de la programmation en Java

Java Runtime Environment : JRE 9.0.1

Disponible dans ce projet:
- un diagramme de classe	=> diagrammes/ClassDiagram.png
- Les sources


Contexte :

Créer une application proposant des jeux de logique.

1 - La recherche d'une combinaison à l'aide d'indications +/-
2 - Le célèbre Mastermind. 


-----	Travail demandé		-----

Vous devez développer une application en Java, soit en mode console soit avec Swing, permettant de lancer les différents jeux.

Chaque jeu pourra être joué selon 3 modes :

    Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur
    Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète
    Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné

Voici le fonctionnement normal attendu dans l'application :

    Au démarrage, l'utilisateur doit choisir le jeu auquel il veut jouer parmi les choix proposés.
    L'utilisateur sélectionne le jeu et le mode de son choix. L'application lance le jeu sélectionné.
    L'utilisateur joue. S'il perd, l'application affiche la solution.
    À la fin de la partie, l'utilisateur peut choisir :
        de rejouer au même jeu
        de lancer un autre jeu (retour à l'écran de choix des jeux du début)
        de quitter l'application

Il doit être possible de lancer l'application dans un mode "développeur". Dans ce mode la solution est affichée dès le début. Cela permet de tester le bon comportement de l'application en cas de bonne ou de mauvaise réponse de l'utilisateur. Ceci est à réaliser avec les mécanismes suivants :

    Passage d'un paramètre au lancement de l'application
    Propriété spécifique dans le fichier de configuration

Un fichier de configuration (config.properties) permettra de paramétrer l'application, notamment :

    Pour chaque jeu :
        le nombre de cases de la combinaison secrète
        le nombre d'essais possibles
    Pour le Mastermind :
        le nombre couleur/chiffre utilisables (de 4 à 10)

Un fichier de configuration (log4j.xml) permettra de paramétrer les logs de l'application. La gestion des logs se fera avec Apache Log4j.
Livrables attendus

Vous livrerez, sur GitHub ou GitLab (dans un seul dépôt Git) :

    le code source de l’application
    le fichier de configuration
    une documentation succincte (un fichier README.md suffit) expliquant comment compiler et lancer l'application


