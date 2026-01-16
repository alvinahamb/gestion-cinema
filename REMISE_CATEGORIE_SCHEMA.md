# 📊 Diagramme des Relations - Remise Catégorie

```
┌─────────────────────┐
│   CATEGORIE_CLIENT  │
│ ─────────────────── │
│ id_categorie_client │◄─────┐
│ libelle             │      │
│ description         │      │
└─────────────────────┘      │
                              │
                     ┌────────┴──────────┐
                     │ REMISE_CATEGORIE │
                     │ ──────────────── │
                     │ id_remise (PK)   │
                     │ id_type_place    │
                     │ id_categorie*    │
                     │ tarif            │
                     │ pourcentage*     │
                     └────────┬──────────┘
                              │
                     ┌────────┴──────────┐
                     │   TYPE_PLACE      │
                     │ ──────────────── │
                     │ id_type_place    │
                     │ libelle          │
                     │ description      │
                     └──────────────────┘

* Remises dynamiques par catégorie client et type de place
* Avant: remise stockée dans TABLE TICKET
* Après: remise gérée via TABLE REMISE_CATEGORIE
```

## 🔄 Migration des Données

### Avant (Table ticket):
```sql
CREATE TABLE ticket (
    id_ticket SERIAL PRIMARY KEY,
    ...
    remise DECIMAL(5,2)  ❌ SUPPRIMÉE
);
```

### Après (Nouvelle approche):
```sql
CREATE TABLE remise_categorie (
    id_remise SERIAL PRIMARY KEY,
    id_type_place INT NOT NULL,
    id_categorie_client INT NOT NULL,
    tarif DECIMAL(7,2),
    pourcentage_de_reduction DECIMAL(5,2),
    UNIQUE(id_type_place, id_categorie_client)
);

CREATE TABLE ticket (
    id_ticket SERIAL PRIMARY KEY,
    ...
    -- remise removed - use remise_categorie instead
);
```

## 📝 Exemple d'Utilisation

### 1. Créer une remise pour étudiants sur les places VIP:
```
POST /remise-categories/save
{
    "idTypePlace": 1,           // VIP
    "idCategorieClient": 2,     // Étudiants
    "tarif": 15.00,
    "pourcentageDeReduction": 20.00
}
```

### 2. Récupérer toutes les remises pour un type de place:
```
Service: remiseCategorieService.getRemisesByTypePlace(1)
```

### 3. Vérifier la remise pour un client:
```
Service: remiseCategorieService.getRemisesByCategorie(2)
```

## 🎯 Avantages

✅ **Flexibilité**: Définir différentes remises selon le type de place et la catégorie client  
✅ **Centralisation**: Gestion centralisée des remises (pas dans ticket)  
✅ **Intégrité**: Contrainte unique évite les doublons  
✅ **Requêtes optimisées**: Jointures efficaces pour calculer les tarifs finaux  
✅ **Maintenance**: Modifier une remise affecte tous les tickets futurs
