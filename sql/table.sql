-- Script SQL pour PostgreSQL - Gestion Cinéma

CREATE TABLE IF NOT EXISTS langue (
    id_langue SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    code VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS pays(
    id_pays SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS genre (
    id_genre SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS methode_paiement (
    id_methode_paiement SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS categorie_client (
    id_categorie_client SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS client (
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    email VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    id_categorie_client INT,
    FOREIGN KEY (id_categorie_client) REFERENCES categorie_client(id_categorie_client)
);

CREATE TABLE IF NOT EXISTS type_statut (
    id_type_statut SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS status (
    id_status SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL,
    ordre INT NOT NULL,
    id_type_statut INT,
    FOREIGN KEY (id_type_statut) REFERENCES type_statut(id_type_statut)
);
-- Tables principales
CREATE TABLE IF NOT EXISTS salle (
    id_salle SERIAL PRIMARY KEY,
    nom_salle VARCHAR(100) NOT NULL,
    capacite INT NOT NULL,
    ligne INT NOT NULL,
    colonne INT NOT NULL
);

CREATE TABLE IF NOT EXISTS type_place (
    id_type_place SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS tarif_place (
    id_tarif SERIAL PRIMARY KEY,
    id_type_place INT,
    prix DECIMAL(5,2),
    FOREIGN KEY (id_type_place) REFERENCES type_place(id_type_place)
);


CREATE TABLE IF NOT EXISTS place (
    id_place SERIAL PRIMARY KEY,
    id_salle INT,
    id_type_place INT,
    numero_ligne INT NOT NULL,
    numero_colonne INT NOT NULL,
    FOREIGN KEY (id_type_place) REFERENCES type_place(id_type_place),
    FOREIGN KEY (id_salle) REFERENCES salle(id_salle)
);

CREATE TABLE IF NOT EXISTS film (
    id_film SERIAL PRIMARY KEY,
    titre VARCHAR(200) NOT NULL,
    synopsis TEXT,
    duree INT NOT NULL,
    id_langue_original INT,
    id_pays INT,
    date_sortie DATE,
    date_ajout DATE DEFAULT CURRENT_DATE,
    classification_age INT,
    FOREIGN KEY (id_langue_original) REFERENCES langue(id_langue),
    FOREIGN KEY (id_pays) REFERENCES pays(id_pays)
);

CREATE TABLE IF NOT EXISTS film_genre (
    id SERIAL PRIMARY KEY,
    id_film INT,
    id_genre INT,
    FOREIGN KEY (id_film) REFERENCES film(id_film),
    FOREIGN KEY (id_genre) REFERENCES genre(id_genre)
);

    id SERIAL PRIMARY KEY,
    id_film INT,
    id_langue INT,
    sous_titre BOOLEAN,
    FOREIGN KEY (id_langue) REFERENCES langue(id_langue)
);

CREATE TABLE IF NOT EXISTS seance (
    id_seance SERIAL PRIMARY KEY,
    id_film_version INT,
    id_salle INT,
    heure_debut TIME(0),
    date_seance DATE DEFAULT CURRENT_DATE,
    tarif DECIMAL(5,2),
    id_status INT,
    FOREIGN KEY (id_film_version) REFERENCES film_version(id),
    FOREIGN KEY (id_salle) REFERENCES salle(id_salle),
    FOREIGN KEY (id_status) REFERENCES status(id_status)
);

    id SERIAL PRIMARY KEY,
    id_seance INT,
    id_categorie_client INT,
    tarif DECIMAL(5,2),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance),
);

CREATE TABLE IF NOT EXISTS tarif_seance_client_place (
    id SERIAL PRIMARY KEY,
    id_seance INT,
    id_categorie_client INT,
    id_type_place INT,
    FOREIGN KEY (id_categorie_client) REFERENCES categorie_client(id_categorie_client),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance),
    FOREIGN KEY (id_type_place) REFERENCES type_place(id_type_place)
);

CREATE TABLE IF NOT EXISTS remise_categorie (
    id_remise SERIAL PRIMARY KEY,
    id_type_place INT,
    id_categorie_client INT NOT NULL,
    tarif DECIMAL(7,2),
    FOREIGN KEY (id_type_place) REFERENCES type_place(id_type_place),
    FOREIGN KEY (id_categorie_client) REFERENCES categorie_client(id_categorie_client)
);

CREATE TABLE IF NOT EXISTS seance_status_historique (
    id SERIAL PRIMARY KEY,

    id_status INT,
    date_modification DATE  DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance),
    FOREIGN KEY (id_status) REFERENCES status(id_status)
); 

CREATE TABLE IF NOT EXISTS reservation (
    id_reservation SERIAL PRIMARY KEY,
    id_client INT NOT NULL,
    id_seance INT NOT NULL,
    date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(7,2) NOT NULL,
    remise DECIMAL(5,2),
    id_status INT,
    date_paiement_prevu DATE,
    FOREIGN KEY (id_status) REFERENCES status(id_status),
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance)
);

CREATE TABLE IF NOT EXISTS reservation_place (
    id SERIAL PRIMARY KEY,
    id_reservation INT NOT NULL,
    id_place INT NOT NULL,
    id_categorie_client INT,
    prix DECIMAL(7,2),
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation),
    FOREIGN KEY (id_place) REFERENCES place(id_place),
    FOREIGN KEY (id_categorie_client) REFERENCES categorie_client(id_categorie_client)
);


