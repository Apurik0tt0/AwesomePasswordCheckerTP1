# AwesomePasswordChecker

## Résumé du projet

`AwesomePasswordChecker` est une bibliothèque Java dédiée à l'évaluation de la robustesse des mots de passe. Elle utilise des centres de clusters pré-définis pour calculer une distance (à l'aide de la distance euclidienne) entre un mot de passe donné et ces centres, afin de déterminer la similarité et la sécurité du mot de passe par rapport à un ensemble de mots de passe analysés.

Cette bibliothèque propose également des fonctionnalités de masquage des caractères d'un mot de passe en utilisant des valeurs entières représentant des groupes de caractères (comme les lettres minuscules, majuscules, les chiffres, etc.). Le processus de calcul de distance permet d'évaluer la similarité d'un mot de passe par rapport à des modèles déjà existants.

## Fonctionnalités

- **Singleton** : Un accès global à l'instance de `AwesomePasswordChecker` à partir d'un fichier de centres de clusters.
- **Masquage de mot de passe** : Transforme un mot de passe en un tableau d'entiers représentant des groupes de caractères.
- **Évaluation de la robustesse** : Calcule la distance minimale entre un mot de passe donné et les centres de clusters pour estimer sa sécurité.
- **Calcul de hash MD5** : Permet de calculer un hash MD5 d'une chaîne d'entrée.

## Installation

1. Clonez ou téléchargez ce projet.
2. Ajoutez les fichiers Java dans votre projet ou intégrez cette bibliothèque en l'ajoutant à votre `classpath`.

## Prérequis

- Java 8 ou version ultérieure.
- La bibliothèque nécessite un fichier de données des centres de clusters pour fonctionner correctement. Par défaut, le fichier `cluster_centers_HAC_aff.csv` est attendu dans le répertoire `resources`.

## Utilisation

### 1. Créer une instance de `AwesomePasswordChecker`

Vous pouvez obtenir l'instance de `AwesomePasswordChecker` en utilisant l'une des méthodes suivantes :

#### À partir d'un fichier spécifique

```java
File file = new File("chemin/vers/votre/fichier.csv");
AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance(file);
```

#### Avec le fichier par défaut (prédéterminé dans le répertoire `resources`)

```java
AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
```

### 2. Masquage d'un mot de passe

Utilisez la méthode `maskAff` pour transformer un mot de passe en un tableau d'entiers :

```java
String password = "MotDePasse123";
int[] mask = checker.maskAff(password);
```

Le tableau `mask` contiendra les valeurs correspondant aux types de caractères (lettres minuscules, majuscules, chiffres, etc.).

### 3. Calcul de la distance

Pour évaluer la robustesse d'un mot de passe par rapport aux centres de clusters, utilisez la méthode `getDistance` :

```java
String password = "MotDePasse123";
double distance = checker.getDistance(password);
System.out.println("Distance minimale : " + distance);
```

Cette distance vous indiquera à quel point le mot de passe est similaire aux centres des clusters.

### 4. Calcul du hash MD5

Si vous avez besoin de calculer le hash MD5 d'un mot de passe ou d'une autre chaîne, vous pouvez utiliser la méthode `computeMD5` :

```java
String password = "MotDePasse123";
String md5Hash = AwesomePasswordChecker.computeMD5(password);
System.out.println("MD5 Hash : " + md5Hash);
```

## Structure du projet

Le projet contient les éléments suivants :

- **AwesomePasswordChecker.java** : Le fichier principal de la bibliothèque qui implémente la logique de vérification du mot de passe.
- **cluster_centers_HAC_aff.csv** : Un fichier de données contenant les centres de clusters utilisés pour évaluer les mots de passe (à placer dans le répertoire `resources` ou à spécifier un autre chemin).

## Contribution

Si vous souhaitez contribuer à ce projet, voici quelques points à respecter :

- Forkez ce dépôt.
- Créez une branche pour votre fonctionnalité ou correction de bug.
- Soumettez une Pull Request avec une description de vos changements.

## Licence

Ce projet est sous licence MIT. Vous pouvez le modifier et le distribuer librement, tant que vous respectez les conditions de la licence.