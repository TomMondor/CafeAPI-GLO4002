# Requis

* Java 18 (openJDK seulement)
* Junit 5+
* Mockito 3+
* Maven

# Notes sur les technologies utilisées
* [Jetty](https://www.eclipse.org/jetty/) est un Servlet Container. Il accepte les requêtes du web et sait comment répondre.
* [Jersey](https://jersey.github.io/) est un Servlet fait pour le développement d'API REST. Il sait comment faire la correspondance entre un API REST et vos méthodes selon la norme JAX-RS.
* [Jackson](https://www.baeldung.com/jackson) sert à sérialiser/désérialiser les objets JSON en POJO.

# Setup pour le développement

_Le setup est recommandé pour IntelliJ_

* À partir du [Github](https://github.com/GLO4002UL/projet2022-eq14.git) du projet, cloner le projet sur votre poste de travail.

* Installer la bonne version de Java.

```
File -> Project Structure -> Project
Appuyer sur le dropdown 
Sélectionner Add SDK et appuyer sur Download JDK
Sélectionner la version 18 du vendeur Oracle OpenJDK
Cliquer sur Download
Cliquer sur Apply et ensuite sur Ok
```

# Démarrer le projet

Vous pouvez démarrer le serveur (CafeServer).

Le `main()` ne demande pas d'argument.

Vous pouvez également rouler le serveur via maven :

```bash
mvn clean install
mvn exec:java -pl cafe-api
```


## Docker

Un Dockerfile est également fourni, si vous désirez rouler votre code sur les mêmes images Docker que le serveur de production.

Pour ce faire:

```bash
docker build -t application-glo4002 .
docker run -p 8181:8181 application-glo4002
```

# Checkstyle

Le checkstyle est ce qui permet de faire le formatage du code de façon uniforme.
Voici les différentes étapes pour l'activer :

```
File -> Settings -> Plugins
S'assurer d'installer le plugin CheckStyle-IDEA (Restart l'IDE si demandé)

//Pour faire apparaître les erreurs dans le code
File -> Settings -> Tools -> Checkstyle
Dans le tableau Configuration File, cliquer sur Add
Sélectionner "Use a local Checkstyle file" et sélectionner le fichier checkstyle.xml à la root du projet
Saisir une description pour le fichier et appuyer sur NEXT
Une fois la nouvelle configuration ajoutée, la sélectionner dans le tableau
Appuyer sur APPLY et ensuite sur OK

//Pour le formateur
File -> Settings -> Editor -> Code Style
Sélectionner Default copy pour le Scheme
Cliquer sur les trois petits points (Show Scheme Actions)
Appuyer sur Rename et renommer pour "Checkstyle"
Cliquer sur les trois petits points (Show Scheme Actions)
Appuyer sur "Import Scheme" et "Checkstyle configuration"
Sélectionner le fichier checkstyle.xml à la root du projet
//Pour tester le formateur, dans un fichier Java, appuyez sur Ctrl+Alt+Shift+L
```

# Conventions et Processus

Pour plus de détails sur les méthodologies, conventions et processus employés par l'équipe, consultez le [wiki](https://github.com/GLO4002UL/projet2022-eq14/wiki).

