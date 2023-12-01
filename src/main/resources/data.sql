INSERT INTO covoituragedb.adresse(appartement, code_postal, numero_civil, province, rue, ville)
VALUES
    ('', 'J6A9Q1', '2000', 'Québec', 'Vaillancour', 'Montréal'),
    ('', 'J1W4X5', '6666', 'Québec', 'Beaubien', 'Montréal');

INSERT INTO covoituragedb.utilisateur(adresse_id, est_passager, courriel, nom, prenom, telephone, tokenId)
VALUES
    (1, 0, 'biden@biden.com', 'Biden', 'Joe', '5140000000', 'auth0|656a124e6a822f7ee8384799'),
    (2, 1, 'trudeau@trudeau.com', 'Trudeau', 'Justin', '4380000000', 'auth0|656a127ca19599c92097aaeb');

INSERT INTO covoituragedb.trajet(adresse_arrivee_id, adresse_depart_id, nom, utilisateur_id)
VALUES
    (2, 1, 'Travail', 1),
    (1, 2, 'Maison', 1);

INSERT INTO covoituragedb.reservation(nombre_passager, trajet_id, heure_depart)
VALUES
    (4, 1, '2023-11-08 13:00:00')
