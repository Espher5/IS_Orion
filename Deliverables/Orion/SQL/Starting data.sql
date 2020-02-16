INSERT INTO Utente (email, password_utente, nome, cognome, data_registrazione, stato)
VALUES ('admin@gmail.com', 'Abcde1', 'Rosemary', 'Fairweather', '2020-01-01', 1),
	   ('cliente1@gmail.com', 'Abcde1', 'Alex', 'Moore', '2020-02-10', 1),
	   ('proprietario1@gmail.com', 'Abcde1', 'Oscar', 'Vaughn', '2020-02-10', 1),
	   ('proprietario2@gmail.com', 'Abcde1', 'May', 'Maguire', '2020-05-10', 1);
	   	   
INSERT INTO Amministratore 
VALUES (1, 0);

INSERT INTO Cliente (ID_utente)
VALUES (2);

INSERT INTO Proprietario
VALUES (3, 'S5GB-86HB-87BN-09HG', 5),
	   (4, '7T5B-8SN6-AMGT-96FV', 3);



INSERT INTO MetodoPagamento 
VALUES ('3482-7934-9126-8976', 2, 'Alex', 'Moore', '2022-01-01', 1),
       ('4573-8523-6355-5532', 2, 'Michael ', 'Reed', '2019-01-01', 0),
        ('5692-8503-7455-0539', 3, 'Oscar', 'Vaughn', '2022-01-01', 1),
       ('5692-8503-7455-0539', 4, 'Oscar', 'Vaughn', '2022-01-01', 1);



INSERT INTO Inserzione (ID_proprietario, stato, regione, città, cap, strada, numero_civico, 
	prezzo_giornaliero, max_numero_ospiti, metratura, descrizione, data_inserimento, visibilità)
VALUES (3, 'Italia', 'Campania', 'Salerno', '84121', 'Via Roma', 12, 80.00, 3, 120.00,
	   'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2020-02-14', 1),
	   (3, 'Italia', 'Campania', 'Salerno', '84121', 'Via Nizza', 7, 40.00, 2, 150.00,
	   'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2020-02-14', 1),
	   (3, 'Italia', 'Lombardia', 'Milano', '20019', 'Via Padova', 21, 90.00, 7, 180.00,
	   'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2020-02-14', 1),

	   (4, 'Spagna', 'Catalogna', 'Barcellona', '08001', 'Rambla de Catalunya', 12, 140.00, 10, 375.00,
	   'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2020-07-14', 1),
	   (4, 'Spagna', 'Catalogna', 'Barcellona', '08001', 'Rambla del Raval', 58, 75.50, 5, 90.50,
	   'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2020-07-14', 1),
	   (4, 'Spagna', 'Catalogna', 'Barcellona', '08001', 'Rambla del Poblenou', 22, 300.00, 20, 250.00,
	   'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2020-07-14', 1);
   
INSERT INTO Immagine
VALUES ('.\\OrionImages\\1_1.jpg', 1),
	   ('.\\OrionImages\\1_2.jpg', 1),
       ('.\\OrionImages\\2_1.jpg', 2),
       ('.\\OrionImages\\3_1.jpg', 3),
	   ('.\\OrionImages\\3_2.jpg', 3),
	   ('.\\OrionImages\\4_1.jpg', 4),
	   ('.\\OrionImages\\4_2.jpg', 4),
       ('.\\OrionImages\\5_1.jpg', 5),
       ('.\\OrionImages\\6_1.jpg', 6);

INSERT INTO Stile 
VALUES ('Barocco', 'blabla'),
	   ('Moderno', 'blabla'),
	   ('Classico', 'blabla');

INSERT INTO AppartenenzaStile VALUES(1, 'Barocco'), (1, 'Moderno'), (1, 'Classico');

INSERT INTO Prenotazione (ID_cliente, ID_inserzione, stato)
VALUES (2, 6, 1);

INSERT INTO Recensione(ID_cliente, ID_inserzione, punteggio, titolo, contenuto, data_pubblicazione)
VALUES (2, 6, 5, 'Esperienza fantastica', 'Appartamento elegante e luminoso', '2020-08-20');

INSERT INTO IntervalloDisponibilità
VALUES (1, '2020-03-24', '2020-05-04'),
	   (1, '2020-05-12', '2020-05-20'),
	   (1, '2020-07-15', '2020-07-24'),
	   (2, '2020-03-28', '2020-04-12'),
	   (2, '2020-04-08', '2020-07-22'),
	   (6, '2020-03-24', '2020-05-04'),
	   (6, '2020-07-15', '2020-07-24');
       
       
SELECT * 
FROM Intervallodisponibilità AS I
WHERE EXISTS (SELECT *
			  FROM Intervallodisponibilità
              WHERE ID_inserzione = 6
              AND ID_inserzione = I.ID_inserzione
              AND data_inizio BETWEEN I.data_inizio AND '2020-03-27'
              AND data_fine BETWEEN '2020-03-30' AND I.data_fine);