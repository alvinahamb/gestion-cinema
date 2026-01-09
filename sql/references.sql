
INSERT INTO langue (nom, code) VALUES 
('Français', 'FR'),
('Anglais', 'EN'),
('Espagnol', 'ES'),
('Allemand', 'DE'),
('Italien', 'IT');

INSERT INTO pays (nom) VALUES 
('France'),
('Etats-Unis'),
('Royaume-Uni'),
('Allemagne'),
('Italie'),
('Espagne'),
('Canada'),
('Australie');

INSERT INTO genre (libelle) VALUES 
('Action'),
('Comédie'),
('Drame'),
('Horreur'),
('Science-Fiction'),
('Romance'),
('Documentaire');

INSERT INTO status_film (libelle) VALUES
('À l affiche'),
('Prochainement'),
('Retiré');

INSERT INTO status_seance (libelle) VALUES
('Prévue'),
('En cours'),
('Terminée'),
('Annulée');

INSERT INTO status_reservation (libelle) VALUES
('En attente'),
('Confirmée'),
('Annulée'),
('Payée');

INSERT INTO methode_paiement (libelle) VALUES 
('Carte Bancaire'),
('Espèces'),
('Chèque'),
('Virement Bancaire'),
('Mobile Money');