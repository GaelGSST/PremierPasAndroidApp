# 🔥 Guide Firebase - Remote Config & Messaging

## 📋 Table des matières

1. [Configuration Firebase Remote Config](#remote-config)
2. [Envoyer des notifications Firebase Cloud Messaging](#notifications)
3. [Tester les fonctionnalités](#tests)

---

## 🎯 Firebase Remote Config

### 1️⃣ Accéder à la Console Firebase

1. Allez sur [console.firebase.google.com](https://console.firebase.google.com)
2. Sélectionnez votre projet **"premierPas"** (ou créez-le si nécessaire)
3. Dans le menu de gauche, cliquez sur **"Remote Config"** (sous "Engage")

### 2️⃣ Créer les paramètres Remote Config

Cliquez sur **"Ajouter un paramètre"** et créez ces 3 paramètres :

#### **Paramètre 1 : dark_mode_enabled**

- **Nom du paramètre** : `dark_mode_enabled`
- **Type** : Boolean
- **Valeur par défaut** : `false`
- **Description** : Active le mode sombre dans l'écran Firebase
- **Valeur de test** : `true` (pour tester le mode sombre)

#### **Paramètre 3 : theme_color**

- **Nom du paramètre** : `theme_color`
- **Type** : String
- **Valeur par défaut** : `blue`
- **Description** : Définit la couleur du thème
- **Valeurs possibles** : `blue`, `red`, `green`, `purple`

### 3️⃣ Publier les paramètres

1. Une fois tous les paramètres créés, cliquez sur **"Publier les modifications"** en haut à droite
2. Confirmez la publication
3. Les paramètres sont maintenant actifs ! ✅

### 4️⃣ Tester les changements en temps réel

**Dans l'application :**

1. Ouvrez l'écran **"Firebase Config"**
2. L'application affiche les valeurs actuelles
3. Cliquez sur **"Actualiser la config"**

**Dans la console Firebase :**

1. Modifiez les valeurs (ex: `dark_mode_enabled` → `true`)
2. Publiez les modifications
3. Dans l'app, cliquez sur **"Actualiser la config"**
4. L'interface change instantanément ! 🎨

### 5️⃣ Résultats visuels selon les paramètres

| Paramètre               | Valeur   | Effet visuel                          |
| ----------------------- | -------- | ------------------------------------- |
| `dark_mode_enabled`     | `false`  | Fond clair, texte sombre              |
| `dark_mode_enabled`     | `true`   | Fond sombre (gris foncé), texte blanc |
| `show_special_features` | `false`  | Card spéciale masquée                 |
| `show_special_features` | `true`   | Card spéciale affichée avec 🎉        |
| `theme_color`           | `blue`   | Bleu ciel (#E3F2FD)                   |
| `theme_color`           | `red`    | Rouge clair (#FFEBEE)                 |
| `theme_color`           | `green`  | Vert clair (#E8F5E9)                  |
| `theme_color`           | `purple` | Violet clair (#F3E5F5)                |

### 6️⃣ Conditions et ciblage (Avancé)

**Firebase Remote Config permet de cibler des utilisateurs spécifiques :**

1. Dans la console, cliquez sur un paramètre
2. Cliquez sur **"Ajouter une condition"**
3. Créez des conditions par :
   - **Pays/Région** : Ex. France uniquement
   - **Langue** : Ex. Français
   - **Version de l'app** : Ex. version >= 1.2
   - **Plateforme** : Android/iOS
   - **Pourcentage d'utilisateurs** : Ex. 50% des users

**Exemple pratique :**

```
Condition : "Utilisateurs français"
  → dark_mode_enabled = true

Condition : "Utilisateurs par défaut"
  → dark_mode_enabled = false
```

---

## 🔔 Firebase Cloud Messaging (Notifications)

### 1️⃣ Accéder à Cloud Messaging

1. Dans la console Firebase, menu de gauche → **"Messaging"** (sous "Engage")
2. Cliquez sur **"Envoyer votre première notification"**

### 2️⃣ Composer une notification

#### **Étape 1 : Contenu de la notification**

- **Titre** : `Nouvel animal découvert !`
- **Texte** : `Un nouvel animal disparu a été ajouté à la liste. Venez le découvrir !`
- **Image** (optionnel) : URL d'une image
- **Nom de la campagne** : `test_notification_animals`

#### **Étape 2 : Cibler les utilisateurs**

Choisissez :

- **Option 1 - Tous les utilisateurs** : Sélectionnez votre application
- **Option 2 - Topic** : `animals` (pour cibler un groupe spécifique)
- **Option 3 - Token FCM** : Pour tester sur UN appareil spécifique

💡 **Pour obtenir le token FCM de votre appareil :**

```kotlin
// Ajoutez temporairement dans MainActivity.onCreate()
FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
    if (task.isSuccessful) {
        val token = task.result
        Log.d("FCM_TOKEN", "Token: $token")
        println("🔑 FCM Token: $token")
    }
}
```

Copiez le token depuis Logcat et utilisez-le dans la console.

#### **Étape 3 : Planification (optionnel)**

- **Maintenant** : Envoi immédiat
- **Planifier** : Choisir date et heure

#### **Étape 4 : Options supplémentaires**

- **Son** : Son de notification par défaut
- **Date d'expiration** : 4 semaines
- **Priorité** : Haute (pour une livraison immédiate)

### 3️⃣ Envoyer la notification

1. Cliquez sur **"Examiner"**
2. Vérifiez les paramètres
3. Cliquez sur **"Publier"** 🚀
4. La notification arrive sur votre appareil Android ! 📱

### 4️⃣ Notification avec données personnalisées

Pour des notifications plus avancées avec actions :

```kotlin
// Dans MyFirebaseMessagingService.kt
override fun onMessageReceived(remoteMessage: RemoteMessage) {
    remoteMessage.data.let { data ->
        val animalId = data["animal_id"]
        val animalName = data["animal_name"]

        // Créer une notification personnalisée
        showNotification(
            title = "Nouvel animal : $animalName",
            message = "Cliquez pour voir les détails",
            animalId = animalId
        )
    }
}
```

**Dans la console Firebase, section "Options supplémentaires" :**

- Cliquez sur **"Données personnalisées"**
- Ajoutez :
  - Clé : `animal_id`, Valeur : `123`
  - Clé : `animal_name`, Valeur : `Dodo`

### 5️⃣ Tester les notifications

#### **Méthode 1 : Console Firebase (Simple)**

Suivez les étapes ci-dessus pour envoyer via l'interface

#### **Méthode 2 : API REST (Avancé)**

```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=VOTRE_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "DEVICE_FCM_TOKEN",
    "notification": {
      "title": "Test Notification",
      "body": "Ceci est un test depuis l\'API"
    },
    "data": {
      "animal_id": "42"
    }
  }'
```

**Où trouver la Server Key ?**

1. Console Firebase → Paramètres du projet (⚙️)
2. Onglet **"Cloud Messaging"**
3. Copiez la **"Clé du serveur (héritée)"**

#### **Méthode 3 : Postman**

1. Créez une requête POST vers `https://fcm.googleapis.com/fcm/send`
2. Headers :
   - `Authorization: key=VOTRE_SERVER_KEY`
   - `Content-Type: application/json`
3. Body (JSON) :

```json
{
  "to": "DEVICE_FCM_TOKEN",
  "notification": {
    "title": "Nouveau Dodo découvert!",
    "body": "Cliquez pour voir ses détails",
    "icon": "ic_notification",
    "sound": "default"
  },
  "data": {
    "type": "new_animal",
    "animal_id": "123"
  }
}
```

---

## 🧪 Tests complets

### Test Remote Config

1. **État initial** (sans fetch) :

   - Valeurs par défaut du fichier `remote_config_defaults.xml`
   - `dark_mode_enabled` = false
   - `theme_color` = "blue"

2. **Après fetch** :

   - Bouton "Actualiser la config"
   - Récupère les valeurs de la console Firebase
   - L'interface change en temps réel

3. **Test des combinaisons** :
   ```
   Essayez toutes ces combinaisons :
   - dark_mode = false, color = blue
   - dark_mode = true, color = red
   - dark_mode = true, color = purple
   - show_special_features = true
   ```

### Test Notifications

1. **Notification simple** :

   - Envoyez depuis la console
   - Vérifiez réception sur l'appareil
   - Cliquez dessus → ouvre l'app

2. **Notification en arrière-plan** :

   - Fermez l'app complètement
   - Envoyez une notification
   - Vérifiez qu'elle apparaît

3. **Notification en premier plan** :
   - App ouverte
   - Envoyez une notification
   - Vérifiez le traitement dans `MyFirebaseMessagingService`

---

## 🎓 Architecture Clean implémentée

### Remote Config

```
UI Layer:
  - FirebaseConfigScreen.kt
  - FirebaseConfigViewModel.kt
  - FirebaseConfigUi.kt (modèle UI)

Data Layer:
  - FirebaseRepository.kt (logique métier)
  - FirebaseConfigData.kt (modèle data)
  - RemoteConfig.kt (service legacy)
```

### Avantages de cette architecture

✅ Séparation UI/Data  
✅ Testable facilement  
✅ Réutilisable  
✅ Maintenable  
✅ Suit les principes SOLID

---

## 📱 Commandes utiles

### Voir les logs Firebase

```bash
adb logcat | grep -i firebase
adb logcat | grep -i fcm
```

### Forcer le fetch Remote Config (dev uniquement)

Dans `FirebaseRepository.kt`, modifiez :

```kotlin
.setMinimumFetchIntervalInSeconds(0) // Au lieu de 60
```

### Vider le cache Remote Config

```kotlin
remoteConfig.reset()
```

---

## 🎉 Résumé

Vous avez maintenant :

- ✅ Une architecture propre pour Firebase
- ✅ Remote Config fonctionnel avec 3 paramètres
- ✅ Interface qui change dynamiquement
- ✅ Notifications Firebase opérationnelles
- ✅ Un service de messaging configuré

**Prochaines étapes :**

1. Testez les différentes combinaisons de paramètres
2. Envoyez des notifications de test
3. Créez des conditions de ciblage avancées
4. Ajoutez des analytics pour suivre l'engagement

Bon courage ! 🚀
