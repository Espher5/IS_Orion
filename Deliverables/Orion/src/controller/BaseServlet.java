package controller;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dataAccessObjects.utenza.*;

/**
 * Contiene una serie di metodi di utility per il controllo
 * degli accessi e la validazione degli input
 * 
 * @author Michelangelo Esposito
 *
 */
@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1480800926362194857L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
		
	/**
	 * Verifica se l'utente con id specificato è cliente
	 * 
	 * @param idUtente l'id dell'utente su cui effettuare il controllo
	 * @return true se l'utente è cliente, false altrimenti
	 */
	public static boolean isCliente(long idUtente) {
		ClienteDao cd = new ClienteDaoImpl();
		return cd.doRetrieveByKey(idUtente) != null ? true : false;
	}
	
	/**
	 * Verifica se l'utente con id specificato è proprietario
	 * 
	 * @param idUtente l'id dell'utente su cui effettuare il controllo
	 * @return true se l'utente è proprietario, false altrimenti
	 */
	public static boolean isProprietario(long idUtente) {
		ProprietarioDao pd = new ProprietarioDaoImpl();
		return pd.doRetrieveByKey(idUtente) != null ? true : false;
	}
	/**
	 * Verifica se l'utente con id specificato è amministratore
	 * 
	 * @param idUtente l'id dell'utente su cui effettuare il controllo
	 * @return true se l'utente è amministratore, false altrimenti
	 */
	public static boolean isAmministratore(long idUtente) {
		AmministratoreDao ad = new AmministratoreDaoImpl();
		return ad.doRetrieveByKey(idUtente) != null ? true : false;
	}
	
	/**
	 * Verifica se una lista di oggetti String contiene elementi nulli o vuoti
	 * 
	 * @param params i parametri su cui effettuare il controllo
	 * @return false se esiste almeno un elemento nullo e vuoto, true altrimenti
	 */
	public static boolean checkParameters(String...params) {
		for(String s : params) {
			if(s == null || s.equals("")) return false;
		}
		return true;
	}
	
	/**
	 * Valida le credenziali inserite al momento della registrazione, appoggiandosi a
	 * diversi altri metodi
	 * 
	 * @see BaseServlet.checkParameters per i controlli generali sui parametri
	 * @see BaseServlet.validaEmail per la validazione dell'email
	 * @see BaseServlet.validaPassword per la validazione della password
	 * @see BaseServlet.validaNome per la validazione di nome e cognome
	 * 
	 * @param email l'email inserita
	 * @param password la password inserita
	 * @param confermaPassword la conferma della password inserita
	 * @param nome il nome inserito
	 * @param cognome il cognome inserito
	 * @return true se le credenziali rispettano la validazione, false altrimenti
	 */
	public static boolean validaCredenzialiUtente(String email, String password, String confermaPassword, String nome, String cognome) {
		boolean result = checkParameters(email, password, confermaPassword, nome, cognome);
		return result && validaEmail(email) && validaPassword(password) && password.equals(confermaPassword) &&
				validaNome(nome) && validaNome(cognome);
	}	
	
	/**
	 * Valida le credenziali relative ad un'inserzione inserita, appoggiandosi a
	 * diversi altri metodi
	 * 
	 * @see BaseServlet.checkParameters per i controlli generali sui parametri
	 * @see BaseServlet.validaCap per la validazione del cap
	 * @see BaseServlet.validaDouble per la validazione di numero civico, prezzo, numero ospiti e metratura
	 * @see BaseServlet.validaDescrizione per la validazione della descrizione
	 * 
	 * @param idProprietario l'id del proprietario che sta inserendo l'inserzione
	 * @param stato lo stato inserito
	 * @param regione la regione inserita
	 * @param città la città inserita
	 * @param cap il cap inserito
	 * @param strada la strada inserita
	 * @param numeroCivico il numero civico inserito
	 * @param prezzo il prezzo inserito
	 * @param numeroOspiti il numero ospiti inserito
	 * @param metratura la metratura inserita
	 * @param descrizione la descrizione inserita
	 * @return true se le informazioni rispettano la validazione, false altrimenti
	 */
	public static boolean validaParametriInserzione(String idProprietario, String stato, String regione, String città,
			String cap, String strada, String numeroCivico, String prezzo, String numeroOspiti, String metratura,
			String descrizione) {
		boolean result = checkParameters(idProprietario, stato, regione, città, cap, strada, numeroCivico, prezzo, numeroOspiti,
				metratura, descrizione);
		return result && validaCap(cap) && validaDouble(Double.parseDouble(numeroCivico), Double.parseDouble(prezzo),
				Double.parseDouble(numeroOspiti), Double.parseDouble(metratura));
	}
	
	/**
	 * Valida le credenziali relative ad una recensione inserita, appoggiandosi a
	 * diversi altri metodi
	 * 
	 * @see BaseServlet.checkParameters per i controlli generali sui parametri
	 * @see BaseServlet.validaDescrizione per la validazione del contenuto della recensione
	 * 
	 * @param punteggio il punteggio inserito
	 * @param titolo il titolo inserito
	 * @param contenuto il contenuto inserito
	 * @return true se le informazioni rispettano la validazione, false altrimenti
	 */
	public static boolean validaRecensione(String punteggio, String titolo, String contenuto) {
		boolean result = checkParameters(punteggio, titolo, contenuto);
		try {
			Double p = Double.parseDouble(punteggio);
			if(p < 1 || p > 5) {
				result = false;
			}
		}
		catch(NumberFormatException e) {
			result = false;
		}
		return result && validaDescrizione(contenuto);
	}
	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param email la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaEmail(String email) {
		Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		return emailRegex.matcher(email).matches();
	}
  	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param password la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaPassword(String password) {
		Pattern passwordRegex = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{6,}$");
		return passwordRegex.matcher(password).matches();
	}
	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param nome la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaNome(String nome) {
		Pattern passwordRegex = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
		return passwordRegex.matcher(nome).matches();
	}
	
	/**
	 * Valida una serie di numeri in virgola mobile e verifica che non siano negativi
	 * 
	 * @param numeri
	 * @return
	 */
	public static boolean validaDouble(Double... numeri) {
		Pattern passwordRegex = Pattern.compile("^\\d+([.]\\d{1,2})?$");
		boolean flag = true;
		for(Double numero: numeri) {
			if(!passwordRegex.matcher(numero.toString()).matches()) {
				flag = false;
			}
		}		
		return flag;
	}

	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param s la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaDescrizione(String s) {
		Pattern passwordRegex = Pattern.compile("^(?!\\s*$).+$");
		return passwordRegex.matcher(s).matches();
	}
	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param cap la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaCap(String cap) {
		Pattern passwordRegex = Pattern.compile("^\\d{5}$");
		return passwordRegex.matcher(cap).matches();
	}
	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param codiceFiscale la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaCodiceFiscale(String codiceFiscale) {
		Pattern regex = Pattern.compile("^([\\w\\d]{4}[-]){3}[\\w\\d]{4}$", Pattern.CASE_INSENSITIVE);
		return regex.matcher(codiceFiscale).matches();
	}
	
	/**
	 * Valida una stringa secondo l'espressione regolare specificata
	 * 
	 * @param numeroCarta la stringa da validare
	 * @return true se la stringa supera la validazione, false altrimenti
	 */
	public static boolean validaNumeroCarta(String numeroCarta) {
		Pattern regex = Pattern.compile("^(\\d{4}[-]){3}\\d{4}$", Pattern.CASE_INSENSITIVE);
		return regex.matcher(numeroCarta).matches();
	}
	
	/**
	 * Verifica se la data passata è antecedente alla data di oggi
	 * 
	 * @param data la data da validare
	 * @return true se la data supera la validazione, false altrimenti
	 */
	public static boolean validaData(Date data) {
		return (data.compareTo(new Date()) > 0);
	}
}
