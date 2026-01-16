# 🧪 Guide de Test - Remise Catégorie

## ✅ Checklist de Vérification

### 1. Base de Données
- [ ] Table `remise_categorie` créée
- [ ] Colonne `remise` supprimée de `ticket`
- [ ] Contrainte UNIQUE sur (id_type_place, id_categorie_client) en place

### Vérifier avec:
```sql
\dt remise_categorie
\d ticket          -- Vérifier que remise n'existe pas
```

---

## 🚀 Guide Complet de Test

### Phase 1: Démarrage de l'Application

```bash
cd c:\Users\Randy\Documents\GitHub\gestion-cinema
mvn clean install
mvn spring-boot:run
```

✅ **Vérifier dans les logs:**
- `Hibernate: create table remise_categorie` (ou update si déjà existante)
- Pas d'erreur de compilation
- `Started DemoApplication in X seconds`

---

### Phase 2: Accès aux Pages

#### Test 1: Accéder à la liste des remises
```
URL: http://localhost:8080/remise-categories
Résultat attendu: 
  - Page affichée
  - Bouton "+ Ajouter une Remise"
  - Tableau (vide ou avec données existantes)
```

#### Test 2: Accéder à la liste des tarifs
```
URL: http://localhost:8080/tarif-places
Résultat attendu: 
  - Page affichée
  - Bouton "+ Ajouter un Tarif"
  - Tableau avec ID, Type Place, Prix
```

---

### Phase 3: CRUD Remise Catégorie

#### Test 3: Créer une remise
```
1. Cliquer sur "+ Ajouter une Remise"
2. Remplir le formulaire:
   - Type de Place: Sélectionner "Normal" (ou autre)
   - Catégorie Client: Sélectionner "Étudiant" (ou autre)
   - Tarif: 12.50
   - Pourcentage de Réduction: 15.00
3. Cliquer "Enregistrer"

Résultat attendu: Redirection vers /remise-categories
La nouvelle remise s'affiche dans la liste
```

#### Test 4: Modifier une remise
```
1. Cliquer sur "Modifier" pour une remise
2. Changer le pourcentage de réduction: 20.00
3. Cliquer "Enregistrer"

Résultat attendu: 
  - Redirection vers /remise-categories
  - La remise affiche le nouveau pourcentage (20.00)
```

#### Test 5: Supprimer une remise
```
1. Cliquer sur "Supprimer" pour une remise
2. Confirmer dans la popup

Résultat attendu: 
  - La remise disparaît de la liste
  - Redirection vers /remise-categories
```

---

### Phase 4: CRUD Tarif Place (Vérification)

#### Test 6: Créer un tarif
```
1. Aller à /tarif-places
2. Cliquer "+ Ajouter un Tarif"
3. Remplir:
   - Type de Place: "Premium"
   - Prix: 18.50
4. Enregistrer

Résultat attendu: Tarif ajouté à la liste
```

#### Test 7: Modifier un tarif
```
1. Cliquer "Modifier" sur un tarif
2. Changer le prix: 19.50
3. Enregistrer

Résultat attendu: Prix mis à jour
```

#### Test 8: Supprimer un tarif
```
1. Cliquer "Supprimer" sur un tarif
2. Confirmer

Résultat attendu: Tarif supprimé
```

---

### Phase 5: Tests de Contraintes

#### Test 9: Tenter de créer une remise dupliquée
```
1. Créer une remise:
   - Type Place: Normal (ID 1)
   - Catégorie: Étudiant (ID 1)
   
2. Tenter de créer une autre avec les mêmes IDs
   
Résultat attendu: Erreur UNIQUE VIOLATION
(La contrainte UNIQUE est respectée)
```

#### Test 10: Tenter de créer une remise avec IDs invalides
```
1. Créer une remise avec:
   - Type Place ID: 999 (inexistant)
   - Catégorie ID: 999
   
Résultat attendu: Erreur FOREIGN KEY VIOLATION
```

---

### Phase 6: Tests de Données

