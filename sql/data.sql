-- =====================
-- SALLES & PLACES
-- =====================

INSERT INTO salle (nom_salle, capacite, ligne, colonne) VALUES
('Salle 4', 100, 10, 10);

INSERT INTO place 


('Salle 2', 50, 5, 10),
('Salle 3', 100, 10, 10);

INSERT INTO type_place (libelle, description) VALUES
('Standard', 'Place standard'),
('VIP', 'Place premium');

INSERT INTO type_place (libelle, description) VALUES
('VIP2', 'Place VIP');
INSERT INTO tarif_place (id_type_place, prix) VALUES
(3, 90000.00);

INSERT INTO tarif_place (id_type_place, prix) VALUES
(1, 20000.00),
(2, 50000.00);

-- Salle 1
INSERT INTO place (id_salle, id_type_place, numero_ligne, numero_colonne)
SELECT 1, 1, l, c FROM generate_series(1,4) l, generate_series(1,5) c;

-- Salle 2
INSERT INTO place (id_salle, id_type_place, numero_ligne, numero_colonne)
SELECT 2, 1, l, c FROM generate_series(1,5) l, generate_series(1,10) c;

-- Salle 3 (100 places: 10 VIP2, 20 VIP, 70 Standard)
-- 10 premières places (ligne 1) avec id_type_place = 3 (VIP2)
INSERT INTO place (id_salle, id_type_place, numero_ligne, numero_colonne)
SELECT 4, 3, 1, c FROM generate_series(1,10) c;

-- 20 places (lignes 2-3) avec id_type_place = 2 (VIP)
INSERT INTO place (id_salle, id_type_place, numero_ligne, numero_colonne)
SELECT 4, 2, l, c FROM generate_series(2,3) l, generate_series(1,10) c;

-- 70 places restantes (lignes 4-10) avec id_type_place = 1 (Standard)
INSERT INTO place (id_salle, id_type_place, numero_ligne, numero_colonne)
SELECT 4, 1, l, c FROM generate_series(4,10) l, generate_series(1,10) c;

-- =====================
-- CLIENTS
-- =====================

INSERT INTO categorie_client (libelle, description) VALUES
('Standard','Client standard'),
('Etudiant','Tarif Etudiant'),
('VIP','Client premium');

INSERT INTO client (
    nom, prenom, date_naissance, email, telephone, id_categorie_client
) VALUES
('Client','Anonyme',NULL,'anonyme@example.com',NULL,1),
('Rajaonarivelo','Herintsalama','1995-04-12','herintsalama@example.com','0341234567',1),
('Andrianarivo','Lala','2000-09-23','lala@example.com','0329876543',2),
('Rakotondramanana','Hery','1998-12-15','hery@example.com','0331122334',1);

-- =====================
-- FILMS
-- =====================

INSERT INTO film (
    titre, synopsis, duree, id_langue_original, id_pays,
    date_sortie, classification_age
) VALUES
('Action Max','Film d action explosif',120,2,2,'2025-12-01',12),
('Comédie Rire','Comédie familiale',90,1,1,'2025-11-20',6),
('Drame Profond','Drame émotionnel',110,1,1,'2025-10-15',16);

INSERT INTO film_genre (id_film, id_genre) VALUES
(1,1),(2,2),(3,3);

INSERT INTO film_version (id_film, id_langue, sous_titre) VALUES
(1,2,TRUE),
(1,1,FALSE),
(2,1,FALSE),
(3,1,FALSE);

-- =====================
-- SÉANCES
-- =====================

INSERT INTO seance (
    id_film_version, id_salle, heure_debut,
    avec_sous_titre, date_seance, tarif, id_status
) VALUES
(1,1,'18:00',TRUE,CURRENT_DATE,5.00,4),
(2,2,'16:00',FALSE,CURRENT_DATE,4.50,4),
(3,3,'20:30',FALSE,CURRENT_DATE,6.00,4);

-- =====================
-- RÉSERVATIONS
-- =====================

INSERT INTO reservation (
    id_client, id_seance, total, id_status
) VALUES
(1,1,10.00,9),
(2,2,4.50,9);

INSERT INTO reservation_place (
    id_reservation, id_place, id_categorie_client, prix
) VALUES
(1,1,1,5.00),
(1,2,1,5.00),
(2,21,2,4.50);

-- =====================
-- TICKETS
-- =====================

INSERT INTO ticket (
    id_reservation, id_client, id_seance, total
) VALUES
(NULL,3,3,12.00);

INSERT INTO ticket_place (
    id_ticket, id_place, id_categorie_client, prix
) VALUES
(1,101,1,6.00),
(1,102,1,6.00);

-- =====================
-- PAIEMENTS
-- =====================

INSERT INTO paiement (
    id_reservation, montant, id_status, date_paiement_prevu
) VALUES
(2,4.50,12,CURRENT_DATE);

INSERT INTO paiement_detail (
    id_paiement, id_methode_paiement, montant
) VALUES
(1,2,4.50);