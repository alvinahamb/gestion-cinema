
-- =====================
-- DONNÉES INITIALES
-- =====================

INSERT INTO langue (nom, code) VALUES
('Français','FR'),('Anglais','EN'),('Espagnol','ES'),
('Allemand','DE'),('Italien','IT');

INSERT INTO pays (nom) VALUES
('France'),('États-Unis'),('Royaume-Uni'),
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

INSERT INTO status (libelle, code, ordre) VALUES
('À l\'affiche','FILM_AFFICHE',1),
('Prochainement','FILM_PROCHAIN',2),
('Retiré','FILM_RETIRE',3),

('Prévue','SEANCE_PREVUE',10),
('En cours','SEANCE_EN_COURS',11),
('Terminée','SEANCE_TERMINEE',12),
('Annulée','SEANCE_ANNULEE',13),

('En attente','RESERVATION_ATTENTE',20),
('Confirmée','RESERVATION_CONFIRMEE',21),
('Payée','RESERVATION_PAYEE',22),
('Annulée','RESERVATION_ANNULEE',23),

('Paiement en attente','PAIEMENT_ATTENTE',30),
('Paiement partiel','PAIEMENT_PARTIEL',31),
('Paiement effectué','PAIEMENT_PAYE',32);