#### Test 11: Vérifier que Ticket n'a plus de colonne remise
```sql
SELECT * FROM ticket LIMIT 1;

Résultat attendu: 
  - Colonnes: id_ticket, id_reservation, id_client, id_seance, date_achat, total
  - PAS de colonne "remise"
```

#### Test 12: Vérifier la structure remise_categorie
```sql
\d remise_categorie

Résultat attendu:
  - id_remise (serial PK)
  - id_type_place (INT, FK)
  - id_categorie_client (INT, FK)
  - tarif (DECIMAL)
  - pourcentage_de_reduction (DECIMAL)
  - Contrainte UNIQUE
```

---

### Phase 7: Tests de Performance (Optionnel)

#### Test 13: Charger la page avec 1000 remises
```
INSERT 1000 remises en test
Charger /remise-categories

Résultat attendu: Page charge en < 2 secondes
```

---

## 🔍 Checklist de Validation Finale

```
✅ Tables créées/modifiées correctement
✅ Entity RemiseCategorie compilée
✅ DTO RemiseCategorieDTO compilé
✅ Repository fonctionnel
✅ Service avec méthodes CRUD
✅ Controller routé correctement
✅ Pages HTML affichées
✅ Créer une remise fonctionne
✅ Lire les remises fonctionne
✅ Modifier une remise fonctionne
✅ Supprimer une remise fonctionne
✅ Colonne remise supprimée de ticket
✅ Contraintes en base de données
✅ Aucune erreur de compilation
✅ Aucune erreur lors du démarrage
```

---

## 📝 Logs à Vérifier

### Au démarrage:
```
...
2026-01-16... INFO ... HHH000487: Processing PersistenceUnitInfo
2026-01-16... INFO ... HHH000001: Hibernate ORM core version 7.2.0.Final
2026-01-16... INFO ... HHH100046: Starting HikariPool...
2026-01-16... INFO ... HikariPool-1 - Starting...
2026-01-16... INFO ... HikariPool-1 - Started.
2026-01-16... INFO ... Started DemoApplication
...
```

### Pas d'erreurs:
```
❌ org.hibernate.exception.AuthException
❌ SQLException
❌ NullPointerException
❌ ClassNotFoundException
```

---

## 🐛 Troubleshooting

### Problème: Page blanc à /remise-categories
**Solutions:**
1. Vérifier les logs pour les erreurs
2. Vérifier le contrôleur est annoté `@Controller`
3. Vérifier le chemin du template: `remise-categorie/list.html`

### Problème: UNIQUE violation en créant une remise
**Solutions:**
1. Vérifier la combinaison (typePlace, category) n'existe pas
2. Modifier au lieu de créer si elle existe

### Problème: Foreign Key violation
**Solutions:**
1. Vérifier l'ID du type de place existe dans `type_place`
2. Vérifier l'ID de la catégorie existe dans `categorie_client`
3. Créer les données de test d'abord

---

## 📊 Données de Test

### Insérer des données de test:
```sql
-- Types de place
INSERT INTO type_place (libelle, description) VALUES 
('Normal', 'Place standard'),
('Premium', 'Place premium avec meilleure vue'),
('VIP', 'Place VIP avec services inclus');

-- Catégories client
INSERT INTO categorie_client (libelle, description) VALUES 
('Adulte', 'Tarif plein'),
('Étudiant', 'Tarif réduit étudiant'),
('Enfant', 'Tarif enfant'),
('Sénior', 'Tarif senior');

-- Remises
INSERT INTO remise_categorie (id_type_place, id_categorie_client, tarif, pourcentage_de_reduction) VALUES 
(1, 2, 10.00, 20.00),  -- Normal + Étudiant: 20% reduction
(1, 3, 8.00, 30.00),   -- Normal + Enfant: 30% reduction
(2, 2, 12.00, 15.00),  -- Premium + Étudiant: 15% reduction
(3, 4, 18.00, 25.00);  -- VIP + Sénior: 25% reduction
```

---

## ✨ Prochaines Étapes

1. Ajouter la navigation dans la barre latérale
2. Implémenter le filtrage par type ou catégorie
3. Ajouter des exports CSV
4. Ajouter des graphiques de remises
5. Implémenter les validations métier avancées
