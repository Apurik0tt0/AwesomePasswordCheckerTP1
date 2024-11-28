# Politique de Sécurité

## Signalement des Vulnérabilités
Si vous découvrez une vulnérabilité de sécurité, veuillez nous la signaler dès que possible. Vous pouvez soumettre des problèmes de sécurité via l'outil [GitHub Security Advisory](https://github.com/advisories) ou en envoyant un email à `security@michelin.com`.

## Processus de Gestion des Vulnérabilités
1. **Accusé de réception** : Nous accuserons réception de votre rapport dans les 24 heures suivant sa soumission.
2. **Évaluation** : Nous analyserons et reproduirons le problème signalé pour comprendre son impact.
3. **Correction** : Nous préparerons un correctif et testerons la solution.
4. **Publication** : Nous publierons une nouvelle version contenant le correctif et annoncerons la mise à jour sur les canaux de communication du projet.

## Bonnes Pratiques de Sécurité
- **Validation des entrées** : Assurez-vous que toutes les entrées utilisateur sont validées et échappées pour prévenir les injections de code.
- **Chiffrement des données** : Utilisez des algorithmes de chiffrement sécurisés pour stocker et transmettre des données sensibles.
- **Principes de moindre privilège** : Limitez les droits d'accès aux ressources au strict nécessaire pour minimiser les risques.
- **Gestion des secrets** : Ne stockez jamais de secrets (comme des mots de passe ou des clés API) dans le code source. Utilisez des solutions de gestion des secrets adaptées.

## Versions Supportées
Les versions actuellement supportées pour les correctifs de sécurité incluent la version en cours et les deux dernières versions majeures du projet.

## Remerciements
Nous remercions la communauté et les chercheurs en sécurité pour leur collaboration et leur aide précieuse pour rendre ce projet plus sûr.
