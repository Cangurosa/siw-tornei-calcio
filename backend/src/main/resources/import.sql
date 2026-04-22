-- TORNEI
INSERT INTO torneo (id, nome, anno, descrizione) VALUES (1, 'Torneo Primavera', 2024, 'Torneo amatoriale primaverile');
INSERT INTO torneo (id, nome, anno, descrizione) VALUES (2, 'Coppa Estate', 2024, 'Torneo estivo tra squadre locali');

-- SQUADRE
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (1, 'Roma FC', 2001, 'Roma');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (2, 'Lazio United', 1998, 'Roma');
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES (3, 'Milan Stars', 2005, 'Milano');

-- RELAZIONE
INSERT INTO torneo_squadra (torneo_id, squadra_id) VALUES (1, 1);
INSERT INTO torneo_squadra (torneo_id, squadra_id) VALUES (1, 2);
INSERT INTO torneo_squadra (torneo_id, squadra_id) VALUES (2, 2);
INSERT INTO torneo_squadra (torneo_id, squadra_id) VALUES (2, 3);

-- GIOCATORI
INSERT INTO giocatore (id, nome, cognome, ruolo, altezza, squadra_id) VALUES (1, 'Mario', 'Rossi', 'Attaccante', 1.80, 1);
INSERT INTO giocatore (id, nome, cognome, ruolo, altezza, squadra_id) VALUES (2, 'Luca', 'Bianchi', 'Difensore', 1.85, 1);
INSERT INTO giocatore (id, nome, cognome, ruolo, altezza, squadra_id) VALUES (3, 'Paolo', 'Verdi', 'Centrocampista', 1.78, 2);
INSERT INTO giocatore (id, nome, cognome, ruolo, altezza, squadra_id) VALUES (4, 'Giovanni', 'Neri', 'Portiere', 1.90, 3);