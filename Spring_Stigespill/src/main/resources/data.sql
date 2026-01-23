-- Sletter eksisterende data for aa unngaa duplikater i H2
DELETE FROM rute;

INSERT INTO rute (id, flytt_til) VALUES (1, 0);
INSERT INTO rute (id, flytt_til) VALUES (2, 0);
INSERT INTO rute (id, flytt_til) VALUES (3, 0);
INSERT INTO rute (id, flytt_til) VALUES (4, 37); -- Stige fra 4 til 37
INSERT INTO rute (id, flytt_til) VALUES (5, 0);
INSERT INTO rute (id, flytt_til) VALUES (6, 0);

INSERT INTO rute (id, flytt_til) VALUES (31, 14); -- Slange fra 31 til 14