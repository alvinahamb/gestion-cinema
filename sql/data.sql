
INSERT INTO tarif_place (id_type_place, prix) VALUES
(1, 30000.00),
(2, 40000.00),
(3, 80000.00);

INSERT INTO remise_categorie (id_type_place, id_categorie_client, pourcentage_de_reduction, tarif) VALUES
(NULL, 1, NULL, 50.00);  -- Enfant: 50% de réduction sur tous les types de place

-- INSERT INTO remise_categorie ( id_categorie_client, pourcentage_de_reduction, tarif) VALUES (2, 3, NULL, 45000.00);
-- INSERT INTO remise_categorie ( id_categorie_client, pourcentage_de_reduction, tarif) VALUES (3, 2, NULL, 30000.00);
-- INSERT INTO remise_categorie ( id_categorie_client, pourcentage_de_reduction, tarif) VALUES (4, 1, NULL, 20000.00);
-- INSERT INTO remise_categorie ( id_categorie_client, pourcentage_de_reduction, tarif) VALUES (5, 2, NULL, 40000.00);
-- INSERT INTO remise_categorie ( id_categorie_client, pourcentage_de_reduction, tarif) VALUES (6, 1, NULL, 30000.00);
-- INSERT INTO remise_categorie ( id_categorie_client, pourcentage_de_reduction, tarif) VALUES (7, 3, NULL, 80000.00);

-- =====================
-- CLIENTS
-- =====================

INSERT INTO categorie_client (libelle, description) VALUES
('Enfant',''),
('Adulte',''),
('Senior',''),
('Ado','');

INSERT INTO client (
    nom, prenom, date_naissance, email, telephone, id_categorie_client
) VALUES
('Client','Anonyme',NULL,'anonyme@example.com',NULL,1);
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
