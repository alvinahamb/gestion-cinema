# 📚 PostgreSQL Cheatsheet

## 🔌 Connexion et Basiques

### Se connecter à PostgreSQL
```bash
# Connexion locale par défaut
psql -U postgres

# Connexion à une base spécifique
psql -U postgres -d nom_base

# Connexion à un serveur distant
psql -h hostname -U username -d database_name
```

### Commandes essentielles dans `psql`
```sql
\l              -- Lister toutes les bases de données
\dt             -- Lister toutes les tables
\du             -- Lister les utilisateurs/rôles
\d nom_table    -- Afficher la structure d'une table
\q              -- Quitter
\h              -- Aide SQL
\?              -- Aide des commandes psql
```

---

## 🗄️ Gestion des Bases de Données

### Créer une base de données
```sql
CREATE DATABASE nom_base;

-- Avec encodage spécifique
CREATE DATABASE nom_base ENCODING 'UTF8';
```

### Supprimer une base de données
```sql
DROP DATABASE nom_base;

-- Forcer la suppression si la base est utilisée
DROP DATABASE IF EXISTS nom_base;
```

### Sauvegarder une base
```bash
pg_dump -U postgres nom_base > sauvegarde.sql
```

### Restaurer une base
```bash
psql -U postgres nom_base < sauvegarde.sql
```

---

## 👤 Gestion des Utilisateurs/Rôles

### Créer un utilisateur
```sql
CREATE USER nom_user WITH PASSWORD 'mot_de_passe';

-- Ou avec CREATE ROLE (plus moderne)
CREATE ROLE nom_user WITH LOGIN PASSWORD 'mot_de_passe';
```

### Donner des permissions
```sql
-- Accorder tous les droits sur une base
GRANT ALL PRIVILEGES ON DATABASE nom_base TO nom_user;

-- Accorder les droits sur une table
GRANT ALL PRIVILEGES ON nom_table TO nom_user;

-- Accorder les droits en lecture seule
GRANT SELECT ON nom_table TO nom_user;
```

### Supprimer un utilisateur
```sql
DROP USER nom_user;
DROP ROLE nom_user;
```

---

## 📝 Créer et Gérer les Tables

### Créer une table
```sql
CREATE TABLE animaux (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    espece VARCHAR(50),
    age INT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Types de données courants
```sql
SERIAL              -- Auto-incrémenté (1, 2, 3...)
INTEGER             -- Nombre entier
BIGINT              -- Grand nombre entier
DECIMAL(10, 2)      -- Nombre décimal (10 chiffres, 2 après la virgule)
VARCHAR(n)          -- Chaîne de caractères variable (max n)
TEXT                -- Texte long
BOOLEAN             -- Vrai/Faux
DATE                -- Date (YYYY-MM-DD)
TIMESTAMP           -- Date et heure
TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Timestamp automatique
```

### Contraintes
```sql
PRIMARY KEY         -- Clé primaire (unique + not null)
UNIQUE              -- Valeur unique
NOT NULL            -- Obligatoire
DEFAULT valeur      -- Valeur par défaut
FOREIGN KEY         -- Clé étrangère (relation vers autre table)
CHECK (condition)   -- Vérifier une condition
```

### Modifier une table
```sql
-- Ajouter une colonne
ALTER TABLE nom_table ADD COLUMN nom_colonne TYPE;

-- Supprimer une colonne
ALTER TABLE nom_table DROP COLUMN nom_colonne;

-- Renommer une colonne
ALTER TABLE nom_table RENAME COLUMN ancienne TO nouvelle;

-- Renommer la table
ALTER TABLE ancienne_nom RENAME TO nouvelle_nom;

-- Changer le type d'une colonne
ALTER TABLE nom_table ALTER COLUMN nom_colonne TYPE nouveau_type;
```

### Supprimer une table
```sql
DROP TABLE nom_table;
DROP TABLE IF EXISTS nom_table;
```

---

## 📊 Interroger les Données (SELECT)

### SELECT simple
```sql
-- Toutes les colonnes
SELECT * FROM animaux;

-- Colonnes spécifiques
SELECT nom, espece FROM animaux;

