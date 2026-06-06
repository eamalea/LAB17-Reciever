# ReceiverDemo – BroadcastReceiver dynamique, statique et custom

Application Android démontrant l'utilisation des **BroadcastReceivers** :
- Récepteur **dynamique** pour `ACTION_AIRPLANE_MODE_CHANGED`
- Récepteur **statique** pour `BOOT_COMPLETED`
- Envoi et réception d’un **broadcast personnalisé** intra‑application

## Fonctionnalités

- Activer/Désactiver le récepteur du mode avion (Toast + TextView).
- Lire l’état actuel du mode avion via `Settings.Global`.
- Envoyer un broadcast custom avec message.
- Récepteur statique qui s’exécute au démarrage du téléphone (Toast).
- Respect des restrictions Android 14+ : broadcast explicite via `setPackage()`.

## Prérequis

- Android Studio
- SDK minimum API 24 (Android 7.0)
- Émulateur ou appareil réel (API 26+ recommandé pour tester `BOOT_COMPLETED`)

## Installation

1. Clonez le dépôt
2. Ouvrez le projet dans Android Studio
3. Lancez l’application

## Utilisation

- **Activer récepteur mode avion** : l’application écoute les changements de mode avion (activez/désactivez dans les paramètres → Toast).
- **Vérifier état mode avion** : affiche l’état courant sans attendre de broadcast.
- **Envoyer broadcast custom** : envoie un événement interne que l’application reçoit (Toast + log).
- **Test BootReceiver** : redémarrez l’émulateur ou le téléphone → un Toast apparaît.

## Améliorations apportées

- `setPackage()` pour les broadcasts customs (compatible Android 14+)
- Lecture synchrone de l’état du mode avion
- Interface utilisateur claire avec TextView
- Gestion complète du cycle de vie (enregistrement/désenregistrement)
- Logs pour le débogage

## Points de vigilance

- Le receiver **statique** doit être déclaré dans le manifeste avec `android:exported="false"`.
- La permission `RECEIVE_BOOT_COMPLETED` est obligatoire.
- Depuis Android 12, les receivers dynamiques sont privilégiés pour économiser la batterie.

