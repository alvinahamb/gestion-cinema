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

CREATE TABLE IF NOT EXISTS client (
    id_client SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    email VARCHAR(100) NOT NULL,
    telephone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS status (
    id_status SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    code VARCHAR(100) NOT NULL,
    ordre INT NOT NULL
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

CREATE TABLE IF NOT EXISTS place (
    id_place SERIAL PRIMARY KEY,
    id_salle INT,
    id_type_place INT,
    numero_ligne INT NOT NULL,
    numero_colonne INT NOT NULL,
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

CREATE TABLE IF NOT EXISTS film_version (
    id SERIAL PRIMARY KEY,
    id_film INT,
    id_langue INT,
    sous_titre BOOLEAN,
    FOREIGN KEY (id_film) REFERENCES film(id_film),
    FOREIGN KEY (id_langue) REFERENCES langue(id_langue)
);

CREATE TABLE IF NOT EXISTS seance (
    id_seance SERIAL PRIMARY KEY,
    id_film_version INT,
    id_salle INT,
    heure_debut TIME(0),
    avec_sous_titre BOOLEAN,
    date_seance DATE DEFAULT CURRENT_DATE,
    tarif DECIMAL(5,2),
    id_status INT,
    FOREIGN KEY (id_film_version) REFERENCES film_version(id),
    FOREIGN KEY (id_salle) REFERENCES salle(id_salle),
    FOREIGN KEY (id_status) REFERENCES status(id_status)
);

CREATE TABLE IF NOT EXISTS tarif_seance_place (
    id SERIAL PRIMARY KEY,
    id_seance INT,
    id_type_place INT,
    tarif DECIMAL(5,2),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance),
    FOREIGN KEY (id_type_place) REFERENCES type_place(id_type_place)
)

CREATE TABLE IF NOT EXISTS seance_status_historique (
    id SERIAL PRIMARY KEY,
    id_seance INT,
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
    FOREIGN KEY (id_status) REFERENCES status(id_status),
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance)
);

CREATE TABLE IF NOT EXISTS reservation_place (
    id SERIAL PRIMARY KEY,
    id_reservation INT NOT NULL,
    id_place INT NOT NULL,
    prix DECIMAL(7,2),
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation),
    FOREIGN KEY (id_place) REFERENCES place(id_place)
);

CREATE TABLE IF NOT EXISTS paiement (
    id_paiement SERIAL PRIMARY KEY,
    id_reservation INT,
    montant DECIMAL(7,2),
    creer_le DATE  DEFAULT CURRENT_DATE,
    id_status INT,
    date_paiement_prevu DATE,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation),
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


CREATE TABLE IF NOT EXISTS ticket (
    id_ticket SERIAL PRIMARY KEY,
    id_reservation INT,
    id_client INT,
    id_seance INT,
    date_achat DATE DEFAULT CURRENT_DATE,
    total DECIMAL(7,2),
    remise DECIMAL(5,2),
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_seance) REFERENCES seance(id_seance),
    FOREIGN KEY (id_reservation) REFERENCES reservation(id_reservation)
);

CREATE TABLE IF NOT EXISTS ticket_place(
    id SERIAL PRIMARY KEY,
    id_ticket INT,
    id_place INT,
    prix DECIMAL(7,2),
    FOREIGN KEY (id_ticket) REFERENCES ticket(id_ticket),
    FOREIGN KEY (id_place) REFERENCES place(id_place)
);