-- Avec alias
SELECT nom AS "Nom de l'animal", espece AS "Type" FROM animaux;
```

### WHERE - Filtrer les données
```sql
SELECT * FROM animaux WHERE age > 5;
SELECT * FROM animaux WHERE espece = 'chat';
SELECT * FROM animaux WHERE nom LIKE '%a%';  -- Contient 'a'
SELECT * FROM animaux WHERE age BETWEEN 1 AND 5;
SELECT * FROM animaux WHERE espece IN ('chat', 'chien');
SELECT * FROM animaux WHERE age IS NULL;
SELECT * FROM animaux WHERE age IS NOT NULL;
```

### ORDER BY - Trier
```sql
SELECT * FROM animaux ORDER BY nom ASC;   -- Croissant
SELECT * FROM animaux ORDER BY age DESC;  -- Décroissant
SELECT * FROM animaux ORDER BY espece, nom;  -- Multi-colonnes
```

### LIMIT et OFFSET - Pagination
```sql
SELECT * FROM animaux LIMIT 10;           -- 10 premiers
SELECT * FROM animaux LIMIT 10 OFFSET 20; -- 10 suivants après 20
```

### Agrégations
```sql
SELECT COUNT(*) FROM animaux;              -- Nombre total
SELECT COUNT(DISTINCT espece) FROM animaux;  -- Espèces différentes
SELECT AVG(age) FROM animaux;              -- Âge moyen
SELECT MAX(age) FROM animaux;              -- Âge maximum
SELECT MIN(age) FROM animaux;              -- Âge minimum
SELECT SUM(age) FROM animaux;              -- Somme des âges
```

### GROUP BY
```sql
SELECT espece, COUNT(*) as nombre 
FROM animaux 
GROUP BY espece;

SELECT espece, AVG(age) as age_moyen 
FROM animaux 
GROUP BY espece 
HAVING COUNT(*) > 2;  -- Espèces avec plus de 2 animaux
```

### DISTINCT
```sql
SELECT DISTINCT espece FROM animaux;  -- Toutes les espèces différentes
```

---

## ✏️ Insérer, Modifier, Supprimer (DML)

### INSERT - Ajouter des données
```sql
INSERT INTO animaux (nom, espece, age) 
VALUES ('Minou', 'chat', 3);

-- Plusieurs lignes
INSERT INTO animaux (nom, espece, age) VALUES
('Minou', 'chat', 3),
('Rex', 'chien', 5),
('Kiwi', 'perroquet', 2);

-- Sans spécifier les colonnes (tous les champs)
INSERT INTO animaux VALUES (DEFAULT, 'Bella', 'chien', 4, DEFAULT);
```

### UPDATE - Modifier des données
```sql
UPDATE animaux SET age = 6 WHERE nom = 'Minou';

UPDATE animaux SET age = age + 1 WHERE espece = 'chat';

-- Plusieurs colonnes
UPDATE animaux SET age = 4, espece = 'chat' WHERE id = 1;
```

### DELETE - Supprimer des données
```sql
DELETE FROM animaux WHERE id = 1;

DELETE FROM animaux WHERE age > 10;

-- ATTENTION ! Supprimer TOUTES les données
DELETE FROM animaux;
```

---

## 🔗 Jointures (JOINS)

### INNER JOIN
```sql
SELECT a.nom, d.description
FROM animaux a
INNER JOIN details d ON a.id = d.animal_id;
```

### LEFT JOIN (LEFT OUTER JOIN)
```sql
SELECT a.nom, d.description
FROM animaux a
LEFT JOIN details d ON a.id = d.animal_id;
```

### RIGHT JOIN
```sql
SELECT a.nom, d.description
FROM animaux a
RIGHT JOIN details d ON a.id = d.animal_id;
```

### FULL OUTER JOIN
```sql
SELECT a.nom, d.description
FROM animaux a
FULL OUTER JOIN details d ON a.id = d.animal_id;
```

---

## 🔑 Clés Étrangères et Relations

### Créer une relation
```sql
CREATE TABLE details (
    id SERIAL PRIMARY KEY,
    animal_id INT NOT NULL,
    description TEXT,
    FOREIGN KEY (animal_id) REFERENCES animaux(id) ON DELETE CASCADE
);
```

### Cascades disponibles
```sql
ON DELETE CASCADE       -- Supprimer les détails si l'animal est supprimé
ON DELETE SET NULL      -- Mettre NULL si l'animal est supprimé
ON DELETE RESTRICT      -- Empêcher la suppression si des détails existent
ON UPDATE CASCADE       -- Mettre à jour les détails si l'animal change
```

---

## 📈 Index et Performance

### Créer un index
```sql
CREATE INDEX idx_nom ON animaux(nom);

