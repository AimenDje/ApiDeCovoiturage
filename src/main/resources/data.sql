INSERT INTO covoituragedb.adresse(appartement, code_postal, numero_civil, province, rue, ville)
VALUES
    ('', 'J6A9Q1', '2000', 'Québec', 'Vaillancour', 'Montréal'),
    ('', 'J1W4X5', '6666', 'Québec', 'Beaubien', 'Montréal');

INSERT INTO covoituragedb.utilisateur(adresse_id, est_passager, courriel, nom, prenom, telephone)
VALUES
    (1, 0, 'joebiden@videotron.com', 'Biden', 'Joe', '5140000000'),
    (2, 1, 'trudeau@hotmail.com', 'Trudeau', 'Justin', '4380000000');

INSERT INTO covoituragedb.trajet(adresse_arrivee_id, adresse_depart_id, nom, utilisateur_id)
VALUES
    (2, 1, 'Travail', 1),
    (1, 2, 'Maison', 1);

INSERT INTO covoituragedb.reservation(nombre_passager, trajet_id, heure_depart)
VALUES
    (4, 1, '2023-11-08 13:00:00')