CREATE TABLE IF NOT EXISTS ticket (
    id_ticket SERIAL PRIMARY KEY,
    id_reservation INT,
    id_client INT,
    id_seance INT,
    date_achat DATE DEFAULT CURRENT_DATE,
    total DECIMAL(7,2),
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance),
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation)
);

CREATE TABLE IF NOT EXISTS ticket_place(
    id SERIAL PRIMARY KEY,
    id_ticket INT,
    id_place INT,
    prix DECIMAL(7,2),
    id_categorie_client INT,
    FOREIGN KEY (id_ticket) REFERENCES ticket(id_ticket),
    FOREIGN KEY (id_place) REFERENCES place(id_place),
    FOREIGN KEY (id_categorie_client) REFERENCES categorie_client(id_categorie_client)
);

CREATE TABLE IF NOT EXISTS paiement (
    id_paiement SERIAL PRIMARY KEY,
    id_ticket INT,
    montant DECIMAL(7,2),
    creer_le DATE  DEFAULT CURRENT_DATE,
    id_status INT,
    date_paiement_prevu DATE,
    FOREIGN KEY (id_ticket) REFERENCES ticket(id_ticket),
    FOREIGN KEY (id_status) REFERENCES status(id_status)
);

CREATE TABLE IF NOT EXISTS paiement_detail (
    id SERIAL PRIMARY KEY,
    id_paiement INT,
    id_methode_paiement INT,
    montant DECIMAL(7,2),
    date_paiement DATE  DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_methode_paiement) REFERENCES methode_paiement(id_methode_paiement),
    FOREIGN KEY (id_paiement) REFERENCES paiement(id_paiement)
);

CREATE TABLE IF NOT EXISTS paiement_status_historique (
    id SERIAL PRIMARY KEY,
    id_paiement INT,
    id_status INT,
    date_modification DATE  DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_paiement) REFERENCES paiement(id_paiement),
    FOREIGN KEY (id_status) REFERENCES status(id_status)
);

--22 janvier 2024 : ajout des tables societe, diffusion, tarif_diffusion
CREATE TABLE IF NOT EXISTS societe(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    date_ajout DATE,
    telephone VARCHAR(20),
    adresse VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS diffusion(
    id SERIAL PRIMARY KEY,
    id_societe INT,
    id_pub INT,
    id_tarif INT,
    mois INT,
    annee INT,
    date_diffusion DATE,
    nombre INT,
    diff_attribue INT,
    diff_restant INT,
    date_ajout DATE,
    FOREIGN KEY (id_societe) REFERENCES societe(id),
    FOREIGN KEY (id_pub) REFERENCES publicite(id),
    FOREIGN KEY (id_tarif) REFERENCES tarif_diffusion(id)
);

CREATE TABLE IF NOT EXISTS tarif_diffusion(
    id SERIAL PRIMARY KEY,
    tarif DECIMAL(12,2),
    date_ajout DATE
);

CREATE TABLE IF NOT EXISTS publicite(
    id SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    duree INT NOT NULL,
    date_ajout DATE,
    code VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS paiement_societe(
    id SERIAL PRIMARY KEY,
    id_diffusion INT,
    montant DECIMAL(12,2),
    date_paiement DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_diffusion) REFERENCES diffusion(id)
);

-- 23 janvier 2026

create table if not EXISTS diffusion_seance(
    id SERIAL PRIMARY KEY,
    id_diffusion INT,
    id_seance INT,
    nombre INT DEFAULT 1,
    date_ajout DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_diffusion) REFERENCES diffusion(id),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance)
);

