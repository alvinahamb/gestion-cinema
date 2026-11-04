# 🎬 Gestion Cinéma

Application web de gestion d'un cinéma : programmation des films et des séances, réservation de places sur plan de salle, tarification dynamique par catégorie de client, émission de tickets et suivi des paiements.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-336791?logo=postgresql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-templates-005F0F?logo=thymeleaf&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)

---

##  Fonctionnalités

### Exploitation
- **Séances** : planification par film (version/langue, sous-titres), salle, date et heure, avec recherche et suivi du statut (prévue, en cours, terminée, annulée).
- **Réservations** : création d'une réservation pour un client sur une séance, ajout et retrait de places, statuts (en attente, confirmée, payée, annulée).
- **Achat de billets** : sélection des places sur le plan de la salle, avec les places déjà occupées (tickets et réservations) bloquées automatiquement.
- **Tickets** : génération du ticket et du détail des places achetées.
- **Paiements** : paiement en une ou plusieurs fois, plusieurs méthodes de paiement, paiement partiel et historique des statuts.

### Tarification
Le prix d'une place est calculé selon des règles de gestion hiérarchiques :

1. remise spécifique au couple *(type de place, catégorie client)* ;
2. à défaut, remise générale de la catégorie client (tous types de place) ;
3. une remise peut fixer un **tarif forcé** ou un **pourcentage de réduction** appliqué au tarif de base ;
4. sans remise, le tarif du type de place (Standard, Premium, VIP) s'applique ;
5. en dernier recours, le tarif de la séance.

### Administration (référentiels)
Films, genres, langues, pays, versions de film, salles, places, types de place, tarifs, catégories de clients, remises, clients, méthodes de paiement, statuts et types de statut.

### API REST
En plus de l'interface web, des endpoints JSON sont exposés sous `/api/...` (films, séances, clients, réservations, paiements, salles, places, etc.).

---

##  Stack technique

| Couche | Technologie |
|---|---|
| Langage | Java 17 |
| Framework | Spring Boot 4.0.1 (Spring MVC, Spring Data JPA) |
| Vue | Thymeleaf + CSS |
| Base de données | PostgreSQL |
| ORM | Hibernate (JPA) |
| Build | Maven (wrapper inclus) |

##  Architecture

Architecture en couches classique :

```
src/main/java/com/example/demo
├── controller/      Contrôleurs MVC (pages Thymeleaf)
│   └── api/         Contrôleurs REST (JSON)
├── service/         Logique métier (tarification, achat, paiement…)
├── repository/      Accès aux données (Spring Data JPA)
├── entity/          Entités JPA
└── dto/             Objets de transfert de données

src/main/resources
├── templates/       Vues Thymeleaf (par module + fragments)
└── static/css/      Feuilles de style par module

sql/                 Scripts de création et d'initialisation de la base
```

Le modèle de données comprend 27 tables, avec le schéma conceptuel dans [sql/](sql/) (fichiers Looping).

---

##  Démarrage rapide

### Prérequis
- JDK 17 ou supérieur
- PostgreSQL (5432 par défaut)
- Maven (optionnel, le wrapper `mvnw` est fourni)

### 1. Cloner le dépôt
```bash
git clone https://github.com/alvinahamb/gestion-cinema.git
cd gestion-cinema
```

### 2. Créer la base de données
```sql
CREATE DATABASE gestion_cinema;
```

Puis exécuter les scripts dans cet ordre :

```bash
psql -U postgres -d gestion_cinema -f sql/table.sql        # structure des tables
psql -U postgres -d gestion_cinema -f sql/references.sql   # langues, pays, genres, statuts…
psql -U postgres -d gestion_cinema -f sql/data.sql         # types de place, tarifs, remises, clients de démo
```

### 3. Configurer la connexion
Adapter [src/main/resources/application.properties](src/main/resources/application.properties) à votre environnement :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestion_cinema
spring.datasource.username=<votre_utilisateur>
spring.datasource.password=<votre_mot_de_passe>
```

### 4. Lancer l'application
```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

L'application est disponible sur **http://localhost:8080**.

---

##  Navigation

| Route | Description |
|---|---|
| `/` , `/dashboard` | Tableau de bord |
| `/seances` | Programmation des séances |
| `/reservations` | Gestion des réservations |
| `/achat/seance/{id}` | Achat de billets pour une séance |
| `/ticket` | Tickets émis |
| `/paiement` | Suivi et encaissement des paiements |
| `/parametre` | Paramétrage des référentiels |
| `/api/...` | API REST (JSON) |

---

##  Documentation complémentaire

- [POSTGRES_CHEATSHEET.md](POSTGRES_CHEATSHEET.md) : aide-mémoire PostgreSQL
- [THYMELEAF_CHEATSHEET.md](THYMELEAF_CHEATSHEET.md) : aide-mémoire Thymeleaf

---

##  Pistes d'évolution

- Authentification et gestion des rôles (caissier, administrateur)
- Pagination et filtres avancés sur les listes
- Tests unitaires et d'intégration sur la tarification et l'achat
- Conteneurisation (Docker Compose avec PostgreSQL)

---

##  Auteur

Projet développé par **R.A Kasaina Alvinah** ([@alvinahamb](https://github.com/alvinahamb)).
