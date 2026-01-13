
-- ======================
-- 2. Salles
-- ======================

INSERT INTO salle (nom_salle, capacite, ligne, colonne) VALUES
('Salle 1', 20, 4, 5),
('Salle 2', 50, 5, 10),
('Salle 3', 100, 10, 10);

-- ======================
-- 3. Places
-- ======================

-- Salle 1 : 4 x 5
INSERT INTO place (id_salle, numero_ligne, numero_colonne)
SELECT 1, l, c
FROM generate_series(1,4) l, generate_series(1,5) c;

-- Salle 2 : 5 x 10
INSERT INTO place (id_salle, numero_ligne, numero_colonne)
SELECT 2, l, c
FROM generate_series(1,5) l, generate_series(1,10) c;

-- Salle 3 : 10 x 10
INSERT INTO place (id_salle, numero_ligne, numero_colonne)
SELECT 3, l, c
FROM generate_series(1,10) l, generate_series(1,10) c;

-- ======================
-- 4. Clients
-- ======================

INSERT INTO client (nom, prenom, date_naissance, email, telephone) VALUES
('Client', 'Anonyme', NULL, 'anonyme@example.com', NULL),
('Rajaonarivelo', 'Herintsalama', '1995-04-12', 'herintsalama@example.com', '0341234567'),
('Andrianarivo', 'Lala', '2000-09-23', 'lala@example.com', '0329876543'),
('Rakotondramanana', 'Hery', '1998-12-15', 'hery@example.com', '0331122334');

-- ======================
-- 5. Films
-- ======================

INSERT INTO film (
    titre, synopsis, duree, id_langue_original, id_pays,
    date_sortie, classification_age
) VALUES
('Action Max', 'Film d action explosif', 120, 2, 2, '2025-12-01', 12),
('Comédie Rire', 'Comédie familiale', 90, 1, 1, '2025-11-20', 6),
('Drame Profond', 'Drame émotionnel', 110, 1, 1, '2025-10-15', 16);

-- ======================
-- 6. Genres des films
-- ======================

INSERT INTO film_genre (id_film, id_genre) VALUES
(1, 1),
(2, 2),
(3, 3);

-- ======================
-- 7. Versions de films
-- ======================

INSERT INTO film_version (id_film, id_langue, sous_titre) VALUES
(1, 2, TRUE),
(1, 1, FALSE),
(2, 1, FALSE),
(3, 1, FALSE);

-- ======================
-- 8. Séances
-- ======================

INSERT INTO seance (
    id_film_version, id_salle, heure_debut,
    avec_sous_titre, tarif, id_status
) VALUES
(1, 1, '18:00', TRUE, 5.00, 1),
(2, 2, '16:00', FALSE, 4.50, 1),
(3, 3, '20:30', FALSE, 6.00, 1);

-- ======================
-- 9. Réservations
-- ======================

INSERT INTO reservation (
    id_client, id_seance, total, id_status
) VALUES
(1, 1, 10.00, 2),
(2, 2, 4.50, 2);

INSERT INTO reservation_place (id_reservation, id_place, prix) VALUES
(1, 1, 5.00),
(1, 2, 5.00),
(2, 21, 4.50);

-- ======================
-- 10. Tickets (achat direct)
-- ======================

INSERT INTO ticket (id_client, id_seance, total) VALUES
(3, 3, 12.00);

INSERT INTO ticket_place (id_ticket, id_place, prix) VALUES
(1, 101, 6.00),
(1, 102, 6.00);

-- ======================
-- 11. Paiement
-- ======================

INSERT INTO paiement (id_ticket, montant, id_status) VALUES
(1, 12.00, 2);

INSERT INTO paiement_detail (
    id_paiement, id_methode_paiement, montant
) VALUES
(1, 2, 12.00);
