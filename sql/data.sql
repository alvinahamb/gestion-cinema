-- =======================================
-- BASE DE DONNEES CINEMA - DONNEES TEST
-- =======================================
-- ======================
-- 2. Salles
-- ======================

INSERT INTO salle (nom_salle, capacite) VALUES
('Salle 1', 20),
('Salle 2', 50),
('Salle 3', 100);

-- ======================
-- 3. Places
-- ======================

-- Salle 1 : 20 places (4 lignes x 5 colonnes)
INSERT INTO place (id_salle, numero_ligne, numero_colonne)
SELECT 1, ligne, colonne
FROM (SELECT 1 as ligne UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) l,
     (SELECT 1 as colonne UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) c;

-- Salle 2 : 50 places (5 lignes x 10 colonnes)
INSERT INTO place (id_salle, numero_ligne, numero_colonne)
SELECT 2, ligne, colonne
FROM (SELECT 1 as ligne UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) l,
     (SELECT 1 as colonne UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
      UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) c;

-- Salle 3 : 100 places (10 lignes x 10 colonnes)
INSERT INTO place (id_salle, numero_ligne, numero_colonne)
SELECT 3, ligne, colonne
FROM (SELECT 1 as ligne UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
      UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) l,
     (SELECT 1 as colonne UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
      UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) c;

-- ======================
-- 4. Clients
-- ======================

INSERT INTO client (nom, prenom, date_naissance, email, telephone) VALUES
('Client', 'Anonyme', null, 'anonyme@example.com', null),
('Rajaonarivelo', 'Herintsalama', '1995-04-12', 'herintsalama@example.com', '0341234567'),
('Andrianarivo', 'Lala', '2000-09-23', 'lala@example.com', '0329876543'),
('Rakotondramanana', 'Hery', '1998-12-15', 'hery@example.com', '0331122334');

-- ======================
-- 5. Films
-- ======================

INSERT INTO film (titre, synopsis, duree, id_langue, id_pays, date_sortie, classification_age, id_status) VALUES
('Action Max', 'Un film bourré d action et de cascades spectaculaires', 120, 2, 2, '2025-12-01', 12, 1),
('Comédie Rire', 'Une comédie hilarante pour toute la famille', 90, 1, 1, '2025-11-20', 6, 1),
('Drame Profond', 'Un drame émouvant sur la vie et les choix difficiles', 110, 1, 1, '2025-10-15', 16, 2);

-- ======================
-- 6. Film Genres
-- ======================

INSERT INTO film_genre (id_film, id_genre) VALUES
(1, 1),
(2, 2),
(3, 3);

-- ======================
-- 7. Film Versions
-- ======================

INSERT INTO film_version (id_film, id_langue, sous_titre) VALUES
(1, 2, TRUE),
(1, 1, FALSE),
(2, 1, FALSE),
(3, 1, FALSE);

-- ======================
-- 8. Séances
-- ======================

INSERT INTO seance (id_film_version, id_salle, heure_debut, heure_fin, avec_sous_titre, tarif, id_status) VALUES
(1, 1, '2026-01-10 18:00:00', '2026-01-10 20:00:00', TRUE, 5.00, 1),
(2, 2, '2026-01-10 16:00:00', '2026-01-10 17:30:00', FALSE, 4.50, 1),
(3, 3, '2026-01-10 20:30:00', '2026-01-10 22:20:00', FALSE, 6.00, 2);

-- ======================
-- 9. Réservations
-- ======================

INSERT INTO reservation (id_client, id_seance, total, id_methode_paiement, id_status) VALUES
(1, 1, 10.00, 2, 2),
(2, 2, 4.50, 2, 2);

-- ======================
-- 10. Détails des réservations
-- ======================

INSERT INTO reservation_detail (id_reservation, id_seance, id_place, prix) VALUES
(1, 1, 1, 5.00),
(1, 1, 2, 5.00),
(2, 2, 21, 4.50);

-- ======================
-- 11. Achats
-- ======================

INSERT INTO achat (id_client, id_seance, total, id_methode_paiement) VALUES
(3, 3, 12.00, 2);

INSERT INTO achat_detail (id_achat, id_seance, id_place, prix) VALUES
(1, 3, 101, 6.00),
(1, 3, 102, 6.00);
