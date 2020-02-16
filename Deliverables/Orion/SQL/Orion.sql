DROP DATABASE IF EXISTS Orion;
CREATE DATABASE Orion;

USE Orion;


/* Tabelle utenti */

CREATE TABLE Utente (
	ID_utente 		BIGINT PRIMARY KEY AUTO_INCREMENT,
	email			VARCHAR(50) NOT NULL UNIQUE,
	password_utente		VARCHAR(20) NOT NULL,
	nome			VARCHAR(30) NOT NULL,
	cognome			VARCHAR(30) NOT NULL,
	data_registrazione  	DATE NOT NULL,
    	stato			BOOLEAN DEFAULT TRUE
);


CREATE TABLE Amministratore (
	ID_utente		BIGINT,
	num_revisionate		INT NOT NULL,

	FOREIGN KEY (ID_utente) REFERENCES Utente (ID_utente)
				ON UPDATE CASCADE
				ON DELETE CASCADE
);


CREATE TABLE Cliente (
	ID_utente		BIGINT,
    	ultima_prenotazione	DATE,

	FOREIGN KEY (ID_utente) REFERENCES Utente (ID_utente)
				ON UPDATE CASCADE
				ON DELETE CASCADE
);


CREATE TABLE Proprietario (
	ID_utente		BIGINT,
	codice_fiscale		VARCHAR(30) NOT NULL,
	num_inserzioni		INT NOT NULL,

	FOREIGN KEY (ID_utente) REFERENCES Utente (ID_utente)
				ON UPDATE CASCADE
				ON DELETE CASCADE
);


CREATE TABLE Messaggio (
	ID_mittente		BIGINT,
	ID_destinatario		BIGINT,
	contenuto		VARCHAR(500),
	data_ora_invio		VARCHAR(50) NOT NULL, 

	PRIMARY KEY (ID_mittente, ID_destinatario),
	FOREIGN KEY (ID_mittente) REFERENCES Utente (ID_utente)
				  ON UPDATE CASCADE
				  ON DELETE CASCADE,						  
	FOREIGN KEY (ID_destinatario) REFERENCES Utente (ID_utente)
				      ON UPDATE CASCADE
				      ON DELETE CASCADE								
);



/* Tabelle inserzioni */

CREATE TABLE Inserzione (
	ID_inserzione		BIGINT  PRIMARY KEY AUTO_INCREMENT,
	ID_proprietario		BIGINT,
	stato			VARCHAR(30) NOT NULL,
	regione 		VARCHAR(30) NOT NULL,
	città			VARCHAR(30) NOT NULL,
	cap			CHAR(5) NOT NULL,
	strada			VARCHAR(30) NOT NULL,
	numero_civico		SMALLINT NOT NULL,
	prezzo_giornaliero  	DOUBLE NOT NULL,
	max_numero_ospiti   	SMALLINT NOT NULL,
	metratura      		DOUBLE NOT NULL,
	descrizione		VARCHAR(500) NOT NULL,
	data_inserimento	DATE NOT NULL,
   	visibilità		BOOLEAN DEFAULT FALSE,

	FOREIGN KEY (ID_proprietario) REFERENCES Utente (ID_utente)
				      ON UPDATE CASCADE
				      ON DELETE SET NULL
);


CREATE TABLE Immagine (
	pathname		VARCHAR(256) PRIMARY KEY,
	ID_inserzione		BIGINT,

	FOREIGN KEY (ID_inserzione) REFERENCES Inserzione (ID_inserzione)
				    ON UPDATE CASCADE
				    ON DELETE CASCADE
);


CREATE TABLE Stile (
	nome_stile		VARCHAR(20) PRIMARY KEY,
	descrizione		VARCHAR(100)
);


