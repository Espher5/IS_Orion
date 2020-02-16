INSERT INTO Utente (email, password_utente, nome, cognome, data_registrazione, stato)
VALUES ('email1@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1),
	   ('admin@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1),
	   ('M.ESPOSITO253@studenti.unisa.it', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1),
	   ('utenteSospeso@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 0),
	   ('utenteConPrenotazioneAttiva@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1),
	   ('EmailEsistente@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1),
	   ('ProprietarioConMPScaduto@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1),
       ('ClienteConRecensione@gmail.com', 'Abcde1', 'nome1', 'cognome1', '2020-01-01', 1);

INSERT INTO Proprietario VALUES(1, 'cod1', 0);
INSERT INTO Proprietario VALUES(6, 'cod1', 10);
INSERT INTO Proprietario VALUES(7, 'cod1', 0);

INSERT INTO Amministratore VALUES(2, 0);
INSERT INTO Cliente(ID_utente) VALUES(3), (4), (8);
SELECT * FROM Cliente;


INSERT INTO MetodoPagamento 
VALUES ('5692-8503-7455-0539', 1, 'AA', 'AA', '2022-01-01', true),
	   ('7537-7534-5636-0465', 3, 'AA', 'AA', '2022-01-01', true),
       ('7777-7777-7777-7777', 3, 'AA', 'AA', '2019-01-01', true),
	   ('3482-7934-9126-8976', 3, 'AA', 'AA', '2022-01-01', true),
       ('4573-8523-6355-5532', 7, 'AA', 'AA', '2019-01-01', true);


INSERT INTO Inserzione (ID_proprietario, stato, 
	regione, città, cap, strada, numero_civico, 
	prezzo_giornaliero, max_numero_ospiti, metratura, 
	descrizione, data_inserimento, visibilità)
VALUES (1, 'Italia', 'Campania', 'Salerno', '12345', 's1', 12, 120.05, 10, 340, 
		'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 
		'2020-01-24', 1),
        (7, 'Italia', 'Campania', 'Salerno', '12345', 's1', 12, 120.05, 10, 340, 
		'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
		tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
		quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
		consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
		cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
		proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 
		'2020-01-24', 1);

INSERT INTO IntervalloDisponibilità 
VALUES (1, '2021-01-01', '2021-02-02');

INSERT INTO Stile 
VALUES ('Barocco', 'blabla'),
	   ('Moderno', 'blabla'),
	   ('Classico', 'blabla');

INSERT INTO AppartenenzaStile VALUES(1, 'Barocco'), (1, 'Moderno'), (1, 'Classico');

INSERT INTO Prenotazione(ID_cliente, ID_inserzione) 
VALUES (3, 1),
	   (8, 1);

INSERT INTO Recensione(ID_cliente, ID_inserzione, punteggio, titolo, contenuto, data_pubblicazione)
VALUES (8, 1, 5, 'blabla', 'blabla', '2020-01-10');
