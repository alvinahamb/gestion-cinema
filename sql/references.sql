
-- =====================
-- DONNÉES INITIALES
-- =====================

INSERT INTO langue (nom, code) VALUES
('Francais','FR'),('Anglais','EN'),('Espagnol','ES'),
('Allemand','DE'),('Italien','IT');

INSERT INTO pays (nom) VALUES
('France'),('Etats-Unis'),('Royaume-Uni'),
('Allemagne'),('Italie'),('Espagne'),
('Canada'),('Australie');

INSERT INTO genre (libelle) VALUES
('Action'),('Comédie'),('Drame'),
('Horreur'),('Science-Fiction'),
('Romance'),('Documentaire');

INSERT INTO methode_paiement (libelle) VALUES
('Carte Bancaire'),('Espèces'),
('Chèque'),('Virement Bancaire'),
('Mobile Money');

INSERT INTO type_place (libelle, description) VALUES
('Standard', 'Place standard'),
('Premium', 'Place premium'),
('VIP', 'Place VIP');

INSERT INTO type_statut (libelle) VALUES
('Film'),
('Séance'),
('Réservation'),
('Paiement');

INSERT INTO status (libelle, code, ordre, id_type_statut) VALUES
-- FILM
('A  l affiche','FILM_AFFICHE',1,1),
('Prochainement','FILM_PROCHAIN',2,1),
('Retiré','FILM_RETIRE',3,1),

-- SEANCE
('Prevue','SEANCE_PREVUE',10,2),
('En cours','SEANCE_EN_COURS',11,2),
('Terminée','SEANCE_TERMINEE',12,2),
('Annulée','SEANCE_ANNULEE',13,2),

-- RESERVATION
('En attente','RESERVATION_ATTENTE',20,3),
('Confirmée','RESERVATION_CONFIRMEE',21,3),
('Payée','RESERVATION_PAYEE',22,3),
('Annulée','RESERVATION_ANNULEE',23,3),

-- PAIEMENT
('Paiement en attente','PAIEMENT_ATTENTE',30,4),
('Paiement partiel','PAIEMENT_PARTIEL',31,4),
('Paiement effectué','PAIEMENT_PAYE',32,4);