CREATE TABLE AppartenenzaStile (
	ID_inserzione		BIGINT,
	nome_stile		VARCHAR(20),
    
    	PRIMARY KEY (ID_inserzione, nome_stile),
	FOREIGN KEY (ID_inserzione) REFERENCES Inserzione (ID_inserzione)
				    ON UPDATE CASCADE
				    ON DELETE CASCADE,
	FOREIGN KEY (nome_stile) REFERENCES Stile (nome_stile)
				    ON UPDATE CASCADE
				    ON DELETE CASCADE
);


CREATE TABLE IntervalloDisponibilità (
	ID_inserzione		BIGINT,
	data_inizio		DATE,
	data_fine		DATE,

	PRIMARY KEY (ID_inserzione, data_inizio, data_fine),
	FOREIGN KEY (ID_inserzione) REFERENCES Inserzione (ID_inserzione)
				    ON UPDATE CASCADE
			    	    ON DELETE CASCADE
);


CREATE TABLE Recensione (
	ID_recensione		BIGINT PRIMARY KEY AUTO_INCREMENT,
	ID_cliente		BIGINT,
	ID_inserzione		BIGINT,
	punteggio		TINYINT NOT NULL DEFAULT 0,
	titolo			VARCHAR(500) NOT NULL,
	contenuto		VARCHAR(500) NOT NULL,
	data_pubblicazione  	DATE NOT NULL,

	FOREIGN KEY (ID_cliente) REFERENCES Utente (ID_utente)
				 ON UPDATE CASCADE
				 ON DELETE CASCADE,
	FOREIGN KEY (ID_inserzione) REFERENCES Inserzione (ID_inserzione)
				    ON UPDATE CASCADE
				    ON DELETE CASCADE
);


CREATE TABLE Commento (
	ID_recensione 		BIGINT,
	contenuto		VARCHAR(500) NOT NULL,
	data_pubblicazione	DATE NOT NULL,

	FOREIGN KEY (ID_recensione) REFERENCES Recensione (ID_recensione)
				    ON UPDATE CASCADE
				    ON DELETE CASCADE
);



/* Tabelle prenotazioni */

CREATE TABLE MetodoPagamento (
	numero_carta		VARCHAR(30),
    	ID_utente		BIGINT,
	nome_titolare		VARCHAR(30) NOT NULL,
	cognome_titolare	VARCHAR(30) NOT NULL,
	data_scadenza		DATE NOT NULL,
    	preferito		BOOLEAN,
    
   	 PRIMARY KEY(numero_carta, ID_utente),
	FOREIGN KEY (ID_utente) REFERENCES Utente (ID_utente)
				ON UPDATE CASCADE
				ON DELETE CASCADE
);


CREATE TABLE Prenotazione (
	ID_prenotazione		BIGINT PRIMARY KEY AUTO_INCREMENT,
   	 ID_cliente		BIGINT,
	ID_inserzione		BIGINT,
	mp_cliente		VARCHAR(30),
	mp_proprietario		VARCHAR(30),
	data_prenotazione	DATE,
	data_check_in		DATE,
	data_check_out		DATE,
	totale			DOUBLE,
	numero_ospiti 		TINYINT,
   	stato			BOOLEAN DEFAULT FALSE,

	FOREIGN KEY (ID_cliente) REFERENCES Utente (ID_utente)
				 ON UPDATE CASCADE
				 ON DELETE SET NULL,
	FOREIGN KEY (ID_inserzione) REFERENCES Inserzione (ID_inserzione)
				    ON UPDATE CASCADE
				    ON DELETE SET NULL
);


CREATE TABLE PrenotazioneArchiviata (
	ID_prenotazione		BIGINT,
	stato			VARCHAR(30) NOT NULL,
	regione 		VARCHAR(30) NOT NULL,
	città			VARCHAR(30) NOT NULL,
	cap			CHAR(5) NOT NULL,
	indirizzo		VARCHAR(50) NOT NULL,
	prezzo_giornaliero 	DOUBLE NOT NULL,

	FOREIGN KEY (ID_prenotazione) REFERENCES Prenotazione (ID_prenotazione)
				      ON UPDATE CASCADE
				      ON DELETE CASCADE
);