create table if not exists facture_societe(
    id SERIAL PRIMARY KEY,
    id_societe INT,
    id_diffusion INT,
    total DECIMAL(12,2) DEFAULT 0,
    pourcentage_restante DECIMAL(5,2) DEFAULT 100,
    date_facture DATE DEFAULT CURRENT_DATE,
    date_paiement DATE,
    date_ajout DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_diffusion) REFERENCES diffusion(id),
    FOREIGN KEY (id_societe) REFERENCES societe(id)
);

create table if not exists facture_societe_detail(
    id SERIAL PRIMARY KEY,
    id_facture_societe INT,
    montant DECIMAL(12,2),
    pourcentage_paye DECIMAL(5,2),
    date_paiement DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_facture_societe) REFERENCES facture_societe(id)
);

-- 29 janvier 2025
create table if not exists produits (
    id serial PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DECIMAL(7,2) NOT NULL
);

-- Tables pour la gestion des produits (29 janvier 2025 - MISE À JOUR)
-- DROP TABLE IF EXISTS paiements_produit_details CASCADE;
-- DROP TABLE IF EXISTS mouvement_total CASCADE;
-- DROP TABLE IF EXISTS paiements_produits CASCADE;
-- DROP TABLE IF EXISTS vente_produit_detail CASCADE;
-- DROP TABLE IF EXISTS vente_produit CASCADE;
-- DROP TABLE IF EXISTS stock CASCADE;
-- DROP TABLE IF EXISTS produits CASCADE;

create table if not exists produits (
    id serial PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DECIMAL(7,2) NOT NULL
);

create Table if not EXISTS vente_produit (
    id SERIAL PRIMARY KEY,
    id_client INT,
    id_seance INT,
    total DECIMAL(7,2),
    date_achat DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance)
);

create table if not EXISTS vente_produit_detail (
    id SERIAL PRIMARY KEY,
    id_vente INT,
    id_produit INT,
    quantite INT,
    prix_unitaire DECIMAL(7,2),
    FOREIGN KEY (id_produit) REFERENCES produits(id),
    FOREIGN KEY (id_vente) REFERENCES vente_produit(id)
);

create table if not EXISTS stock (
    id serial primary key,
    id_produit INT UNIQUE,
    quantite_actuelle INT DEFAULT 0,
    FOREIGN KEY (id_produit) REFERENCES produits(id)
);

create table if not exists paiements_produits(
    id serial PRIMARY KEY,
    id_vente INT,
    montant_paye DECIMAL(7,2),
    montant_restant DECIMAL(7,2),
    date_paiement DATE DEFAULT CURRENT_DATE,
    foreign key (id_vente) REFERENCES vente_produit(id)
);

create table if not exists mouvement_total (
    id serial primary key,
    id_produit INT,
    entree int DEFAULT 0,
    sortie int DEFAULT 0,
    quatite_actuelle int,
    date_mouvement date DEFAULT CURRENT_DATE,
    id_vente_produit_detail INT,
    FOREIGN key (id_produit) REFERENCES produits(id),
    Foreign Key (id_vente_produit_detail) REFERENCES vente_produit_detail(id)
);

create table IF NOT EXISTS paiements_produit_details (
    id serial PRIMARY key,
    id_paiements int,
    montant DECIMAL(7,2),
    date_paiement date DEFAULT CURRENT_DATE,
    FOREIGN key (id_paiements) REFERENCES paiements_produits(id)
);