-- Index sur plusieurs colonnes
CREATE INDEX idx_espece_age ON animaux(espece, age);

-- Index unique
CREATE UNIQUE INDEX idx_email ON utilisateurs(email);
```

### Supprimer un index
```sql
DROP INDEX idx_nom;
```

### Voir les index
```sql
SELECT * FROM pg_indexes WHERE tablename = 'animaux';
```

---

## 🔍 Requêtes Utiles

### Trouver les doublons
```sql
SELECT nom, COUNT(*) 
FROM animaux 
GROUP BY nom 
HAVING COUNT(*) > 1;
```

### Sous-requêtes
```sql
SELECT * FROM animaux 
WHERE age > (SELECT AVG(age) FROM animaux);

SELECT * FROM animaux 
WHERE espece IN (SELECT espece FROM animaux WHERE age > 5);
```

### UNION - Combiner les résultats
```sql
SELECT nom FROM animaux WHERE espece = 'chat'
UNION
SELECT nom FROM animaux WHERE age > 5;
```

### CTE (Common Table Expression)
```sql
WITH animaux_jeunes AS (
    SELECT * FROM animaux WHERE age < 5
)
SELECT * FROM animaux_jeunes WHERE espece = 'chat';
```

---

## 🛠️ Maintenance et Diagnostic

### Vérifier la version
```sql
SELECT version();
```

### Voir le dernier ID généré
```sql
SELECT LASTVAL();
```

### Statistiques sur les tables
```sql
SELECT 
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) AS size
FROM pg_tables
WHERE schemaname NOT IN ('pg_catalog', 'information_schema')
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;
```

### Nettoyer et optimiser
```sql
VACUUM;              -- Nettoyer l'espace inutilisé
ANALYZE;             -- Analyser les statistiques
REINDEX DATABASE nom_base;  -- Reconstruire les index
```

---

## ⚠️ Conseils et Bonnes Pratiques

✅ **Toujours utiliser :**
- Des PRIMARY KEY sur chaque table
- Des types de données appropriés
- Des noms descriptifs

❌ **À éviter :**
- `SELECT *` en production (spécifier les colonnes)
- Supprimer sans WHERE clause
- Ajouter des colonnes NOT NULL sans valeur par défaut à une grande table

💡 **Performance :**
- Utiliser des INDEX sur les colonnes souvent filtrées
- Utiliser EXPLAIN ANALYZE pour vérifier les requêtes lentes
- Commiter régulièrement les transactions longues

---

## 📝 Exemple Complet : Gestion de Cinéma

```sql
-- Créer les tables
CREATE TABLE films (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(200) NOT NULL,
    genre VARCHAR(50),
    duree INT,
    date_sortie DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE seances (
    id SERIAL PRIMARY KEY,
    film_id INT NOT NULL REFERENCES films(id) ON DELETE CASCADE,
    horaire TIME,
    salle INT,
    places_disponibles INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insérer des données
INSERT INTO films (titre, genre, duree, date_sortie) VALUES
('Avatar 2', 'Science-fiction', 192, '2022-12-14'),
('Oppenheimer', 'Drame', 180, '2023-07-21');

INSERT INTO seances (film_id, horaire, salle, places_disponibles) VALUES
(1, '19:00', 1, 100),
(2, '20:30', 2, 85);

-- Requête de consultation
SELECT f.titre, s.horaire, s.salle, s.places_disponibles
FROM films f
INNER JOIN seances s ON f.id = s.film_id
WHERE f.genre = 'Science-fiction'
ORDER BY s.horaire;
```

---

*Dernière mise à jour : 2026*